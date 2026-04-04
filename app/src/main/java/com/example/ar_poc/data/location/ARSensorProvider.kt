package com.example.ar_poc.data.location

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * AR 전용 센서 프로바이더.
 * TYPE_ROTATION_VECTOR를 사용하여 EMA 보정된 방위각/피치/롤을 StateFlow로 제공.
 * CompassProvider(EMA 아지무스)와 별도로 동작하며 AR 오버레이 카드 위치 계산에 사용.
 */
class ARSensorProvider(context: Context) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    // 지수이동평균(EMA) 평활 계수
    // 0.35 = 빠른 응답 + 충분한 노이즈 제거 균형
    // (0.18은 너무 낮아 카드가 실제 방향보다 뒤처지는 '슬라이딩' 유발)
    private val ALPHA = 0.35f

    private var filteredAzimuth = -1f
    private var filteredPitch = 0f
    private var filteredRoll = 0f

    private val _azimuth = MutableStateFlow(0f)
    /** 방위각 (0~360°) — EMA 평활 적용 */
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()

    private val _pitch = MutableStateFlow(0f)
    /**
     * 피치 (기울기) — SensorManager.getOrientation 기준:
     *   0° : 폰이 수직으로 세워진 상태 (portrait 정면)
     *  -90°: 폰이 수평으로 눕힌 상태 (face-up)
     */
    val pitch: StateFlow<Float> = _pitch.asStateFlow()

    private val _roll = MutableStateFlow(0f)
    /** 롤 (좌우 기울기) */
    val roll: StateFlow<Float> = _roll.asStateFlow()

    private val _isPhoneFlat = MutableStateFlow(false)
    /**
     * 폰이 수평(눕힌 상태)인지 여부.
     * pitch < -45° 이면 true — 손목을 자연스럽게 내릴 때 지도로 전환.
     * (기존 -65°는 거의 완전히 눕혀야 했으나, -45°로 완화하여 사용 편의성 향상)
     */
    val isPhoneFlat: StateFlow<Boolean> = _isPhoneFlat.asStateFlow()

    fun start() {
        sensorManager.registerListener(
            this,
            rotationSensor,
            SensorManager.SENSOR_DELAY_GAME  // ~50Hz: AR 카드 추적에 충분히 빠름
        )
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ROTATION_VECTOR) return

        val rotMatrix = FloatArray(9)
        SensorManager.getRotationMatrixFromVector(rotMatrix, event.values)

        // 눕힘 감지: rotMatrix[8] = 기기 Z축(화면 법선)과 세계 수직(하늘) 방향의 내적
        //   ≈ +1.0 : 화면이 위를 향함 (완전 눕힘, face-up)
        //   ≈  0.0 : 화면이 옆을 향함 (세워진 portrait)
        //   ≈ -1.0 : 화면이 아래를 향함 (뒤집어진 상태)
        // 0.5 임계값 = 화면이 약 60° 이상 하늘을 향할 때 지도 전환
        val screenUpComponent = rotMatrix[8]
        _isPhoneFlat.value = screenUpComponent > 0.5f

        // ── Portrait 모드 축 보정 ────────────────────────────────────────────
        // Android 회전 행렬의 기본 기준축은 Landscape(가로) 모드.
        // Portrait(세로)로 사용할 때 X/Y 축이 90° 뒤바뀌어 방위각이 틀린 방향을 가리킴.
        // remapCoordinateSystem으로 X→Z, Z→-X 매핑하면:
        //   - orientation[0] (azimuth) : 카메라가 향하는 방위각 정확히 반영
        //   - orientation[1] (pitch)   : 0° = 폰 세워서 카메라 정면, -90° = 눕힘
        val remappedMatrix = FloatArray(9)
        SensorManager.remapCoordinateSystem(
            rotMatrix,
            SensorManager.AXIS_X,
            SensorManager.AXIS_Z,
            remappedMatrix
        )

        // 방위각/피치/롤 계산 (AR 카드 위치용) — 보정된 행렬 사용
        val orientation = FloatArray(3)
        SensorManager.getOrientation(remappedMatrix, orientation)

        val rawAzimuth = ((Math.toDegrees(orientation[0].toDouble()).toFloat() + 360f) % 360f)
        val rawPitch = Math.toDegrees(orientation[1].toDouble()).toFloat()
        val rawRoll = Math.toDegrees(orientation[2].toDouble()).toFloat()

        if (filteredAzimuth < 0f) {
            filteredAzimuth = rawAzimuth
            filteredPitch = rawPitch
            filteredRoll = rawRoll
        } else {
            var deltaAzimuth = rawAzimuth - filteredAzimuth
            if (deltaAzimuth > 180f) deltaAzimuth -= 360f
            if (deltaAzimuth < -180f) deltaAzimuth += 360f

            // 적응형 EMA (Adaptive Alpha):
            // 회전 속도가 빠르거나 변화량이 클 때(>5도)는 신뢰도를 높여(0.85) 
            // 카드가 밀리거나 슬라이딩(지연)되는 현상 방지. 
            // 가만히 있을 때는(0.35) 부드럽게 유지하여 손떨림 억제. 
            val adaptiveAlpha = if (Math.abs(deltaAzimuth) > 5f) 0.85f else 0.35f

            filteredAzimuth = (filteredAzimuth + adaptiveAlpha * deltaAzimuth + 360f) % 360f
            filteredPitch += adaptiveAlpha * (rawPitch - filteredPitch)
            filteredRoll += adaptiveAlpha * (rawRoll - filteredRoll)
        }

        _azimuth.value = filteredAzimuth
        _pitch.value = filteredPitch
        _roll.value = filteredRoll
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
