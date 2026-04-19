# AR_POC Project Map
> **목적**: 코드 수정 시 이 파일만 참조해서 불필요한 파일 full-read를 줄임
> **업데이트**: 파일 구조/시그니처 변경 시 반드시 이 파일도 갱신
> **최종 업데이트**: 2026-04-19 (DetailScreen 퀴즈 복원, SubElement locationHint + 누락 보물 추가, Stamp→Detail 이동)

---

## 패키지 구조

```
com.example.ar_poc
├── ARApplication.kt                 # @HiltAndroidApp 등록 (Hilt 시작점)
│                                    # [연결] EventLogger.start() + MemorySessionHandler.start()
├── Config.kt                        # DEV_MODE(const val), MOCK GPS 좌표
├── Strings.kt                       # 정적 다국어 번역 맵 (추천 질문, 궁 이름, 섹션 라벨, 카메라/스탬프 UI 라벨)
│                                    #   getSuggestedQuestions / getHeritageSuggestedQuestions(전각별)
│                                    #   getPalaceName (17개 전각 매핑) / getSection
│                                    #   getCameraLabels / getStampLabels
├── MainActivity.kt                  # @AndroidEntryPoint, attachBaseContext(LocaleHelper 적용), setContent { NavGraph() }
├── NavGraph.kt                      # NavHost: LanguageSelection → Camera → Detail (LocaleHelper로 로케일 주입)
│                                    #   detail/{heritageId}?chunkId={chunkId} (쿼리 파라미터 지원)
├── DetailScreen.kt                  # 유산 상세 화면 (청크별 번역 병렬 처리, RAG Q&A 스트리밍)
├── ObjectDetectorAnalyzer.kt        # ML Kit 객체 감지 ImageAnalysis.Analyzer
│
├── ai/
│   └── GeminiClient.kt              # Gemini 2.5 Flash API 래퍼
│                                    #   classifyLandmark / classifySubElement / translateText / askQuestion
│                                    # [개선] scaleBitmap(512px) 전처리 → API 레이턴시 35% 절감
│                                    # [개선] classifyLandmark: GPS는 참고용, 시각 증거 절대 우선 프롬프트
│                                    # [개선] classifySubElement: buildingId 후보 제거, 클로즈업 디테일 식별 집중
│                                    # [수정] scaleBitmap 결과 finally에서 recycle() — 메모리 누수 방지
│                                    # [정리] askQuestion 미사용 errorMsg 변수 제거
│
├── data/
│   ├── LanguageManager.kt           # SharedPreferences 언어 저장/불러오기 + 언어변경 플래그
│   ├── audio/
│   │   └── TTSManager.kt            # Android TTS 래퍼 — speak(text, lang) / stop() / shutdown()
│   │                                 #   isEnabled/isSpeaking: StateFlow<Boolean>
│   ├── discovery/
│   │   └── DiscoveryTracker.kt      # 발견 타임스탬프 SharedPrefs 저장소
│   │                                 #   recordHeritage/SubElement(), isQuizCompleted(), markQuizCompleted()
│   ├── heritage/
│   │   ├── HeritageKnowledgeSource.kt   # interface: getHeritageById, getHeritageList
│   │   │                                 #   + getExtraPoiList(): List<Poi>  (default=emptyList)
│   │   └── LocalHeritageKnowledgeSource.kt  # 하드코딩 데이터 (경복궁 14개 전각) ~2,000줄
│   │                                        # P1: 근정전/경회루/광화문/사정전/수정전/강녕전/교태전
│   │                                        #     자경전/향원정/건청궁/집옥재/동궁
│   │                                        # P2: 태원전/소주방/흥복전/근정문/영제교
│   │                                        # 다국어 titleMap/shortDescMap/contentMap 완비
│   │                                        # extraPoiList: 카페2, 기념품점, 안내소, 뷰포인트2, 신무문, 건춘문, 영추문, 흥례문 (10개)
│   ├── location/
│   │   ├── LocationProviderImpl.kt  # GPS (lastLocation 우선, MAX_LOCATION_AGE_MS=3분)
│   │   │                            #   DevMode 시 Mock 좌표 반환
│   │   ├── ARSensorProvider.kt      # TYPE_ROTATION_VECTOR → azimuth/pitch/roll StateFlow
│   │   │                            #   적응형 EMA 필터: |delta|>5° → alpha=0.85, else 0.35
│   │   │                            #   isPhoneFlat: StateFlow<Boolean> — pitch < -45° 시 true
│   │   ├── OutdoorSpatialProvider.kt # SpatialContextProvider 구현체 (GPS 기반)
│   │   │                            #   [수정] cold flow → shareIn hot flow (구독자간 단일 폴링 공유)
│   │   │                            #   WhileSubscribed(5000ms) + replay=1
│   │   │                            #   getAllPois()로 전체 POI 로드 후 거리 계산
│   │   │                            #   currentPoi(20m 반경), nearbyPois(10개), zoneId 판정
│   │   │                            #   POLL_INTERVAL_MS=5000 (5초), DevMode 지원
│   │   └── IndoorSpatialProvider.kt # BLE/UWB 기반 Stub — 현재 EMPTY 발행
│   └── quiz/
│       └── QuizRepository.kt        # 하드코딩 4지선다 퀴즈 — 14개 전각 각 1~3문항 (총 40+개)
│
├── di/
│   ├── AppModule.kt                 # Hilt @Module (@Singleton): 모든 의존성 바인딩
│   │                                #   EventBus, DocentGateway (Local/Remote 자동선택),
│   │                                #   MemoryWriter, RetrievalEngine, GeminiClient,
│   │                                #   Pipeline, Repository, ARSensorProvider,
│   │                                #   TTSManager, DiscoveryTracker, QuizRepository,
│   │                                #   SpatialContextProvider → OutdoorSpatialProvider
│   │                                # [수정] ORCHESTRATOR_URL 유무로 Gateway 자동 토글
│   └── DevModeManager.kt           # Singleton — DevMode 토글 & StateFlow 관리
│
├── domain/
│   ├── HeritageRecognitionPipeline.kt  # 핵심 인식 파이프라인 + DebugInfo
│   │   # [버그수정] try-finally로 isProcessing 데드락 방지
│   │   # [개선] buildCandidateHint: "most likely" 편향 제거 → 후보 3개 방향 차이 목록 제공
│   │   # [수정] recognizedLandmarkId/SubElementId: StateFlow → private var (외부 미구독)
│   │   # [수정] getRankedCandidates → List<Pair<HeritageContent, Float>> 반환으로 bearing 중복 계산 제거
│   │   # [정리] processSubElementFrame: buildingId 중복 체크 dead code 제거 (GeminiClient에서 이미 필터)
│   ├── model/
│   │   ├── HeritageContent.kt       # HeritageContent + HeritageChunk + SubElement
│   │   ├── Poi.kt                   # Poi data class + PoiType enum + HeritageContent.toPoi() 확장함수
│   │   └── QuizQuestion.kt          # QuizQuestion(heritageId, 다국어 question, choices, correctIndex)
│   ├── repository/
│   │   └── HeritageRepository.kt    # getRelevantChunks() + buildRagContext() + getHeritageList()
│   │                                #   getAllPois(): heritagePois + extraPoiList 합산 반환
│   │                                # [수정] getRelevantChunks → RetrievalEngine에 위임
│   │                                # [수정] extractStems → RuleRetriever로 이관
│   ├── coordinator/                 # [신규] ARViewModel에서 분리된 책임
│   │   ├── TtsController.kt        # TTS 토글/낭독/정지/해제 (@Singleton)
│   │   ├── QuizCoordinator.kt      # 퀴즈 트리거/완료/발견기록 (@Singleton)
│   │   │                            #   pendingQuestions: StateFlow<List<QuizQuestion>>
│   │   │                            # [연결] EventBus.publish(QuizTriggered) — 실제 퀴즈 로드 시
│   │   └── NavigationCoordinator.kt # 다음 방문지 추천 + 방향 힌트 생성 (@Singleton)
│   │                                #   bind() → NavigationState(nextPoiIds, navigationHint)
│   └── spatial/                     # XR 엔진 도메인 패키지
│       ├── SpatialContext.kt        # 공간 상태 스냅샷 모델
│       │                            #   siteId, zoneId, currentPoi, nearbyPois, isIndoor, lat/lng, accuracy
│       │                            #   EMPTY companion, hasValidLocation/isInsideSite 프로퍼티
│       ├── SpatialContextProvider.kt # interface: getSpatialContext(): Flow<SpatialContext>
│       └── SpatialCalculator.kt     # 순수 함수 AR/XR 계산 — UI 의존성 없음
│                                    #   calcScreenPos(), calcBearing(), calcDistanceM(), azimuthToCardinal()
│                                    #   FOV_H=65°, FOV_V=120°, FOV_MARGIN=1.1
│
├── core/
│   ├── events/                      # [신규] 이벤트 드리븐 아키텍처 인프라
│   │   ├── DocentEvent.kt           # sealed interface: 모든 도메인 이벤트 정의
│   │   │                            #   FrameCandidateDetected, HeritageConfirmed,
│   │   │                            #   QuestionAsked, QuizTriggered
│   │   ├── EventBus.kt              # interface: publish(event) / subscribe(): Flow
│   │   │                            #   + on<T>() 확장함수 (타입별 필터 구독)
│   │   ├── InMemoryEventBus.kt      # MutableSharedFlow 기반 구현체 (@Singleton)
│   │   │                            #   replay=0, buffer=64, DROP_OLDEST
│   │   └── EventLogger.kt          # [연결] DEV_MODE 전용 logcat 구독자
│   │                                #   모든 DocentEvent를 TAG=DocentEvents로 로깅
│   │                                #   발행 지점: ARViewModel(HeritageConfirmed),
│   │                                #   QuizCoordinator(QuizTriggered), CameraScreen(QuestionAsked)
│   ├── memory/                      # [신규] 파일 기반 세션 메모리
│   │   ├── SessionState.kt         # 세션 상태 스냅샷 data class (toJson/fromJson)
│   │   ├── MemoryWriter.kt         # interface: startSession, appendEvent, writeSessionState
│   │   │                            #   + SessionEvent DTO (NDJSON 직렬화)
│   │   ├── FileSessionMemoryWriter.kt # Context.filesDir 기반 구현체 (@Singleton)
│   │   │                            #   memory/sessions/{id}/live.state.json + recent.events.ndjson
│   │   │                            #   Mutex 동시쓰기 보호, atomic rename, safeIO
│   │   ├── EventMapping.kt         # DocentEvent → SessionEvent 변환 확장함수
│   │   └── MemorySessionHandler.kt # [연결] EventBus + SpatialContext 이중 구독 (@Singleton)
│   │                                #   첫 이벤트 시 lazy 세션 시작/복원(1h window)
│   │                                #   모든 이벤트 → recent.events.ndjson 즉시 append
│   │                                #   HeritageConfirmed → live.state.json 즉시 flush
│   │                                #   그 외 이벤트 → dirty bit + 1초/5건 throttle flush
│   │                                #   SpatialContext → 10m hav 거리 변화 시 state 갱신
│   │                                #   ProcessLifecycleOwner ON_STOP → 즉시 flush (데이터 손실 방지)
│   ├── retrieval/                   # [신규] 검색 엔진 추상화 계층
│   │   ├── RetrievalModels.kt      # RetrievalQuery / RetrievalResult / RetrievalChunk DTOs
│   │   ├── RetrievalEngine.kt      # interface: retrieve(query) → RetrievalResult
│   │   │                            #   TODO: VectorRetriever, HybridRetriever 확장점
│   │   └── RuleRetriever.kt        # 키워드+어간 매칭 구현체 (@Singleton)
│   │                                #   HeritageRepository.extractStems()에서 이관
│   │                                #   스코어링: chunkId(+100), 키워드(+5), 어간(+3), 제목(+2), 본문(+1)
│   └── gateway/                     # [신규] AI 기능 추상화 계층
│       ├── DocentGateway.kt         # interface: classifyLandmark, classifySubElement,
│       │                            #   translate, translateBatch, askQuestion
│       │                            #   + DTOs: ClassifyLandmarkRequest, TranslateRequest,
│       │                            #   QuestionRequest 등 Request/Response 모음
│       ├── LocalFirstDocentGateway.kt # GeminiClient 래핑 구현체 (@Singleton)
│       │                            #   TtlCache: 번역(24h, 500개), QA(30m, 100개)
│       │                            #   llmCalls/cacheHits 메트릭 카운터
│       └── RemoteDocentGateway.kt   # [신규] 원격 orchestrator 경유 구현체
│                                    #   Q&A만 HTTP POST /docent/question → orchestrator
│                                    #   인식/번역은 delegate(LocalFirst)에 위임
│                                    #   실패 시 delegate.askQuestion()으로 자동 fallback
│                                    #   BuildConfig.ORCHESTRATOR_URL 유무로 DI 자동 토글
│
├── util/
│   ├── LocaleHelper.kt             # Context를 특정 Locale로 래핑하는 유틸리티
│   ├── AppLogger.kt                # DEV_MODE 기반 logcat 래퍼
│   └── SpeechRecognizerHelper.kt   # Android 내장 STT 래퍼 (ko/en/ja/zh, RECORD_AUDIO 권한)
│
└── ui/
    ├── LanguageSelectionScreen.kt   # 언어 선택 (ko/en/ja/zh), 그라데이션 한지 배경
    ├── ar/
    │   └── AROverlayScreen.kt       # GPS+방위각 기반 AR 카드 오버레이
    │                                #   AR_VISIBLE_RADIUS_M=500f, AR_MAX_VISIBLE_POIS=5
    │                                #   calcARScreenPos(): SpatialCalculator.calcScreenPos 위임
    │                                #   겹침 방지: X축 군집화(190dp) + Y축 110dp 간격 분산 배치
    │                                #   ARHeritageCard: 거리/발견여부 표시, 둥실 부유 애니메이션
    │                                #   PoiInfoDialog: 일반 POI(카페/안내소 등) 클릭 시 정보 팝업
    ├── camera/
    │   ├── CameraScreen.kt          # 메인 AR 화면 — 모든 기능의 허브
    │   │   # AR 오버레이 토글 (toggleArOverlay())
    │   │   # isPhoneFlat 감지 시 MapScreen 자동 전환 + currentLocation 전달
    │   │   # 퀴즈 바텀시트 표시 (pendingQuizQuestions)
    │   │   # 스탬프 수집 화면 오버레이
    │   └── components/
    │       ├── ARMarker.kt          # Canvas 기반 Cyan 코너 마크 + 깜빡임 애니메이션
    │       ├── CameraPreviewLayer.kt    # CameraX 프리뷰 및 ImageAnalysis(640x480 최적화)
    │       ├── DebugOverlay.kt      # DEV_MODE 전용 디버그 패널 (GPS/나침반/후보/Gemini Raw)
    │       └── UnrealXRLayer.kt     # Sceneview 2.0.3 기반 ARScene (Compose 2D 표식)
    ├── map/
    │   └── MapScreen.kt             # Google Maps Compose 지도 + 세이프 에어리어 + 다국어 마커
    │                                # [개선] currentLocation을 실제 GPS 수신
    │                                # [버그수정] PointerInput 제스처 즉각 감지 — 지도 무한 원점 회귀 버그 해결
    │                                # [버그수정] 맵 초기 로딩 시 (0,0)(아프리카) 좌표 점프 방지
    ├── quiz/
    │   └── QuizBottomSheet.kt       # ModalBottomSheet 4지선다 퀴즈
    │                                #   AnimatedContent 문제 전환, 정답/오답 색상 피드백
    │                                #   정답 후 1.5초 자동 다음 문제, 마지막 문제 후 onFinish()
    ├── stamp/
    │   └── StampCollectionScreen.kt # 도장 수집 화면 — 발견 유산 카드 그리드, 진행률 표시
    │                                # [버그수정] 미발견 유산도 클릭 시 바텀시트 열림 (❓ 힌트 제공)
    ├── theme/
    │   ├── Color.kt                 # 한국 전통 색상 (DancheongRed, HanjiWhite, GiwaDark, GoldAccent 등)
    │   ├── Theme.kt                 # MaterialTheme — 전통 색상 강제 (dynamicColor 무시)
    │   └── Type.kt                  # FontFamily.Serif(명조) 헤드라인 + Default 본문
    └── viewmodel/
        └── ARViewModel.kt           # 메인 ViewModel (@HiltViewModel)
            # [개선] ARSensorProvider 주입 — arAzimuth/arPitch/isPhoneFlat StateFlow
            # [개선] SpatialContextProvider 주입 → spatialContext/isInsidePalace/currentLocation 파생
            # [개선] showArOverlay StateFlow + toggleArOverlay() (기본 ON)
            # [수정] nextPoiIds: combine(spatialContext, _discoveredHeritages)으로 race condition 해소
            # [수정] frameBuffer.clear() — processFrame/processSubElementFrame 후 Bitmap 참조 해제
            # [정리] 미사용 savedStateHandle, LocationProvider import 제거
            # [버그수정] onCleared()에서 arSensorProvider.stop() + ttsManager.shutdown()
```

