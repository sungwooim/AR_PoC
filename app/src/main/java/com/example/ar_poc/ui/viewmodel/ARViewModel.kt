package com.example.ar_poc.ui.viewmodel

import android.graphics.Bitmap
import android.location.Location
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ar_poc.Strings
import com.example.ar_poc.ai.GeminiClient
import com.example.ar_poc.data.audio.TTSManager
import com.example.ar_poc.data.discovery.DiscoveryTracker
import com.example.ar_poc.data.location.ARSensorProvider
import com.example.ar_poc.data.quiz.QuizRepository
import com.example.ar_poc.domain.HeritageRecognitionPipeline
import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.QuizQuestion
import com.example.ar_poc.domain.model.SubElement
import com.example.ar_poc.domain.repository.HeritageRepository
import com.example.ar_poc.domain.spatial.SpatialCalculator
import com.example.ar_poc.domain.spatial.SpatialContext
import com.example.ar_poc.domain.spatial.SpatialContextProvider
import com.google.mlkit.vision.objects.DetectedObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.ar_poc.di.DevModeManager

sealed class ARUiState {
    object Idle : ARUiState()
    object GazeLoading : ARUiState()
    object Detecting : ARUiState()
    data class Detected(
        val content: HeritageContent,
        val center: Offset = Offset(0.5f, 0.45f),
        val size: Size = Size(250f, 200f)
    ) : ARUiState()
    data class Error(val message: String) : ARUiState()
}

enum class CameraMode { LIVE, DEMO }

