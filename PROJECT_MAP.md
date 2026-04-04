# AR_POC Project Map
> **목적**: 코드 수정 시 이 파일만 참조해서 불필요한 파일 full-read를 줄임
> **업데이트**: 파일 구조/시그니처 변경 시 반드시 이 파일도 갱신
> **최종 업데이트**: 2026-03-29 (AR 오버레이 및 지도 트래킹 UX/안정성 고도화)

---

## 패키지 구조

```
com.example.ar_poc
├── ARApplication.kt                 # @HiltAndroidApp 등록 (Hilt 시작점)
├── Config.kt                        # DEV_MODE(const val), MOCK GPS 좌표
├── Strings.kt                       # 정적 다국어 번역 맵 (추천 질문, 궁 이름, 섹션 라벨, 카메라 UI 라벨)
├── MainActivity.kt                  # @AndroidEntryPoint, attachBaseContext(LocaleHelper 적용), setContent { NavGraph() }
├── NavGraph.kt                      # NavHost: LanguageSelection → Camera → Detail (LocaleHelper로 로케일 주입)
├── DetailScreen.kt                  # 유산 상세 화면 (청크별 번역 병렬 처리)
├── ObjectDetectorAnalyzer.kt        # ML Kit 객체 감지 ImageAnalysis.Analyzer
│
├── ai/
│   └── GeminiClient.kt              # Gemini API 래퍼 — classifyLandmark / classifySubElement / translateText / askQuestion
│                                    # [개선] scaleBitmap(512px) 전처리로 전송 데이터 35% 절감 → API 레이턴시 개선
│                                    # [개선] classifyLandmark: GPS는 참고용, 시각 증거 절대 우선 프롬프트
│                                    # [개선] classifySubElement: buildingId 후보 제거, 클로즈업 디테일 식별 집중
│
├── data/
│   ├── LanguageManager.kt           # SharedPreferences 언어 저장/불러오기
│   ├── audio/
│   │   └── TTSManager.kt            # [신규] Android TTS 래퍼 — speak(text, lang) / stop() / shutdown()
│   │                                 #        isEnabled/isSpeaking: StateFlow<Boolean>
│   ├── discovery/
│   │   └── DiscoveryTracker.kt      # [신규] 발견 타임스탬프 SharedPrefs 저장소
│   │                                 #        recordHeritage/SubElement(), isQuizCompleted() 등
│   ├── heritage/
│   │   ├── HeritageKnowledgeSource.kt   # interface: getHeritageById, getHeritageList
│   │   │                                 #   + getExtraPoiList(): List<Poi>  (default=emptyList)
│   │   └── LocalHeritageKnowledgeSource.kt  # 하드코딩 데이터 (근정전/경회루/광화문)
│   │                                        # [개선] 경회루 titleMap/shortDescMap + 4개청크 추가
│   │                                        # [신규] extraPoiList: 카페2, 기념품점, 안내소, 뷰포인트, 신무문(북문) 6개
│   ├── location/
│   │   ├── CompassProvider.kt       # 나침반 EMA 필터, azimuth: StateFlow<Float>
│   │   ├── LocationProviderImpl.kt  # GPS (lastLocation 우선, companion MAX_LOCATION_AGE_MS)
│   │   ├── ARSensorProvider.kt      # [신규/개선] TYPE_ROTATION_VECTOR → azimuth/pitch/roll StateFlow (적응형 EMA 필터로 슬라이딩 지연 방지)
│   │   │                            #        isPhoneFlat: StateFlow<Boolean> — pitch < -45° 시 true (기존 -65°에서 완화)
│   │   ├── OutdoorSpatialProvider.kt # [신규] SpatialContextProvider 구현체 (GPS 기반)
│   │   │                            #  - getAllPois()로 전체 POI 로드 후 거리 계산
│   │   │                            #  - currentPoi(20m 반경), nearbyPois(10개), zoneId 판정
│   │   │                            #  - POLL_INTERVAL_MS=5000, DevMode 지원
│   │   └── IndoorSpatialProvider.kt # [신규] BLE/UWB 기반 Stub — 현재 EMPTY 발행
│   └── quiz/
│       └── QuizRepository.kt        # [신규] 하드코딩 4지선다 퀴즈 — 근정전/경회루/광화문 각 3문항
│
├── di/
│   ├── AppModule.kt                 # Hilt @Module: GeminiClient, Pipeline, Repository, ARSensorProvider 등 Singleton 주입
│   │                                #   [신규] provideSpatialContextProvider() → OutdoorSpatialProvider 바인딩
│   └── DevModeManager.kt           # Singleton — DevMode 토글 & StateFlow 관리
│
├── domain/
│   ├── HeritageRecognitionPipeline.kt  # 핵심 인식 파이프라인 + DebugInfo
│   │   # [버그수정] try-finally로 isProcessing 데드락 방지
│   │   # [개선] buildCandidateHint: "most likely" 편향 제거 → 후보 3개 방향 차이 목록 제공
│   │   # [개선] processSubElementFrame: buildingId 후보 제거 (GeminiClient에서 필터링)
│   ├── model/
│   │   ├── HeritageContent.kt       # HeritageContent + HeritageChunk + SubElement
│   │   ├── Poi.kt                   # [신규] Poi data class + PoiType enum + HeritageContent.toPoi() 확장함수
│   │   └── QuizQuestion.kt          # [신규] QuizQuestion(heritageId, 다국어 question, choices, correctIndex)
│   ├── repository/
│   │   └── HeritageRepository.kt    # getRelevantChunks() + buildRagContext() + getHeritageList()
│   │                                # [신규] getAllPois(): heritagePois + extraPoiList 합산 반환
│   │                                # [버그수정] extractStems() koreanEndings 중복 "군요" 제거
│   └── spatial/                     # [신규] XR 엔진 도메인 패키지
│       ├── SpatialContext.kt        # 공간 상태 스냅샷 모델
│       │                            #   siteId, zoneId, currentPoi, nearbyPois, isIndoor, lat/lng, accuracy
│       │                            #   EMPTY companion, hasValidLocation/isInsideSite 프로퍼티
│       ├── SpatialContextProvider.kt # interface: getSpatialContext(): Flow<SpatialContext>
│       └── SpatialCalculator.kt     # 순수 함수 AR/XR 계산 — UI 의존성 없음
│                                    #   calcScreenPos(), calcBearing(), calcDistanceM(), azimuthToCardinal()
│
├── util/
│   └── LocaleHelper.kt             # Context를 특정 Locale로 래핑하는 유틸리티
│
└── ui/
    ├── LanguageSelectionScreen.kt   # 언어 선택 (ko/en/ja/zh)
    ├── ar/
    │   └── AROverlayScreen.kt       # [신규/개선] GPS+방위각 기반 AR 카드 오버레이
    │                                #   - calcARScreenPos(): bearingDiff→X, pitch→Y 좌표 계산
    │                                #   - 겹침 방지: X축 군집화(Clustering) 및 Y축 110dp 간격 분산 배치로 물리적 카드 겹침 원천 차단
    │                                #   - ARHeritageCard: 거리/발견여부 표시, 둥실 부유 애니메이션
    ├── camera/
    │   ├── CameraScreen.kt          # 메인 AR 화면
    │   │   # [개선] 지도 FAB → AR 오버레이 토글 (toggleArOverlay())
    │   │   # [개선] isPhoneFlat 감지 시 MapScreen 자동 전환 + currentLocation 전달
    │   └── components/
    │       ├── ARMarker.kt
    │       ├── CameraPreviewLayer.kt    # CameraX 프리뷰 및 ImageAnalysis(640x480 최적화)
    │       ├── DebugOverlay.kt      # DEV_MODE 전용 디버그 패널
    │       └── UnrealXRLayer.kt
    ├── map/
    │   └── MapScreen.kt             # Google Maps Compose 지도 + 세이프 에어리어 + 다국어 마커
    │                                # [개선] currentLocation을 실제 GPS 수신, 내 위치 아이콘 수정(LocationOn)
    │                                # [버그수정] PointerInput 제스처 즉각 감지로 자유 패닝 모드 전환 시 카메라 포지션 강제 갱신을 전면 차단 (지도 무한 원점 회귀 버그 해결)
    │                                # [버그수정] 맵 초기 로딩 시 카메라 타겟이 (0,0)(아프리카)로 튀는 버그 제어
    ├── stamp/
    │   └── StampCollectionScreen.kt # 도장 수집 화면 — 발견 유산 카드 그리드, 진행률 표시
    │                                # [버그수정] 미발견 유산도 클릭 시 바텀시트 열림 (❓ 힌트 제공)
    ├── theme/
    │   ├── Color.kt                 # 한국 전통 색상 (DancheongRed, HanjiWhite 등)
    │   ├── Theme.kt                 # MaterialTheme Customizing
    │   └── Type.kt                  # FontFamily.Serif(명조) 적용
    └── viewmodel/
        └── ARViewModel.kt           # 메인 ViewModel (@HiltViewModel)
            # [개선] ARSensorProvider 주입 — arAzimuth/arPitch/isPhoneFlat StateFlow
            # [개선] showArOverlay StateFlow + toggleArOverlay()
            # [개선] currentLocation StateFlow (DEV_MODE: MOCK 좌표, 실기기: GPS)
            # [버그수정] onCleared()에서 arSensorProvider.stop() 추가
            # [버그수정] Stage 2: Detected 상태에서 ML Kit validObject 기반 트리거 복구
            # [버그수정] locationPollingJob 명시적 취소 (Issue 5)
```