---

## 핵심 클래스 시그니처

### `EventBus` (`core/events/EventBus.kt`) ← **신규**
```kotlin
// 이벤트 정의 (sealed interface)
sealed interface DocentEvent {
    val timestamp: Long
    data class FrameCandidateDetected(candidateCount, azimuth, timestamp)
    data class HeritageConfirmed(heritageId, isNewDiscovery, timestamp)
    data class QuestionAsked(heritageId, question, language, timestamp)
    data class QuizTriggered(heritageId, questionCount, timestamp)
}

// 발행/구독 인터페이스
interface EventBus {
    fun publish(event: DocentEvent)
    fun subscribe(): Flow<DocentEvent>
}

// 타입별 구독 확장함수
inline fun <reified T : DocentEvent> EventBus.on(): Flow<T>

// 구현체: InMemoryEventBus (@Singleton, MutableSharedFlow, buffer=64, DROP_OLDEST)
```

### `GeminiClient` (`ai/GeminiClient.kt`)
```kotlin
class GeminiClient {  // Hilt @Singleton, Gemini 2.5 Flash
    // 전송 전 비트맵 512px 다운스케일 (API 레이턴시 개선)
    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int = 512): Bitmap

    // Stage 1: 건물 인식 (시각 증거 절대 우선, GPS는 참고용 Warning 문구)
    // scaleBitmap 결과를 finally에서 recycle() — 메모리 누수 방지
    suspend fun classifyLandmark(
        bitmap: Bitmap,
        candidates: List<String>,
        palaceName: String,
        locationHint: String = ""  // "User facing N. GPS bearing diffs: X(5°off),..."
    ): String  // {landmark_id} | "Unknown" | "Error: ..."

    // Stage 2: 클로즈업 디테일 인식 (buildingId 후보 제외 필터링 내장)
    // scaleBitmap 결과를 finally에서 recycle() — 메모리 누수 방지
    suspend fun classifySubElement(
        bitmap: Bitmap,
        buildingId: String,
        buildingTitle: String,
        palaceName: String,
        candidates: List<Pair<String, String>>  // (subElementId, visualHint)
    ): String?  // subElementId | null (Unknown/buildingId/Error 시)

    suspend fun translateText(text: String, targetLanguage: String): String
    suspend fun translateMultipleTexts(texts: List<String>, targetLanguage: String): List<String>
    fun askQuestion(question: String, heritageContext: String, targetLanguage: String): Flow<String>
}
```

