package com.example.ar_poc.ui.viewmodel

import android.graphics.Bitmap
import android.location.Location
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ar_poc.Strings
import com.example.ar_poc.data.location.ARSensorProvider
import com.example.ar_poc.core.events.DocentEvent
import com.example.ar_poc.core.events.EventBus
import com.example.ar_poc.core.gateway.DocentGateway
import com.example.ar_poc.di.DevModeManager
import com.example.ar_poc.domain.HeritageRecognitionPipeline
import com.example.ar_poc.domain.coordinator.NavigationCoordinator
import com.example.ar_poc.domain.coordinator.QuizCoordinator
import com.example.ar_poc.domain.coordinator.TourCourseCoordinator
import com.example.ar_poc.domain.coordinator.TourProgressState
import com.example.ar_poc.domain.coordinator.TtsController
import com.example.ar_poc.domain.model.TourCourse
import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.QuizQuestion
import com.example.ar_poc.domain.model.SubElement
import com.example.ar_poc.domain.repository.HeritageRepository
import com.example.ar_poc.domain.spatial.SpatialContext
import com.example.ar_poc.domain.spatial.SpatialContextProvider
import com.google.mlkit.vision.objects.DetectedObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    val gateway: DocentGateway,
    val eventBus: EventBus,
    private val devModeManager: DevModeManager,
    private val arSensorProvider: ARSensorProvider,
    private val ttsController: TtsController,
    private val quizCoordinator: QuizCoordinator,
    private val navigationCoordinator: NavigationCoordinator,
    private val tourCourseCoordinator: TourCourseCoordinator,
    private val spatialContextProvider: SpatialContextProvider,
) : ViewModel() {
    var targetLanguage: String = "ko"
        private set

    // ─────────────────────────────────────────────────────────────────────
    // UI State
    // ─────────────────────────────────────────────────────────────────────

    private val _uiState = MutableStateFlow<ARUiState>(ARUiState.Idle)
    val uiState: StateFlow<ARUiState> = _uiState.asStateFlow()

    private val _cameraMode = MutableStateFlow(CameraMode.LIVE)
    val cameraMode = _cameraMode.asStateFlow()

    private val _gazeProgress = MutableStateFlow(0f)
    val gazeProgress = _gazeProgress.asStateFlow()

    private val _isObjectCandidateDetected = MutableStateFlow(false)
    val isObjectCandidateDetected: StateFlow<Boolean> = _isObjectCandidateDetected.asStateFlow()

    val isDevMode: StateFlow<Boolean> = devModeManager.devModeState

    private val _recognizedSubElement = MutableStateFlow<SubElement?>(null)
    val recognizedSubElement: StateFlow<SubElement?> = _recognizedSubElement.asStateFlow()

    private val _recognitionFailedMessage = MutableStateFlow<String?>(null)
    val recognitionFailedMessage: StateFlow<String?> = _recognitionFailedMessage.asStateFlow()

    private val _discoveredHeritages = MutableStateFlow<Set<String>>(emptySet())
    val discoveredHeritages: StateFlow<Set<String>> = _discoveredHeritages.asStateFlow()

    private val _discoveredSubElements = MutableStateFlow<Set<String>>(emptySet())
    val discoveredSubElements: StateFlow<Set<String>> = _discoveredSubElements.asStateFlow()

    private val _suggestedQuestions = MutableStateFlow<List<String>>(emptyList())
    val suggestedQuestions: StateFlow<List<String>> = _suggestedQuestions.asStateFlow()

    // Q&A 채팅 내역 — BottomSheet 닫아도 유지, 유산 변경 시 초기화
    data class ChatMessage(val text: String, val isUser: Boolean, val isError: Boolean = false)
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    fun addChatMessage(msg: ChatMessage) {
        _chatMessages.value = _chatMessages.value + msg
    }
    fun updateLastChatMessage(msg: ChatMessage) {
        val list = _chatMessages.value.toMutableList()
        if (list.isNotEmpty()) { list[list.lastIndex] = msg }
        _chatMessages.value = list
    }
    fun clearChat() { _chatMessages.value = emptyList() }

    private val _showArOverlay = MutableStateFlow(true)
    val showArOverlay: StateFlow<Boolean> = _showArOverlay.asStateFlow()

    var labels = Strings.getCameraLabels(targetLanguage)
        private set

    // ─────────────────────────────────────────────────────────────────────
    // Sensor (ARSensorProvider 위임)
    // ─────────────────────────────────────────────────────────────────────

    val currentAzimuth: StateFlow<Float> = arSensorProvider.azimuth
    val arPitch: StateFlow<Float> = arSensorProvider.pitch
    val isPhoneFlat: StateFlow<Boolean> = arSensorProvider.isPhoneFlat
    val debugInfo = pipeline.debugInfo

    // ─────────────────────────────────────────────────────────────────────
    // Data (Repository 위임)
    // ─────────────────────────────────────────────────────────────────────

    val heritageList: List<HeritageContent> = repository.getHeritageList()
    val poiList: List<Poi> = repository.getAllPois()

    // ─────────────────────────────────────────────────────────────────────
    // XR 엔진 (SpatialContextProvider 위임)
    // ─────────────────────────────────────────────────────────────────────

    val spatialContext: StateFlow<SpatialContext> =
        spatialContextProvider.getSpatialContext()
            .stateIn(viewModelScope, SharingStarted.Eagerly, SpatialContext.EMPTY)

    val isInsidePalace: StateFlow<Boolean> =
        spatialContext.map { it.isInsideSite }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val currentLocation: StateFlow<Location?> =
        spatialContext.map { ctx ->
            if (!ctx.hasValidLocation) null
            else Location("SpatialProvider").apply {
                latitude = ctx.userLat
                longitude = ctx.userLng
                accuracy = ctx.accuracyM
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    // ─────────────────────────────────────────────────────────────────────
    // Navigation (NavigationCoordinator 위임)
    // ─────────────────────────────────────────────────────────────────────

    private val navState = navigationCoordinator.bind(
        spatialContext = spatialContext,
        discoveredHeritages = _discoveredHeritages,
        languageProvider = { targetLanguage },
        scope = viewModelScope
    )
    val nextPoiIds: StateFlow<List<String>> = navState.nextPoiIds
    val navigationHint: StateFlow<String?> = navState.navigationHint

    // ─────────────────────────────────────────────────────────────────────
    // TTS (TtsController 위임)
    // ─────────────────────────────────────────────────────────────────────

    val isTtsEnabled: StateFlow<Boolean> = ttsController.isEnabled
    fun toggleTts() = ttsController.toggle()

    // ─────────────────────────────────────────────────────────────────────
    // Quiz (QuizCoordinator 위임)
    // ─────────────────────────────────────────────────────────────────────

    val pendingQuizQuestions: StateFlow<List<QuizQuestion>> = quizCoordinator.pendingQuestions
    fun clearPendingQuiz() = quizCoordinator.clearPending()
    fun markQuizCompleted(heritageId: String) = quizCoordinator.markCompleted(heritageId)
    fun getDiscoveryTime(heritageId: String): Long? = quizCoordinator.getDiscoveryTime(heritageId)
    fun requestQuiz(heritageId: String) = quizCoordinator.requestQuiz(heritageId)
    fun hasQuiz(heritageId: String): Boolean = quizCoordinator.hasQuiz(heritageId)

    // ─────────────────────────────────────────────────────────────────────
    // Tour Course (40/60/90분 관람 코스)
    // ─────────────────────────────────────────────────────────────────────

    val tourCourses: List<TourCourse> = tourCourseCoordinator.getAllCourses()
    val selectedTourCourse: StateFlow<TourCourse?> = tourCourseCoordinator.selectedCourse
    val visitedWaypointOrders: StateFlow<Set<Int>> = tourCourseCoordinator.visitedOrders
    val isNavigating: StateFlow<Boolean> = tourCourseCoordinator.isNavigating

    /** GPS-driven auto-check + next-waypoint + progress% + completion. */
    private val tourProgress: TourProgressState =
        tourCourseCoordinator.bindProgress(spatialContextProvider, viewModelScope)

    val nextCourseWaypoint = tourProgress.nextWaypoint
    val courseProgressPercent = tourProgress.progressPercent
    val courseCompleted = tourProgress.isCompleted

    /**
     * 내비게이션 HUD용 다음 웨이포인트 정보.
     * - 사용자 현위치 → 다음 stop까지 거리(m)와 베어링(절대 방위각, 0=북)
     * - 현재 폰 방향과의 상대 각도도 함께 제공 (화살표 회전용)
     */
    data class NavHudInfo(
        val waypointName: String,
        val distanceM: Int,
        val absoluteBearingDeg: Float,  // 0=북, 90=동
        val relativeBearingDeg: Float   // 폰이 보는 방향 기준 상대 각도 (0=정면, 음수=왼쪽)
    )

    val navHudInfo: StateFlow<NavHudInfo?> =
        kotlinx.coroutines.flow.combine(
            isNavigating,
            nextCourseWaypoint,
            currentLocation,
            currentAzimuth
        ) { navigating, wp, loc, az ->
            if (!navigating || wp == null || loc == null) return@combine null
            val bearing = com.example.ar_poc.domain.spatial.SpatialCalculator.calcBearing(
                loc.latitude, loc.longitude, wp.latitude, wp.longitude
            )
            val distance = com.example.ar_poc.domain.spatial.SpatialCalculator.calcDistanceM(
                loc.latitude, loc.longitude, wp.latitude, wp.longitude
            ).toInt()
            val relative = (((bearing - az) + 540f) % 360f) - 180f
            NavHudInfo(
                waypointName = wp.localizedName(targetLanguage),
                distanceM = distance,
                absoluteBearingDeg = bearing,
                relativeBearingDeg = relative
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun selectTourCourse(courseId: String) = tourCourseCoordinator.selectCourse(courseId)
    fun clearTourCourse() = tourCourseCoordinator.clearSelection()
    fun toggleWaypointVisited(order: Int) = tourCourseCoordinator.toggleVisited(order)
    fun startCourseNavigation() = tourCourseCoordinator.startNavigation()
    fun stopCourseNavigation() = tourCourseCoordinator.stopNavigation()

    // ─────────────────────────────────────────────────────────────────────
    // Recognition Flow (ARViewModel 자체 책임 — UI 상태 전이)
    // ─────────────────────────────────────────────────────────────────────

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

    fun setLanguage(lang: String) {
        if (targetLanguage != lang) {
            targetLanguage = lang
            labels = Strings.getCameraLabels(targetLanguage)
        }
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

    fun toggleDevMode() {
        devModeManager.toggle()
        if (!devModeManager.isDevMode) resetDetection()
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

        if (_uiState.value is ARUiState.Detected) {
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

    fun resetDetection() {
        handleTargetLost()
        _uiState.value = ARUiState.Idle
        _recognizedSubElement.value = null
        clearChat()
        pipeline.clearResult()
    }

    // ─────────────────────────────────────────────────────────────────────
    // Private — Recognition internals
    // ─────────────────────────────────────────────────────────────────────

    private fun processRecognition(landmarkId: String) {
        recognitionJob?.cancel()
        recognitionJob = viewModelScope.launch {
            val content = repository.getHeritageContent(landmarkId)
            if (content == null) {
                android.util.Log.w("ARViewModel", "Heritage not found: $landmarkId")
                return@launch
            }

            val isNewDiscovery = landmarkId !in _discoveredHeritages.value
            _discoveredHeritages.value = _discoveredHeritages.value + landmarkId

            // 이벤트 발행: 유산이 실제로 확정 인식된 시점
            eventBus.publish(
                DocentEvent.HeritageConfirmed(
                    heritageId = landmarkId,
                    isNewDiscovery = isNewDiscovery
                )
            )

            // 퀴즈 + 발견 기록 → QuizCoordinator 위임
            // (퀴즈가 실제로 트리거되면 QuizCoordinator가 QuizTriggered 발행)
            quizCoordinator.onHeritageDiscovered(landmarkId, isNewDiscovery)

            _suggestedQuestions.value = Strings.getHeritageSuggestedQuestions(landmarkId, targetLanguage)
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

            // TTS → TtsController 위임
            val descToSpeak = content.shortDescMap[targetLanguage] ?: content.shortDescription
            ttsController.speakDescription(descToSpeak, targetLanguage)
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
                if (elapsed == 1000L || elapsed == 2000L || elapsed == 3000L) {
                    lastCapturedFrame?.let { frameBuffer.add(it) }
                }
            }

            _isObjectCandidateDetected.value = false
            consecutiveValidFrames = 0
            _uiState.value = ARUiState.Detecting

            val recognizedId = pipeline.processFrame(frameBuffer.toList(), currentAzimuth.value)
            frameBuffer.clear()

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

    private var subElementRetryCount = 0
    private companion object {
        const val SUB_ELEMENT_MAX_RETRIES = 3
        const val SUB_ELEMENT_COOLDOWN_MS = 2000L
    }

    /** 세부 유물을 다시 스캔하기 위해 현재 인식 결과를 초기화한다. */
    fun clearSubElement() {
        _recognizedSubElement.value = null
        subElementValidFrames = 0
        subElementRetryCount = 0
    }

    private fun startSubElementGazeTimer() {
        subElementGazeJob?.cancel()
        subElementGazeJob = viewModelScope.launch {
            frameBuffer.clear()
            delay(1000L)
            lastCapturedFrame?.let { frameBuffer.add(it) }
            delay(1000L)
            lastCapturedFrame?.let { frameBuffer.add(it) }

            if (_uiState.value is ARUiState.Detected) {
                val subResultId = pipeline.processSubElementFrame(frameBuffer.toList())
                frameBuffer.clear()

                val currentContent = (_uiState.value as? ARUiState.Detected)?.content
                val resolvedElem = subResultId?.let { subId ->
                    // 이미 인식된 동일 subElement면 스킵 (API 낭비 방지)
                    if (_recognizedSubElement.value?.id == subId) return@let _recognizedSubElement.value
                    val elem = currentContent?.subElements?.find { it.id == subId }
                    if (elem != null) {
                        val isNew = subId !in _discoveredSubElements.value
                        _discoveredSubElements.value = _discoveredSubElements.value + subId
                        if (isNew) quizCoordinator.onSubElementDiscovered(subId)
                    }
                    elem
                }
                _recognizedSubElement.value = resolvedElem

                if (resolvedElem == null) {
                    subElementRetryCount++
                    subElementValidFrames = 0

                    if (subElementRetryCount >= SUB_ELEMENT_MAX_RETRIES) {
                        // 최대 재시도 초과 → 사용자에게 실패 메시지 + 쿨다운
                        showFailedMessage(isSubElement = true)
                        subElementRetryCount = 0
                        delay(SUB_ELEMENT_COOLDOWN_MS)
                    }
                    // 아니면 subElementValidFrames = 0 상태로 ML Kit가 다시 3프레임 감지하면 자동 재시도
                } else {
                    subElementRetryCount = 0
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
            delay(3000L)
            _recognitionFailedMessage.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        arSensorProvider.stop()
        ttsController.shutdown()
        lastCapturedFrame = null
        frameBuffer.clear()
    }
}