---

## 핵심 클래스 시그니처

### `GeminiClient` (`ai/GeminiClient.kt`)
```kotlin
class GeminiClient {  // Hilt @Singleton
    // 전송 전 비트맵 512px 다운스케일 (API 레이턴시 개선)
    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int = 512): Bitmap

    // Stage 1: 건물 인식 (시각 증거 절대 우선, GPS는 참고용 Warning 문구)
    suspend fun classifyLandmark(
        bitmap: Bitmap,
        candidates: List<String>,
        palaceName: String,
        locationHint: String = ""  // "User facing N. GPS bearing diffs: X(5°off),..."
    ): String  // {landmark_id} | "Unknown" | "Error: ..."

    // Stage 2: 클로즈업 디테일 인식 (buildingId 후보 제외 필터링 내장)
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

### `ARSensorProvider` (`data/location/ARSensorProvider.kt`) ← **신규/개선**
```kotlin
class ARSensorProvider(context: Context) : SensorEventListener {
    val azimuth: StateFlow<Float>      // 0~360°, 적응형 EMA 평활(회전 빠를 땐 즉각 반영, 느릴 땐 노이즈 억제)
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

class HeritageRecognitionPipeline(geminiClient, locationProvider, knowledgeSource) {
    val isProcessing: StateFlow<Boolean>
    val debugInfo: StateFlow<DebugInfo>