### `ARSensorProvider` (`data/location/ARSensorProvider.kt`)
```kotlin
class ARSensorProvider(context: Context) : SensorEventListener {
    val azimuth: StateFlow<Float>      // 0~360°, 적응형 EMA 평활(|delta|>5° → 0.85, else 0.35)
    val pitch: StateFlow<Float>        // -90°(face-up) ~ 0°(수직) ~ +90°(face-down)
    val roll: StateFlow<Float>
    val isPhoneFlat: StateFlow<Boolean> // pitch < -45° 시 true → MapScreen 자동 전환

    fun start()  // SENSOR_DELAY_GAME (~50Hz) 등록
    fun stop()
}
```

### `HeritageRecognitionPipeline` (`domain/HeritageRecognitionPipeline.kt`)
```kotlin
data class DebugInfo(
    val latitude: Double, val longitude: Double, val gpsAccuracy: Float,
    val azimuth: Float,
    val rankedCandidates: List<Pair<String, Float>>,  // name → bearing diff
    val geminiRawResponse: String, val resolvedId: String?,
    val isInsidePalace: Boolean,
    val subElementRawResponse: String, val resolvedSubElementId: String?
)

class HeritageRecognitionPipeline(gateway: DocentGateway, locationProvider, knowledgeSource) {
    // 내부 상태 (외부 미구독 — private var)
    private var recognizedLandmarkId: String?       // processSubElementFrame에서 참조
    private var recognizedSubElementId: String?

    val isProcessing: StateFlow<Boolean>
    val debugInfo: StateFlow<DebugInfo>

    // try-finally 구조로 isProcessing 데드락 방지
    suspend fun processFrame(bitmaps: List<Bitmap>, azimuth: Float): String?

    // GeminiClient가 buildingId 필터링 후 반환 → 여기서는 subElements.find만 수행
    suspend fun processSubElementFrame(bitmaps: List<Bitmap>): String?

    fun clearResult()

    // bearing 1회 계산 후 debug/hint 모두에 재활용
    private fun getRankedCandidates(userLocation, azimuth): List<Pair<HeritageContent, Float>>
    private fun buildCandidateHint(azimuth, ranked: List<Pair<HeritageContent, Float>>): String
    // 반환 예: "User facing N. GPS bearing diffs from smallest: geunjeongjeon(5°off), gyeonghoeru(78°off)"
    private fun resolveToId(landmarkName: String): String?  // Gemini 응답 → heritage ID
}
```