@HiltViewModel
class ARViewModel @Inject constructor(
    private val pipeline: HeritageRecognitionPipeline,
    private val repository: HeritageRepository,
    private val geminiClient: GeminiClient,
    private val devModeManager: DevModeManager,
    private val arSensorProvider: ARSensorProvider,
    private val ttsManager: TTSManager,
    private val discoveryTracker: DiscoveryTracker,
    private val quizRepository: QuizRepository,
    private val spatialContextProvider: SpatialContextProvider,  // XR 엔진
) : ViewModel() {
    var targetLanguage: String = "ko"
        private set

    private val _uiState = MutableStateFlow<ARUiState>(ARUiState.Idle)
    val uiState: StateFlow<ARUiState> = _uiState.asStateFlow()

    private val _cameraMode = MutableStateFlow(CameraMode.LIVE)
    val cameraMode = _cameraMode.asStateFlow()

    private val _gazeProgress = MutableStateFlow(0f)
    val gazeProgress = _gazeProgress.asStateFlow()

    private val _isObjectCandidateDetected = MutableStateFlow(false)
    val isObjectCandidateDetected: StateFlow<Boolean> = _isObjectCandidateDetected.asStateFlow()

    val isDevMode: StateFlow<Boolean> = devModeManager.devModeState

    // 모든 방위각은 ARSensorProvider(Rotation Vector)로 통합
    val currentAzimuth: StateFlow<Float> = arSensorProvider.azimuth
    val debugInfo = pipeline.debugInfo

    // AR 오버레이 센서 데이터
    val arPitch: StateFlow<Float> = arSensorProvider.pitch
    val isPhoneFlat: StateFlow<Boolean> = arSensorProvider.isPhoneFlat

    // AR 오버레이 (기본 ON — 세우면 AR카드, 눕히면 지도 자동 전환)
    private val _showArOverlay = MutableStateFlow(true)
    val showArOverlay: StateFlow<Boolean> = _showArOverlay.asStateFlow()

    /** The currently recognised sub-element (Stage 2), or null if none detected. */
    private val _recognizedSubElement = MutableStateFlow<SubElement?>(null)
    val recognizedSubElement: StateFlow<SubElement?> = _recognizedSubElement.asStateFlow()

    private val _recognitionFailedMessage = MutableStateFlow<String?>(null)
    val recognitionFailedMessage: StateFlow<String?> = _recognitionFailedMessage.asStateFlow()

    private val _discoveredHeritages = MutableStateFlow<Set<String>>(emptySet())
    val discoveredHeritages: StateFlow<Set<String>> = _discoveredHeritages.asStateFlow()

    private val _discoveredSubElements = MutableStateFlow<Set<String>>(emptySet())
    val discoveredSubElements: StateFlow<Set<String>> = _discoveredSubElements.asStateFlow()

    val heritageList = repository.getHeritageList()

    /**
     * 유산 POI + 일반 POI(카페, 안내소 등) 통합 목록.
     * Repository의 getAllPois()가 단일 체임으로 반환 — ViewModel은 그대로 위임.
     * AROverlayScreen 및 MapScreen에서 사용.
     */
    val poiList: List<Poi> = repository.getAllPois()

    // ─────────────────────────────────────────────────────────────────────
    // XR 엔진 상태 (SpatialContextProvider 위임)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * 현재 공간 컨텍스트. GPS 5초 폴링 기반으로 갱신.
     * 이 하나만 구독하면 지도/AR오버레이/내비게이션 모두 렴더링할 수 있다.
     * [SpatialContext.EMPTY]가 초기값;이후 GPS 횟 화인 시 진짜 값으로 교체됨.
     */
    val spatialContext: StateFlow<SpatialContext> =
        spatialContextProvider.getSpatialContext()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SpatialContext.EMPTY
            )

    // 경복궁 등 사이트 진입 여부 — spatialContext.isInsideSite에서 실시간 파생
    val isInsidePalace: StateFlow<Boolean> =
        spatialContext
            .map { it.isInsideSite }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false
            )

    // 현재 GPS 위치 — spatialContext에서 파생 (GPS 중복 폴링 없음)
    val currentLocation: StateFlow<Location?> =
        spatialContext
            .map { ctx ->
                if (!ctx.hasValidLocation) null
                else Location("SpatialProvider").apply {
                    latitude = ctx.userLat
                    longitude = ctx.userLng
                    accuracy = ctx.accuracyM
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    /**
     * 다음에 방문할 POI ID 시퀀스 (Mock 로직).
     * 현재 nearbyPois 중 발견하지 않은 HERITAGE POI를 우선으로 추시.
     * TODO: 실제 코스 추시 / 청소년 AR 주제에 맞는 게이미피케이션 로직으로 교체할 것.
     */
    val nextPoiIds: StateFlow<List<String>> =
        combine(spatialContext, _discoveredHeritages) { ctx, discovered ->
            ctx.nearbyPois
                .filter { poi -> poi.id !in discovered }
                .take(3)
                .map { it.id }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    /**
     * 다음 목적지 방향 힌트 (Mock 로직).
     * combine을 사용하여 spatialContext와 nextPoiIds 모두에서 수집 후 생성 — race condition 방지.
     */
    val navigationHint: StateFlow<String?> =
        combine(spatialContext, nextPoiIds) { ctx, ids ->
            val nextId = ids.firstOrNull() ?: return@combine null
            val nextPoi = ctx.nearbyPois.find { it.id == nextId } ?: return@combine null
            if (!ctx.hasValidLocation) return@combine null
            val bearing = SpatialCalculator.calcBearing(
                ctx.userLat, ctx.userLng,
                nextPoi.latitude, nextPoi.longitude
            )
            val cardinal = SpatialCalculator.azimuthToCardinal(bearing, targetLanguage)
            val distM = SpatialCalculator.calcDistanceM(
                ctx.userLat, ctx.userLng,
                nextPoi.latitude, nextPoi.longitude
            ).toInt()
            val title = nextPoi.localizedTitle(targetLanguage)
            if (targetLanguage == "ko") "$cardinal 방향 ${distM}m → $title"
            else "$cardinal · ${distM}m → $title"
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val isTtsEnabled: StateFlow<Boolean> = ttsManager.isEnabled
    fun toggleTts() = ttsManager.toggleEnabled()

    // 퀴즈: 신규 발견 시 문항 트리거
    private val _pendingQuizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val pendingQuizQuestions: StateFlow<List<QuizQuestion>> = _pendingQuizQuestions.asStateFlow()
    fun clearPendingQuiz() { _pendingQuizQuestions.value = emptyList() }
    fun markQuizCompleted(heritageId: String) = discoveryTracker.markQuizCompleted(heritageId)

    // 발견 타임스탬프 (타임라인)
    fun getDiscoveryTime(heritageId: String): Long? = discoveryTracker.getHeritageDiscoveryTime(heritageId)

    var labels = Strings.getCameraLabels(targetLanguage)
        private set

    fun setLanguage(lang: String) {
        if (targetLanguage != lang) {
            targetLanguage = lang
            labels = Strings.getCameraLabels(targetLanguage)
        }
    }

    private val _suggestedQuestions = MutableStateFlow<List<String>>(emptyList())
    val suggestedQuestions: StateFlow<List<String>> = _suggestedQuestions.asStateFlow()

    private var recognitionJob: Job? = null
    private var gazeTimerJob: Job? = null
    private var subElementGazeJob: Job? = null
    private var consecutiveValidFrames = 0
    private var subElementValidFrames = 0
    private var isManualScan = false
    private var lastCapturedFrame: Bitmap? = null
    private val frameBuffer = mutableListOf<Bitmap>()

    init {
        arSensorProvider.start()
    }

    fun toggleArOverlay() {
        _showArOverlay.value = !_showArOverlay.value
    }

    fun forceScan() {
        if (_uiState.value is ARUiState.Idle || _uiState.value is ARUiState.Detected) {
            isManualScan = true
            if (_uiState.value is ARUiState.Idle) {
                consecutiveValidFrames = 3
                _isObjectCandidateDetected.value = true
                startGazeTimer()
            } else if (_uiState.value is ARUiState.Detected && _recognizedSubElement.value == null) {
                subElementValidFrames = 3
                startSubElementGazeTimer()
            }
        }
    }

    private fun processRecognition(landmarkId: String) {
        recognitionJob?.cancel()
        recognitionJob = viewModelScope.launch {
            val content = repository.getHeritageContent(landmarkId) ?: return@launch

            // 도장 깨기: 신규 발견 시 타임스탬프 기록 + TTS + 퀴즈 트리거
            val isNewDiscovery = landmarkId !in _discoveredHeritages.value
            _discoveredHeritages.value = _discoveredHeritages.value + landmarkId
            if (isNewDiscovery) {
                discoveryTracker.recordHeritage(landmarkId)
                // 퀴즈 아직 안 풀었으면 트리거
                if (!discoveryTracker.isQuizCompleted(landmarkId)) {
                    _pendingQuizQuestions.value = quizRepository.getQuestionsForHeritage(landmarkId)
                }
            }

            // Suggested questions & palace name: always use static map (no LLM needed)
            _suggestedQuestions.value = Strings.getSuggestedQuestions(targetLanguage)
            val palaceName = Strings.getPalaceName(landmarkId, targetLanguage) ?: content.palace

            if (targetLanguage != "ko") {
                val newTitle = content.titleMap[targetLanguage] ?: content.title
                val newDesc = content.shortDescMap[targetLanguage] ?: content.shortDescription
                _uiState.value = ARUiState.Detected(
                    content.copy(title = newTitle, shortDescription = newDesc, palace = palaceName)
                )
            } else {
                _uiState.value = ARUiState.Detected(content.copy(palace = palaceName))
            }

            // TTS: 건물 발견 시 짧은 설명 낭독
            val descToSpeak = content.shortDescMap[targetLanguage] ?: content.shortDescription
            ttsManager.speak(descToSpeak, targetLanguage)
        }
    }

    fun toggleDevMode() {
        devModeManager.toggle()
        if (!devModeManager.isDevMode) {
            resetDetection()
        }
    }

    fun toggleCameraMode() {
        _cameraMode.value = if (_cameraMode.value == CameraMode.LIVE) CameraMode.DEMO else CameraMode.LIVE
        resetDetection()
    }

    fun onObjectsDetected(detectedObjects: List<DetectedObject>, width: Int, height: Int, bitmap: Bitmap?) {
        lastCapturedFrame = bitmap

        val validObject = detectedObjects.find { obj ->
            val bounds = obj.boundingBox
            val centerX = bounds.centerX().toFloat() / width
            val centerY = bounds.centerY().toFloat() / height
            centerX in 0.3f..0.7f && centerY in 0.3f..0.7f
        }

        // Behavior depends on current UI state
        if (_uiState.value is ARUiState.Detected) {
            // Stage 2: ML Kit 감지 결과 기반 서브엘리먼트 추적
            // (이전 auto-trigger 방식 복구: ML Kit validObject 또는 forceScan으로 트리거)
            if (validObject != null || isManualScan) {
                subElementValidFrames++
                if (subElementValidFrames >= 3 && subElementGazeJob == null && _recognizedSubElement.value == null) {
                    startSubElementGazeTimer()
                }
            } else {
                subElementValidFrames = 0
                subElementGazeJob?.cancel()
                subElementGazeJob = null
            }
        } else if (_uiState.value !is ARUiState.Detecting) {
            // Stage 1: Track buildings
            if (validObject != null || isManualScan) {
                consecutiveValidFrames++
                if (consecutiveValidFrames >= 3 && !_isObjectCandidateDetected.value) {
                    _isObjectCandidateDetected.value = true
                    startGazeTimer()
                }
            } else {
                handleTargetLost()
            }
        }
    }

    private fun startGazeTimer() {
        gazeTimerJob?.cancel()
        gazeTimerJob = viewModelScope.launch {
            _uiState.value = ARUiState.GazeLoading
            var elapsed = 0L
            frameBuffer.clear()
            
            while (elapsed < 3000L) {
                delay(100L)
                elapsed += 100L
                _gazeProgress.value = elapsed.toFloat() / 3000L
                
                // 1초, 2초, 3초 시점마다 프레임 캡처 (현재는 마지막 프레임만 사용)
                if (elapsed == 1000L || elapsed == 2000L || elapsed == 3000L) {
                    lastCapturedFrame?.let { frameBuffer.add(it) }
                }
            }
            
            // Reset candidate state before starting detection
            _isObjectCandidateDetected.value = false
            consecutiveValidFrames = 0
            _uiState.value = ARUiState.Detecting
            
            // Execute real pipeline. Overriding happens upon success or failure synchronously
            val recognizedId = pipeline.processFrame(frameBuffer.toList(), currentAzimuth.value)
            frameBuffer.clear()

            // 개발자 모드 시 강제 오버라이드. 아닐 경우 결과값에 따라 진행 혹은 실패 처리
            if (devModeManager.isDevMode) {
                processRecognition("geunjeongjeon")
            } else if (recognizedId != null) {
                processRecognition(recognizedId)
            } else {
                handleTargetLost()
                showFailedMessage(isSubElement = false)
            }
            
            isManualScan = false
        }
    }

    private fun startSubElementGazeTimer() {
        subElementGazeJob?.cancel()
        subElementGazeJob = viewModelScope.launch {
            frameBuffer.clear()
            // 2초 응시 중 1초, 2초 시점에 캡처
            delay(1000L)
            lastCapturedFrame?.let { frameBuffer.add(it) }
            delay(1000L)
            lastCapturedFrame?.let { frameBuffer.add(it) }

            if (_uiState.value is ARUiState.Detected) {
                val subResultId = pipeline.processSubElementFrame(frameBuffer.toList())
                frameBuffer.clear()

                val currentContent = (_uiState.value as? ARUiState.Detected)?.content
                val resolvedElem = subResultId?.let { subId ->
                    val elem = currentContent?.subElements?.find { it.id == subId }
                    if (elem != null) {
                        val isNew = subId !in _discoveredSubElements.value
                        _discoveredSubElements.value = _discoveredSubElements.value + subId
                        if (isNew) discoveryTracker.recordSubElement(subId)
                    }
                    elem
                }
                _recognizedSubElement.value = resolvedElem

                // 인식 실패 시: 카운터를 리셋해 자동 재시도 허용 (계속 스캔)
                if (resolvedElem == null) {
                    subElementValidFrames = 0
                }
            }
            subElementGazeJob = null
            isManualScan = false
        }
    }

    private fun handleTargetLost() {
        gazeTimerJob?.cancel()
        recognitionJob?.cancel()
        subElementGazeJob?.cancel()
        isManualScan = false
        _isObjectCandidateDetected.value = false
        consecutiveValidFrames = 0
        subElementValidFrames = 0
        subElementGazeJob = null
        _gazeProgress.value = 0f
        if (_uiState.value is ARUiState.GazeLoading || _uiState.value is ARUiState.Detecting) {
            _uiState.value = ARUiState.Idle
        }
        _suggestedQuestions.value = emptyList()
    }

    fun resetDetection() {
        handleTargetLost()
        _uiState.value = ARUiState.Idle
        _recognizedSubElement.value = null
        pipeline.clearResult()
    }

    private fun showFailedMessage(isSubElement: Boolean) {
        viewModelScope.launch {
            _recognitionFailedMessage.value = if (isSubElement) {
                when (targetLanguage) {
                    "en" -> "Details not recognized. Please move closer."
                    "ja" -> "詳細を認識できません。近づいてください。"
                    "zh" -> "无法识别细节。请靠近一点。"
                    else -> "세부 요소를 뚜렷하게 인식할 수 없습니다. 더 가까이 비춰주세요."
                }
            } else {
                when (targetLanguage) {
                    "en" -> "Building not recognized. Please move closer."
                    "ja" -> "認識できない建物です。もう少し近づいてください。"
                    "zh" -> "无法识别的建筑。请靠近一点。"
                    else -> "인식할 수 없는 건물입니다. 조금 더 가까이서 비춰주세요."
                }
            }
            kotlinx.coroutines.delay(3000L)
            _recognitionFailedMessage.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        arSensorProvider.stop()
        ttsManager.shutdown()  // stop() 포함, TTS 엔진 완전 해제
    }
}