    // [버그수정] try-finally 구조로 isProcessing 데드락 방지
    suspend fun processFrame(bitmaps: List<Bitmap>, azimuth: Float): String?

    // [개선] buildingId 후보 제거 후 GeminiClient 호출 (항상 건물ID 반환되던 문제 해결)
    suspend fun processSubElementFrame(bitmaps: List<Bitmap>): String?

    suspend fun getCurrentLocation(): Location?
    fun isInsidePalace(location: Location): Boolean
    fun clearResult()

    // [개선] "most likely: X" 편향 제거 → 방향 차이 목록 제공
    private fun buildCandidateHint(userLocation, azimuth, ranked): String
    // 반환 예: "User facing N. GPS bearing diffs from smallest: geunjeongjeon(5°off), gyeonghoeru(78°off)"
}
```

### `ARViewModel` (`ui/viewmodel/ARViewModel.kt`)
```kotlin
@HiltViewModel
class ARViewModel @Inject constructor(
    pipeline, repository, compassProvider, geminiClient, devModeManager,
    arSensorProvider: ARSensorProvider  // [신규]
) : ViewModel() {
    var targetLanguage: String
    var labels: Strings.ARUiLabels

    // 기존 StateFlow
    val uiState: StateFlow<ARUiState>
    val cameraMode: StateFlow<CameraMode>
    val gazeProgress: StateFlow<Float>
    val isObjectCandidateDetected: StateFlow<Boolean>
    val isInsidePalace: StateFlow<Boolean>
    val isDevMode: StateFlow<Boolean>
    val currentAzimuth: StateFlow<Float>
    val debugInfo: StateFlow<DebugInfo>
    val recognizedSubElement: StateFlow<SubElement?>
    val suggestedQuestions: StateFlow<List<String>>
    val discoveredHeritages: StateFlow<Set<String>>
    val discoveredSubElements: StateFlow<Set<String>>
    val heritageList: List<HeritageContent>
    val recognitionFailedMessage: StateFlow<String?>

    val poiList: List<Poi>  // repository.getAllPois() — 유산 POI + 일반 POI(카페·안내소 등) 통합

    fun onObjectsDetected(detectedObjects: List<DetectedObject>, width: Int, height: Int, bitmap: Bitmap?)
    fun forceScan()
    fun toggleDevMode()
    fun toggleCameraMode()
    fun resetDetection()
    fun setLanguage(lang: String)
}

sealed class ARUiState {
    object Idle; object GazeLoading; object Detecting
    data class Detected(content: HeritageContent, center: Offset, size: Size)
    data class Error(message: String)
}
```

### `AROverlayScreen` (`ui/ar/AROverlayScreen.kt`) ← **신규**
```kotlin
@Composable
fun AROverlayScreen(
    heritageList: List<HeritageContent>,
    currentLat: Double, currentLng: Double,
    azimuth: Float, pitch: Float,
    targetLanguage: String,
    discoveredHeritages: Set<String>,
    onHeritageClick: (String) -> Unit,
    onClose: () -> Unit
)

// 핵심 좌표 계산 함수
fun calcARScreenPos(
    userLat, userLng, targetLat, targetLng,
    azimuth, pitch, screenW, screenH
): Pair<Float, Float>?  // FOV(68°H×52°V) 밖이면 null
// bearingDiff = ((bearing - azimuth + 540) % 360) - 180  → X
// pitchAdjusted = pitch + 25°  → Y (자연스러운 파지 보정)
// [추가 알고리즘]: X축 군집화 및 spreadStepDp(110dp) Y축 분산 배치 로직
```

### `MapScreen` (`ui/map/MapScreen.kt`)
```kotlin
@Composable
fun MapScreen(
    currentLocation: Location?,          // [개선] 이제 실제 GPS 좌표가 전달됨 (기존: 항상 null)
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
    // [버그수정] 미발견 유산도 클릭 시 바텀시트 열림 (❓ 힌트 동기부여)
    // 발견 유산: 컬러 + ✓ 뱃지 + 서브엘리먼트 이름 표시
    // 미발견: blur + 회색 + ❓ 힌트 (linkedChunkId 기반)
}
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
    val subElements: List<SubElement>
)