### `ARViewModel` (`ui/viewmodel/ARViewModel.kt`)
```kotlin
@HiltViewModel
class ARViewModel @Inject constructor(
    pipeline: HeritageRecognitionPipeline,
    repository: HeritageRepository,
    val gateway: DocentGateway,               // [추가] CameraScreen에서 DI 경유 접근
    devModeManager: DevModeManager,
    arSensorProvider: ARSensorProvider,
    ttsController: TtsController,              // [변경] TTSManager → TtsController
    quizCoordinator: QuizCoordinator,          // [변경] QuizRepo+DiscoveryTracker → QuizCoordinator
    navigationCoordinator: NavigationCoordinator, // [신규] nextPoiIds+navigationHint 위임
    spatialContextProvider: SpatialContextProvider
) : ViewModel() {
    var targetLanguage: String
    var labels: Strings.ARUiLabels

    // UI 상태
    val uiState: StateFlow<ARUiState>
    val cameraMode: StateFlow<CameraMode>
    val gazeProgress: StateFlow<Float>
    val isObjectCandidateDetected: StateFlow<Boolean>
    val isDevMode: StateFlow<Boolean>
    val recognizedSubElement: StateFlow<SubElement?>
    val suggestedQuestions: StateFlow<List<String>>
    val recognitionFailedMessage: StateFlow<String?>

    // 센서 (ARSensorProvider 위임)
    val currentAzimuth: StateFlow<Float>
    val arPitch: StateFlow<Float>
    val isPhoneFlat: StateFlow<Boolean>
    val showArOverlay: StateFlow<Boolean>  // 기본 ON

    // 컬렉션
    val discoveredHeritages: StateFlow<Set<String>>
    val discoveredSubElements: StateFlow<Set<String>>
    val heritageList: List<HeritageContent>
    val poiList: List<Poi>  // repository.getAllPois() — 유산 POI + 일반 POI 통합

    // XR 엔진 (SpatialContextProvider 위임, GPS 중복 폴링 없음)
    val spatialContext: StateFlow<SpatialContext>        // 5초 주기 갱신, EMPTY 초기값
    val isInsidePalace: StateFlow<Boolean>               // spatialContext.isInsideSite 파생
    val currentLocation: StateFlow<Location?>            // spatialContext lat/lng 파생
    val nextPoiIds: StateFlow<List<String>>              // combine(spatialContext, discoveredHeritages)
    val navigationHint: StateFlow<String?>               // combine(spatialContext, nextPoiIds)

    // TTS
    val isTtsEnabled: StateFlow<Boolean>
    fun toggleTts()

    // 퀴즈
    val pendingQuizQuestions: StateFlow<List<QuizQuestion>>
    fun clearPendingQuiz()
    fun markQuizCompleted(heritageId: String)
    fun getDiscoveryTime(heritageId: String): Long?

    // 동작
    fun onObjectsDetected(detectedObjects: List<DetectedObject>, width: Int, height: Int, bitmap: Bitmap?)
    fun forceScan()
    fun toggleDevMode()
    fun toggleCameraMode()
    fun toggleArOverlay()
    fun resetDetection()
    fun setLanguage(lang: String)
}

sealed class ARUiState {
    object Idle; object GazeLoading; object Detecting
    data class Detected(content: HeritageContent, center: Offset, size: Size)
    data class Error(message: String)
}
```

