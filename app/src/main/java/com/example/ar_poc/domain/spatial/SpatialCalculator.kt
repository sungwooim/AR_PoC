package com.example.ar_poc.domain.spatial

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * AR/XR 화면 좌표 계산 순수 함수 모음.
 *
 * **설계 원칙 — UI 의존성 제로**
 * - Android/Jetpack Compose 임포트가 없다.
 * - Float 쌍(Pair)과 기본 타입만 사용하므로 AR 글래스, TV, 웹 등 어떤 렌더러도 재사용 가능.
 * - 모든 함수는 부수효과 없는 순수 함수(pure function)이므로 단위 테스트가 용이하다.
 *
 * **좌표계 정의**
 * - X축: 화면 왼쪽(0) → 오른쪽(screenW)
 * - Y축: 화면 위쪽(0) → 아래쪽(screenH)
 * - 방위각(azimuth): 0°=북, 90°=동, 180°=남, 270°=서
 * - 피치(pitch): -90°=수평(눕힘), 0°=수직(정면), +90°=아래 향함
 */
object SpatialCalculator {

    // ─────────────────────────────────────────────────────────────────────
    // 카메라 FOV 상수 (일반 스마트폰 광각 기준)
    // ─────────────────────────────────────────────────────────────────────
    const val FOV_H = 65f    // 수평 시야각(°)
    /**
     * 수직 FOV = 120°:
     * remapCoordinateSystem 적용 후 pitch 범위: 폰 세로 파지 정면=0°, 눕힘=-90°, 위 향함=+90°.
     * 실제 체감 AR 시야(위아래 60°씩)에 맞게 120°로 설정.
     */
    const val FOV_V = 120f
    /**
     * PITCH_BIAS = 0°:
     * ARSensorProvider에서 remapCoordinateSystem(AXIS_X, AXIS_Z) 적용 후,
     * 폰 세로 파지 시 pitch ≈ 0° → 추가 보정 불필요.
     */
    const val PITCH_BIAS = 0f
    const val FOV_MARGIN = 1.1f // FOV 허용 배율 — 1.1=10% 여유

    /**
     * POI의 AR 화면 픽셀 좌표를 계산한다.
     *
     * @param userLat    사용자 위도
     * @param userLng    사용자 경도
     * @param targetLat  POI 위도
     * @param targetLng  POI 경도
     * @param azimuth    현재 나침반 방위각 (0~360°)
     * @param pitch      현재 피치 (-90°=눕힘, 0°=수직, +90°=아래)
     * @param screenW    화면 너비(px)
     * @param screenH    화면 높이(px)
     * @param fovHDeg    수평 FOV(°). 기본값 [FOV_H]
     * @param fovVDeg    수직 FOV(°). 기본값 [FOV_V]
     * @param fovMargin  FOV 외곽 허용 배율 (1.0=딱 맞게, 1.4=40% 여유). 기본값 1.4f
     * @param pitchBias  자연스러운 파지 각도 보정값(°). 기본값 25f
     *
     * @return Pair(xPx, yPx), FOV 밖이면 null
     */
    fun calcScreenPos(
        userLat: Double, userLng: Double,
        targetLat: Double, targetLng: Double,
        azimuth: Float, pitch: Float,
        screenW: Float, screenH: Float,
        fovHDeg: Float = FOV_H,
        fovVDeg: Float = FOV_V,
        fovMargin: Float = FOV_MARGIN,
        pitchBias: Float = PITCH_BIAS
    ): Pair<Float, Float>? {
        val bearing = calcBearing(userLat, userLng, targetLat, targetLng)

        // X: 방위각 차 → 화면 좌표 (-1=좌측, +1=우측)
        val bearingDiff = ((bearing - azimuth + 540f) % 360f) - 180f
        val xFrac = bearingDiff / (fovHDeg / 2f)
        if (xFrac < -fovMargin || xFrac > fovMargin) return null

        // Y 계산:
        //   ARSensorProvider.remapCoordinateSystem(AXIS_X, AXIS_Z) 적용 후 pitch:
        //     폰 수직 portrait → ≈  0°
        //     폰 수평 face-up  → ≈ -90°
        //     폰 위로 기울임   → ≈ +수십°
        //
        //   pitchBias=0 (추가 보정 없음):
        //     수직 파지 :  0° + 0° =  0° → yFrac=0 → 화면 중앙 ✅
        //     앞으로 기울 : +30° + 0° = +30° → yFrac=-0.5 → 화면 상단 ✅
        //     뒤로 기울  : -30° + 0° = -30° → yFrac=+0.5 → 화면 하단 ✅
        val pitchAdjusted = pitch + pitchBias  // pitchBias=0 → pitch 그대로 사용
        val yFrac = -pitchAdjusted / (fovVDeg / 2f)  // 위로 기울수록 Y가 작아짐(위쪽)

        val xPx = screenW / 2f + xFrac * (screenW / 2f)
        // Y: 화면 10%~90% 안에서 클램프
        val yPxRaw = screenH / 2f + yFrac * (screenH / 2f)
        val yPx = yPxRaw.coerceIn(screenH * 0.10f, screenH * 0.90f)

        return Pair(xPx, yPx)
    }

    /**
     * 두 GPS 좌표 간 방위각(베어링)을 계산한다.
     *
     * @return 0~360° (0=북, 90=동, 180=남, 270=서)
     */
    fun calcBearing(
        lat1: Double, lng1: Double,
        lat2: Double, lng2: Double
    ): Float {
        val lat1R = Math.toRadians(lat1)
        val lat2R = Math.toRadians(lat2)
        val dLng = Math.toRadians(lng2 - lng1)
        val y = sin(dLng) * cos(lat2R)
        val x = cos(lat1R) * sin(lat2R) - sin(lat1R) * cos(lat2R) * cos(dLng)
        return ((Math.toDegrees(atan2(y, x)).toFloat() + 360f) % 360f)
    }

    /**
     * 두 GPS 좌표 간 하버사인(Haversine) 직선 거리를 미터 단위로 계산한다.
     */
    fun calcDistanceM(
        lat1: Double, lng1: Double,
        lat2: Double, lng2: Double
    ): Float {
        val R = 6_371_000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLng / 2) * sin(dLng / 2)
        return (R * 2 * atan2(sqrt(a), sqrt(1.0 - a))).toFloat()
    }

    /**
     * 두 방위각 사이의 최소 각도 차이를 반환한다 (0~180°).
     *
     * 예: azimuth=10°, bearing=350° → 20° (북쪽 방향으로 20° 차이)
     *
     * @param azimuth 사용자 방위각 (0~360°)
     * @param bearing 목표물 베어링 (0~360°)
     * @return 최소 각도 차이 (0~180°)
     */
    fun angularDifference(azimuth: Float, bearing: Float): Float {
        val diff = ((azimuth - bearing + 540f) % 360f) - 180f
        return kotlin.math.abs(diff)
    }

    /**
     * 방위각을 8방위 문자열로 변환한다.
     *
     * @param lang 언어 코드 (ko/en/ja/zh)
     */
    fun azimuthToCardinal(azimuth: Float, lang: String = "ko"): String {
        val idx = ((azimuth + 22.5f) / 45f).toInt() % 8
        return when (lang) {
            "en" -> arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")[idx]
            "ja" -> arrayOf("北", "北東", "東", "南東", "南", "南西", "西", "北西")[idx]
            "zh" -> arrayOf("北", "东北", "东", "东南", "南", "西南", "西", "西北")[idx]
            else -> arrayOf("북", "북동", "동", "남동", "남", "남서", "서", "북서")[idx]
        }
    }
}