data class SubElement(
    val id: String,                        // "geun_throne"
    val displayName: Map<String, String>,  // 다국어 라벨
    val visualHints: List<String>,         // Gemini 프롬프트용 영문 묘사
    val linkedChunkId: String?             // 연결 청크 (클릭 시 해당 섹션으로 이동)
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

### POI 모델 (`domain/model/Poi.kt`) ← **신규**
```kotlin
// 지도/AR 오버레이에 표시되는 모든 위치 데이터의 상위 개념
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

// 변환 헬퍼 (기존 코드 수정 없이 점진적 이관)
fun HeritageContent.toPoi(type: PoiType = PoiType.HERITAGE): Poi
```

### 퀴즈 모델 (`domain/model/QuizQuestion.kt`) ← **신규**
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
| ID | 이름 | lat | lng |
|----|------|-----|-----|
| geunjeongjeon | 근정전 | 37.5796 | 126.9770 |
| gyeonghoeru   | 경회루 | 37.5800 | 126.9749 |
| gwanghwamun   | 광화문 | 37.5759 | 126.9769 |

---

## NavGraph 흐름

```
MainActivity → NavGraph(navController)
    startDestination = LanguageSelection
    → 언어 선택 시 LanguageManager.saveLanguage() + navigate(Camera)

    Camera → hiltViewModel<ARViewModel>
          → CameraScreen(viewModel, onNavigateToDetail, targetLanguage)
          // 🗺️ AR FAB 클릭: AR 오버레이 토글 (toggleArOverlay())
          //   → AR 오버레이 중 폰 눕힘(isPhoneFlat): MapScreen 자동 전환 (currentLocation 포함)
          // 🎯 도장 FAB 클릭: StampCollectionScreen 오버레이
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

## 주요 버그 수정 이력 (이 세션)

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
| 11 | `MapScreen.kt` | 터치 제스처 즉각 감지(isFollowing 자동 해제)로 지도 패닝/줌 무한 덮어쓰기 파훼 및 아프리카(0,0) 좌표 점프 방지 |
| 12 | `AROverlayScreen.kt` | AR 카드 겹침 현상을 `X축 군집화 기반 Y축 분산 알고리즘(Spread=110dp)`으로 교체해 근본적 해결 |
| 13 | `ARSensorProvider.kt` | 방향 센서 회전 지연(슬라이딩 드리프트)을 방지하는 Delta 기반 `적응형 EMA 필터(Adaptive Alpha)` 도입 |

## 신규 기능

| 파일 | 기능 |
|------|------|
| `data/location/ARSensorProvider.kt` | TYPE_ROTATION_VECTOR 센서로 azimuth/pitch/roll 제공, `isPhoneFlat` 감지 |
| `ui/ar/AROverlayScreen.kt` | GPS+나침반 기반 AR 카드 오버레이, Spring 애니메이션 카메라 트래킹 |
| `domain/model/Poi.kt` | POI 상위 개념 데이터 구조 + `PoiType` enum + `HeritageContent.toPoi()` 변환 헬퍼 |
| `domain/model/QuizQuestion.kt` | 4지선다 퀴즈 데이터 모델 (`QuizQuestion` + `QuizChoice`) 다국어 지원 |
| `data/quiz/QuizRepository.kt` | 하드코딩 퀴즈 데이터 저장소 — 근정전/경회루/광화문 각 3문항(총 9개) |
| `data/discovery/DiscoveryTracker.kt` | 유산/서브엘리먼트 최초 발견 타임스탬프 SharedPrefs 저장, 퀴즈 완료 여부 관리 |
| `data/audio/TTSManager.kt` | Android 내장 TTS 래퍼 — 다국어(`ko/en/ja/zh`) 음성 재생, `isEnabled`/`isSpeaking` StateFlow |

## 주요 설계 결정 사항

- **DI**: Hilt 완전 적용 (`AppModule`, `DevModeManager @Singleton`, `@HiltViewModel`).
- **파이프라인**: `processFrame/processSubElementFrame`이 `String?` 직접 반환. StateFlow 감지 없음.
- **DEV_MODE**: `DevModeManager` 싱글톤이 StateFlow 관리. 무조건 `processRecognition("geunjeongjeon")` 강제.
- **AR 오버레이**: `ARSensorProvider`(TYPE_ROTATION_VECTOR)로 방위각/피치 추적, `isPhoneFlat` 시 MapScreen 전환.
- **지도**: `MapScreen`에서 `currentLocation`으로 실제 GPS 위치 마커 표시.
- **벡터 DB 없음**: 청크 12개 → 룰 기반 키워드 매칭 + 형태소 어간 추출. 추후 DB 도입 예정.
- **RAG**: `buildRagContext(landmarkId, question)` → 상위 3청크 → `askQuestion()` 스트리밍 `Flow<String>`.
- **UI**: 경복궁 전통 색상(단청/기와/한지) + 명조체(`Serif`) 강제 적용.
- **POI 시스템**: `Poi` + `PoiType`으로 유산/전시/게이트 등 다양한 위치를 통합 관리. `HeritageContent.toPoi()`로 하위 호환 변환.
- **퀴즈**: `QuizRepository`(하드코딩, 외부 API 없음) + `DiscoveryTracker`(SharedPrefs 타임스탬프 기록)으로 게이미피케이션.
- **TTS**: `TTSManager`(Android 내장)으로 외부 API 없이 다국어 음성 낭독. `shutdown()` 필수 호출.