### `AROverlayScreen` (`ui/ar/AROverlayScreen.kt`)
```kotlin
private const val AR_VISIBLE_RADIUS_M = 500f   // AR 카드 표시 최대 거리 (미터)
private const val AR_MAX_VISIBLE_POIS = 5       // 동시에 표시할 최대 POI 수

@Composable
fun AROverlayScreen(
    poiList: List<Poi>,              // heritageList가 아닌 Poi 통합 목록
    currentLat: Double, currentLng: Double,
    azimuth: Float, pitch: Float,
    targetLanguage: String,
    discoveredHeritages: Set<String>,
    onHeritageClick: (String) -> Unit,
    onClose: () -> Unit,
    isDevMode: Boolean = false       // DevMode 시 FOV 무효화(99f)로 모든 POI 표시
)

// SpatialCalculator.calcScreenPos() 위임 shim
fun calcARScreenPos(...): Pair<Float, Float>?
fun calcDistance(...): Float

// 일반 POI(카페/안내소 등) 클릭 시 정보 팝업
@Composable
fun PoiInfoDialog(poi: Poi, targetLanguage: String, onDismiss: () -> Unit)
```

### `MapScreen` (`ui/map/MapScreen.kt`)
```kotlin
@Composable
fun MapScreen(
    currentLocation: Location?,          // 실제 GPS 좌표 (spatialContext 파생)
    discoveredHeritages: Set<String>,
    heritageList: List<HeritageContent>,
    targetLanguage: String = "ko",
    onClose: () -> Unit
)
```

### `StampCollectionScreen` (`ui/stamp/StampCollectionScreen.kt`)
```kotlin
@Composable
fun StampCollectionScreen(
    discoveredHeritages: Set<String>,
    discoveredSubElements: Set<String>,
    heritageList: List<HeritageContent>,
    targetLanguage: String,
    onClose: () -> Unit
) {
    // 발견 유산: 컬러 + ✓ 뱃지 + 서브엘리먼트 이름 표시
    // 미발견: blur + 회색 + ❓ 힌트 (linkedChunkId 기반)
}
```

### `QuizBottomSheet` (`ui/quiz/QuizBottomSheet.kt`)
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizBottomSheet(
    questions: List<QuizQuestion>,
    targetLanguage: String,
    onFinish: () -> Unit,
    onDismiss: () -> Unit
)
// AnimatedContent 문제 전환, 정답 초록/오답 빨강 피드백
// 정답 선택 후 1.5초 자동 다음, 마지막 문제 후 onFinish()
```

---

## 데이터 모델

```kotlin
data class HeritageContent(
    val id: String,                  // "geunjeongjeon" | "gyeonghoeru" | "gwanghwamun"
    val title: String,
    val titleMap: Map<String, String>,    // 다국어 제목 (LLM 호출 없이 로컬 번역)
    val aliases: List<String>,       // Gemini 응답 → ID 매핑용
    val palace: String,
    val zone: String,
    val shortDescription: String,
    val shortDescMap: Map<String, String>, // 다국어 설명
    val chunks: List<HeritageChunk>,
    val latitude: Double,
    val longitude: Double,
    val subElements: List<SubElement> = emptyList()
)

data class SubElement(
    val id: String,                        // "geun_throne"
    val displayName: Map<String, String>,  // 다국어 라벨
    val visualHints: List<String>,         // Gemini 프롬프트용 영문 묘사
    val linkedChunkId: String? = null      // 연결 청크 (클릭 시 해당 섹션으로 이동)
)

data class HeritageChunk(
    val chunkId: String,
    val section: String,    // "역사" | "건축" | "관람 포인트" | "트리비아" | "관람 가이드"
    val title: String,
    val titleMap: Map<String, String>,
    val keywords: List<String>,
    val content: String,
    val contentMap: Map<String, String>
)
```

### POI 모델 (`domain/model/Poi.kt`)
```kotlin
data class Poi(
    val id: String,
    val type: PoiType,
    val title: String,
    val titleMap: Map<String, String>,
    val latitude: Double,
    val longitude: Double,
    val linkedHeritage: HeritageContent? = null  // 유산 건물인 경우 연결
) {
    fun localizedTitle(lang: String): String  // titleMap[lang] ?: title
}

enum class PoiType(val icon: String, val labelKo: String) {
    HERITAGE("🏛", "문화유산"), EXHIBIT("🖼", "전시"),
    GATE("🚪", "문/입구"), VIEWPOINT("📷", "전망 포인트"),
    INFO("ℹ️", "안내"), CAFE("☕", "카페"), SHOP("🛍", "기념품")
}

fun HeritageContent.toPoi(type: PoiType = PoiType.HERITAGE): Poi
```

### 퀴즈 모델 (`domain/model/QuizQuestion.kt`)
```kotlin
data class QuizQuestion(
    val heritageId: String,
    val questionKo: String, val questionEn: String,
    val questionJa: String, val questionZh: String,
    val choices: List<QuizChoice>,
    val correctIndex: Int    // 0-based
) {
    fun getQuestion(lang: String): String
}

data class QuizChoice(
    val textKo: String, val textEn: String,
    val textJa: String, val textZh: String
) {
    fun getText(lang: String): String
}
```

### 건물별 GPS 좌표
| ID | 이름 | lat | lng | 우선순위 |
|----|------|-----|-----|---------|
| geunjeongjeon | 근정전 | 37.5796 | 126.9770 | 기존 |
| gyeonghoeru   | 경회루 | 37.5800 | 126.9749 | 기존 |
| gwanghwamun   | 광화문 | 37.5759 | 126.9769 | 기존 |
| sajeongjeon   | 사정전 | 37.5802 | 126.9770 | P1 |
| sujeongjeon   | 수정전 | 37.5801 | 126.9760 | P1 |
| gangnyeongjeon| 강녕전 | 37.5808 | 126.9770 | P1 |
| gyotaejeon    | 교태전 | 37.5813 | 126.9770 | P1 |
| jagyeongjeon  | 자경전 | 37.5812 | 126.9779 | P1 |
| hyangwonjeong | 향원정 | 37.5822 | 126.9768 | P1 |
| geoncheongung | 건청궁 | 37.5826 | 126.9770 | P1 |
| jibokjae      | 집옥재 | 37.5829 | 126.9775 | P1 |
| donggung      | 동궁   | 37.5803 | 126.9782 | P1 |
| taeweonjeon   | 태원전 | 37.5815 | 126.9755 | P2 |
| sojubang      | 소주방 | 37.5810 | 126.9775 | P2 |
| heungbokjeon  | 흥복전 | 37.5793 | 126.9778 | P2 |
| geunjeongmun  | 근정문 | 37.5788 | 126.9770 | P2 |
| yeongjeogyo   | 영제교 | 37.5782 | 126.9769 | P2 |

---

## 주요 상수

| 상수 | 파일 | 값 | 용도 |
|------|------|-----|------|
| DEV_MODE | Config.kt | false | 개발 모드 토글 |
| MOCK_LATITUDE/LONGITUDE | Config.kt | 37.5796 / 126.9770 | Mock GPS 좌표 (경복궁) |
| MAX_LOCATION_AGE_MS | LocationProviderImpl.kt | 180000 (3분) | GPS 캐시 유효기간 |
| POLL_INTERVAL_MS | OutdoorSpatialProvider.kt | 5000 (5초) | GPS 폴링 주기 |
| CURRENT_POI_RADIUS_M | OutdoorSpatialProvider.kt | 20f | currentPoi 판정 반경 |
| MAX_NEARBY | OutdoorSpatialProvider.kt | 10 | nearbyPois 최대 수 |
| FOV_H / FOV_V | SpatialCalculator.kt | 65° / 120° | AR 시야각 |
| FOV_MARGIN | SpatialCalculator.kt | 1.1 | FOV 여유 마진 |
| AR_VISIBLE_RADIUS_M | AROverlayScreen.kt | 500f | AR 카드 표시 최대 거리 |
| AR_MAX_VISIBLE_POIS | AROverlayScreen.kt | 5 | 동시 표시 최대 POI |
| adaptiveAlpha (빠름/느림) | ARSensorProvider.kt | 0.85 / 0.35 | 방위각 EMA 필터 계수 |

---

## NavGraph 흐름

```
MainActivity → NavGraph(navController)
    startDestination = LanguageSelection
    → 언어 선택 시 LanguageManager.saveLanguage() + activity.recreate() + navigate(Camera)

    Camera → hiltViewModel<ARViewModel>
          → CameraScreen(viewModel, onNavigateToDetail, targetLanguage)
          // 🗺️ AR FAB 클릭: AR 오버레이 토글 (toggleArOverlay())
          //   → AR 오버레이 중 폰 눕힘(isPhoneFlat): MapScreen 자동 전환 (currentLocation 포함)
          // 🎯 도장 FAB 클릭: StampCollectionScreen 오버레이
          // 🧩 퀴즈: 신규 유산 발견 시 pendingQuizQuestions → QuizBottomSheet 자동 표시
          // building 카드 클릭: onNavigateToDetail(id, null)
          // sub-element 카드 클릭: onNavigateToDetail(id, linkedChunkId)

    Detail → DetailScreen(heritageId, chunkId, targetLanguage, onBack)
           → chunkId가 있으면 해당 청크를 최상단 랭크로 노출
```

---

## 번역 전략 (토큰 절약)

### 번역 결정 로직 (Decision Tree)
1. UI 고정 문구 → `Strings.kt` 정적 맵
2. 고유 명사 → `Strings.kt` 정적 맵
3. DB 관리 데이터 → `titleMap`, `shortDescMap`, `contentMap` 직접 조회 (LLM 없음)
4. 동적 장문 → Gemini `translateMultipleTexts()` / `askQuestion()` 스트리밍

### 번역 매핑 테이블
| 항목 | 번역 방식 | 함수/위치 |
|------|-----------|-----------| 
| 추천 질문 | 정적 | `Strings.getSuggestedQuestions()` |
| 궁/건물 이름 | 정적 | `Strings.getPalaceName()` |
| 청크 섹션 카테고리 | 정적 | `Strings.getSection()` |
| 카메라 공통 UI 라벨 | 정적 | `Strings.getCameraLabels()` |
| 스탬프 UI 라벨 | 정적 | `Strings.getStampLabels()` |
| 건축물 제목/설명 | DB 필드 | `titleMap`, `shortDescMap` |
| **청크 본문** | **LLM 동적** | `translateMultipleTexts()` 병렬 |
| **챗봇 답변** | **LLM 스트리밍** | `askQuestion()` |

---

## API 및 보안 설정 (`local.properties`)

- `MAPS_API_KEY`: 구글 맵 SDK용 키 (Manifest에 주입)
- `GEMINI_API_KEY`: Gemini AI SDK용 키 (BuildConfig 경유)

```kotlin
// app/build.gradle.kts
manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
```

---

## 버그 수정 이력

| # | 파일 | 내용 |
|---|------|------|
| 1 | `HeritageRecognitionPipeline.kt` | `isProcessing` 데드락: `try-finally` 구조로 수정 |
| 2 | `GeminiClient.kt` + `Pipeline.kt` | GPS 힌트 → Gemini 프롬프트 전달 (dead code 해소) |
| 3 | `LocalHeritageKnowledgeSource.kt` | 경회루 한/영/일/중 다국어 맵 전체 추가 |
| 4 | `HeritageRepository.kt` | `koreanEndings` 중복 "군요" 제거 |
| 5 | `ARViewModel.kt` | `locationPollingJob` 명시적 취소 |
| 6 | `GeminiClient.kt` | `scaleBitmap(512px)` → API 레이턴시 개선 |
| 7 | `Pipeline.kt` + `GeminiClient.kt` | `buildCandidateHint` 편향 제거, 시각 증거 절대 우선 |
| 8 | `GeminiClient.kt` | `classifySubElement`: buildingId 후보 제거 |
| 9 | `ARViewModel.kt` | Stage 2 ML Kit 기반 트리거 복구 |
| 10 | `StampCollectionScreen.kt` | 미발견 유산 클릭 시 바텀시트 열림 수정 |
| 11 | `MapScreen.kt` | 터치 제스처 즉각 감지 → 지도 무한 원점 회귀 해결 |
| 12 | `AROverlayScreen.kt` | X축 군집화 기반 Y축 분산 알고리즘(Spread=110dp)으로 카드 겹침 해결 |
| 13 | `ARSensorProvider.kt` | Delta 기반 적응형 EMA 필터(Adaptive Alpha)로 슬라이딩 드리프트 방지 |
| 14 | `GeminiClient.kt` | scaleBitmap 결과 finally에서 recycle() — Bitmap 메모리 누수 방지 |
| 15 | `GeminiClient.kt` | askQuestion 미사용 `errorMsg` 변수 제거 |
| 16 | `HeritageRecognitionPipeline.kt` | `recognizedLandmarkId/SubElementId`: 불필요 StateFlow → private var |
| 17 | `HeritageRecognitionPipeline.kt` | `getRankedCandidates` bearing 중복 계산 제거 (3회→1회) |
| 18 | `HeritageRecognitionPipeline.kt` | `processSubElementFrame` buildingId 중복 체크 dead code 제거 |
| 19 | `ARViewModel.kt` | `nextPoiIds` combine 수정: discoveredHeritages race condition 해소 |
| 20 | `ARViewModel.kt` | `frameBuffer.clear()` 추가: 처리 완료 후 Bitmap 참조 즉시 해제 |
| 21 | `ARViewModel.kt` | 미사용 `savedStateHandle` 파라미터 및 `LocationProvider` import 제거 |
| 22 | `OutdoorSpatialProvider.kt` | `POLL_INTERVAL_MS` 1500→5000: 문서/주석과 코드 불일치 수정, 배터리 절약 |
| 23 | `AROverlayScreen.kt` | 매직넘버 500f/5 → `AR_VISIBLE_RADIUS_M`/`AR_MAX_VISIBLE_POIS` 상수 추출 |

## 신규 기능

| 파일 | 기능 |
|------|------|
| `data/location/ARSensorProvider.kt` | TYPE_ROTATION_VECTOR 센서로 azimuth/pitch/roll 제공, `isPhoneFlat` 감지 |
| `ui/ar/AROverlayScreen.kt` | GPS+나침반 기반 AR 카드 오버레이, 부유 애니메이션, PoiInfoDialog |
| `domain/model/Poi.kt` | POI 상위 개념 데이터 구조 + `PoiType` enum + `HeritageContent.toPoi()` 변환 헬퍼 |
| `domain/model/QuizQuestion.kt` | 4지선다 퀴즈 데이터 모델 (`QuizQuestion` + `QuizChoice`) 다국어 지원 |
| `data/quiz/QuizRepository.kt` | 하드코딩 퀴즈 데이터 저장소 — 근정전/경회루/광화문 각 3문항(총 9개) |
| `data/discovery/DiscoveryTracker.kt` | 유산/서브엘리먼트 최초 발견 타임스탬프 SharedPrefs 저장, 퀴즈 완료 여부 관리 |
| `data/audio/TTSManager.kt` | Android 내장 TTS 래퍼 — 다국어(`ko/en/ja/zh`) 음성 재생, `isEnabled`/`isSpeaking` StateFlow |
| `ui/quiz/QuizBottomSheet.kt` | ModalBottomSheet 퀴즈 UI — AnimatedContent 전환, 정답/오답 피드백 |
| `domain/spatial/` | XR 엔진 도메인 패키지 — SpatialContext, SpatialContextProvider, SpatialCalculator |
| `data/location/OutdoorSpatialProvider.kt` | GPS 기반 SpatialContext 발행 (5초 폴링, 사이트 경계 판정) |
| `data/location/IndoorSpatialProvider.kt` | BLE/UWB Stub — 향후 실내 측위 구현 예정 |

## 주요 설계 결정 사항

- **DI**: Hilt 완전 적용 (`AppModule` @Singleton, `DevModeManager @Singleton`, `@HiltViewModel`).
- **파이프라인**: `processFrame/processSubElementFrame`이 `String?` 직접 반환. 내부 상태는 private var.
- **DEV_MODE**: `DevModeManager` 싱글톤이 StateFlow 관리. 무조건 `processRecognition("geunjeongjeon")` 강제.
- **AR 오버레이**: `ARSensorProvider`(TYPE_ROTATION_VECTOR)로 방위각/피치 추적, `isPhoneFlat` 시 MapScreen 전환.
- **XR 엔진**: `SpatialContextProvider` 인터페이스로 Outdoor/Indoor 전환 가능. GPS 폴링 1곳 집중.
- **지도**: `MapScreen`에서 `currentLocation`(spatialContext 파생)으로 실제 GPS 위치 마커 표시.
- **벡터 DB 없음**: 청크 12개 → 룰 기반 키워드 매칭 + 형태소 어간 추출. 추후 DB 도입 예정.
- **RAG**: `buildRagContext(landmarkId, question)` → 상위 3청크 → `askQuestion()` 스트리밍 `Flow<String>`.
- **UI**: 경복궁 전통 색상(단청/기와/한지) + 명조체(`Serif`) 강제 적용. dynamicColor 무시.
- **POI 시스템**: `Poi` + `PoiType`으로 유산/전시/게이트 등 다양한 위치를 통합 관리. `HeritageContent.toPoi()`로 하위 호환 변환.
- **퀴즈**: `QuizRepository`(하드코딩) + `DiscoveryTracker`(SharedPrefs) + `QuizBottomSheet`(UI)로 게이미피케이션.
- **TTS**: `TTSManager`(Android 내장)으로 외부 API 없이 다국어 음성 낭독. `onCleared`에서 `shutdown()` 필수.
- **메모리**: Bitmap은 scaleBitmap 후 finally에서 recycle(), frameBuffer는 처리 후 즉시 clear(). onCleared()에서 lastCapturedFrame null 처리.

## 미완성 작업 (TODO)

| 위치 | 내용 |
|------|------|
| `ARViewModel.kt:163` | `nextPoiIds` 로직을 실제 코스 추천 / 게이미피케이션으로 교체 |
| `IndoorSpatialProvider.kt:21` | BLE/UWB Ranging → indoor 좌표 → SpatialContext 발행 구현 |

---

## 서버 마이크로서비스 구조

```
services/
├── orchestrator/              # port 8000 — 라우팅 + cache-first 결정
│   ├── main.py                # FastAPI 앱: /health, /metrics, /docent/question, /docent/narrative,
│   │                          #   /docent/translate, /docent/translate/batch
│   ├── orchestrator.py        # 핵심 로직: memory → cache → retrieval → narrative → fallback
│   ├── cache.py               # 도메인별 TTL 캐시 (qa:30m, narrative:2h, translation:24h)
│   │                          #   DomainMetrics + LLM usage tracking + /metrics 엔드포인트
│   │                          #   make_hashed_key() — MD5 기반 (긴 질문 메모리 효율)
│   ├── config.py              # 환경변수 설정 (.env 지원)
│   │                          #   retrieval_agent_url, narrative_agent_url, memory_agent_url
│   │                          #   retrieval_timeout_ms=200, narrative_timeout_ms=5000, memory_timeout_ms=300
│   ├── knowledge.py           # 로컬 유산 메타데이터 (Android 동기화)
│   ├── retrieval_client.py    # agent-retrieval HTTP 호출 (httpx, 200ms timeout)
│   ├── narrative_client.py    # agent-narrative HTTP 호출 (/explain, memory_context 포함)
│   ├── memory_client.py       # agent-memory HTTP 호출 (세션 + 이벤트 조회, personalization)
│   ├── models.py              # Pydantic DTOs (QuestionResponse.retrieved_chunks 포함)
│   └── requirements.txt       # fastapi, uvicorn, pydantic, pydantic-settings, httpx
│
├── agent-retrieval/           # port 8001 — 하이브리드 검색 (rule + vector stub)
│   ├── main.py                # /retrieve, /retrieve/rule
│   ├── hybrid_retriever.py    # rule(0.6) + vector(0.4) 가중 합산
│   ├── rule_retriever.py      # 한국어 어간 + 키워드 매칭 (Android RuleRetriever 포팅)
│   ├── vector_retriever.py    # stub (항상 빈 결과)
│   ├── knowledge.py           # 서버측 청크 데이터 (근정전/경회루/광화문)
│   └── models.py              # RetrievalQuery / ScoredChunk / RetrievalResult
│
├── agent-narrative/           # port 8002 — 해설 + Q&A 답변 생성
│   ├── main.py                # /generate (템플릿), /explain (청크→자연어, LLM)
│   ├── generator.py           # cache → template → LLM → fallback
│   ├── explainer.py           # cache → Gemini 2.5 Flash → extractive fallback
│   │                          # [활성화] genai.GenerativeModel.generate_content_async() 실제 호출
│   │                          #   env: GEMINI_API_KEY / DOCENT_GEMINI_API_KEY / DOCENT_GEMINI_MODEL
│   │                          #   SDK 미설치 시 extractive fallback으로 안전 동작
│   ├── prompts.py             # docent/kid/expert 모드별 grounded 프롬프트 + personalization 블록
│   ├── templates.py           # 3종 × 3건물 × 4언어 정적 템플릿
│   ├── cache.py               # NarrativeCache (TTL 2h, hit rate 추적)
│   └── models.py              # ExplainRequest (memory_context 포함), ExplainResponse
│
└── agent-memory/              # port 8003 — 세션/사용자/이력 영속화
    ├── main.py                # 14개 엔드포인트 (session/user/history)
    ├── session_writer.py      # live.state.json + recent.events.ndjson
    ├── user_writer.py         # 사용자 프로필 + 선호도
    ├── history_writer.py      # 방문 이력 NDJSON + 통계
    ├── file_utils.py          # atomic write, NDJSON append, safe IO
    ├── models.py              # SessionState, UserProfile, VisitRecord 등
    └── examples/              # 예제 메모리 파일
```

## Q&A 전체 호출 체인

```
Android CameraScreen QuestionBottomSheet
  │ gateway.askQuestion(QuestionRequest)
  │
  ├─ ORCHESTRATOR_URL 없음 → LocalFirstDocentGateway
  │   └─ GeminiClient.askQuestion() (로컬 Gemini SDK 스트리밍)
  │
  └─ ORCHESTRATOR_URL 있음 → RemoteDocentGateway
      └─ HTTP POST orchestrator:8000/docent/question
           ├─ 1. Memory (agent-memory:8003, 300ms)
           ├─ 2. Cache (hashed key, qa:30m TTL)
           ├─ 3. Retrieval (agent-retrieval:8001, 200ms)
           ├─ 4. Narrative (agent-narrative:8002, 5s, Gemini 2.5 Flash)
           └─ 5. Fallback chain (safe message → extractive → 청크 그대로)
      │
      └─ Remote 실패 → delegate.askQuestion() (로컬 Gemini fallback)
```

## 주요 상수 (서버)

| 상수 | 파일 | 값 | 용도 |
|------|------|-----|------|
| ORCHESTRATOR_URL | BuildConfig (local.properties) | "" (기본) | Remote Gateway 활성화 |
| retrieval_timeout_ms | orchestrator/config.py | 200 | retrieval agent timeout |
| narrative_timeout_ms | orchestrator/config.py | 5000 | narrative agent timeout |
| memory_timeout_ms | orchestrator/config.py | 300 | memory agent timeout |
| qa TTL | orchestrator/cache.py | 1800 (30분) | Q&A 캐시 |
| narrative TTL | orchestrator/cache.py | 7200 (2시간) | 해설 캐시 |
| translation TTL | orchestrator/cache.py | 86400 (24시간) | 번역 캐시 |
| LOCATION_THRESHOLD_M | MemorySessionHandler | 10f | hav 거리 기반 GPS 변화 감지 |
| FLUSH_INTERVAL_MS | MemorySessionHandler | 1000 | throttle 후 state flush 주기 |
| BATCH_SIZE | MemorySessionHandler | 5 | 즉시 flush 트리거 임계 |
