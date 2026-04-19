# Feedback Loop Log

> AI 코딩 → 리뷰 → 패턴 발견 → 규칙 보강 → 반복
>
> 이 파일은 AI 에이전트가 코드 리뷰 중 발견한 **실수 패턴**과 **교훈**을 기록한다.
> 각 세션 시작 시 이 파일을 읽고, 이전에 발견된 패턴을 **반복하지 않도록** 한다.

---

## 사용 방법

### 세션 시작 시
- 이 파일의 **패턴 통계** 테이블과 최근 세션 기록을 훑어본다
- 빈번한 카테고리(메모리 누수, Dead code 등)를 의식하며 작업한다

### 세션 중 새 패턴 발견 시
```
### [YYYY-MM-DD] 제목
- **발견 위치**: 파일명 함수명
- **패턴**: 무엇이 반복되었는가
- **원인**: 왜 이런 실수가 발생하는가
- **수정**: 어떻게 고쳤는가
- **규칙 승격**: 아래 기준에 따라 판단
```

### 규칙 승격 기준
| 조건 | 승격 대상 |
|------|----------|
| 동일 패턴 2회 이상 반복 | → **AGENTS.md** 금지 목록에 추가 |
| 새로운 기술 패턴/관행 | → **CLAUDE.md** 아키텍처 패턴에 추가 |
| 자동 감지 가능한 패턴 | → **pre-commit hook**에 검사 추가 |
| 1회성, 일반적 최적화 | → 미반영 (기록만 유지) |

---

## 2026-04-09 세션 기록

### [2026-04-09] Bitmap scaleBitmap 후 recycle 누락
- **발견 위치**: GeminiClient.kt classifyLandmark(), classifySubElement()
- **패턴**: `scaleBitmap()`으로 새 Bitmap 생성 후 API 호출만 하고 `recycle()` 미호출
- **원인**: 원본 bitmap과 scaled bitmap이 같은 객체일 수 있어 단순 recycle 불가 → 조건부 처리 필요
- **수정**: `finally { if (scaled !== bitmap) scaled.recycle() }` 패턴 적용
- **규칙 승격**: ✅ AGENTS.md "Bitmap recycle 검사" 항목, CLAUDE.md "메모리 관리" 섹션, pre-commit hook 검사 #3

### [2026-04-09] StateFlow를 외부에서 구독하지 않는데 생성
- **발견 위치**: HeritageRecognitionPipeline.kt `_recognizedLandmarkId`, `_recognizedSubElementId`
- **패턴**: `MutableStateFlow` + `asStateFlow()` 패턴으로 만들었지만 외부에서 한 번도 구독하지 않음
- **원인**: 초기 설계에서 외부 구독을 예상했으나 실제로는 내부 `.value` 접근만 사용
- **수정**: `private var recognizedLandmarkId: String? = null`로 단순화
- **규칙 승격**: ✅ AGENTS.md 금지 목록: "StateFlow를 외부 미구독 상태로 만들기"

### [2026-04-09] bearing 계산 중복 실행
- **발견 위치**: HeritageRecognitionPipeline.kt `processFrame()`
- **패턴**: `getRankedCandidates()`, `debugCandidates`, `buildCandidateHint()` 3곳에서 동일한 `calcBearing()` 반복
- **원인**: 함수 반환값에 계산 결과를 포함하지 않아 호출자가 다시 계산
- **수정**: `getRankedCandidates()` → `List<Pair<HeritageContent, Float>>` 반환으로 1회 계산 재활용
- **규칙 승격**: 미반영 (일반적 성능 최적화 패턴, 특정 규칙으로 승격 불필요)

### [2026-04-09] combine 대신 .value 직접 접근 → race condition
- **발견 위치**: ARViewModel.kt `nextPoiIds`
- **패턴**: `.map { ctx -> ... _discoveredHeritages.value ... }` — Flow 안에서 다른 StateFlow의 `.value` 직접 읽기
- **원인**: `combine()`을 쓰면 두 소스 모두 변경 시 재계산되지만, `.value`는 호출 시점의 스냅샷만 사용
- **수정**: `combine(spatialContext, _discoveredHeritages) { ctx, discovered -> ... }` 로 변경
- **규칙 승격**: ✅ CLAUDE.md "GPS/센서" 섹션, AGENTS.md 금지 목록

### [2026-04-09] frameBuffer Bitmap 참조 미해제
- **발견 위치**: ARViewModel.kt `startGazeTimer()`, `startSubElementGazeTimer()`
- **패턴**: `frameBuffer`에 카메라 프레임 Bitmap 참조를 추가한 후 pipeline 호출 후 clear 안 함
- **원인**: 다음 타이머 시작 시 `clear()` 호출하지만, 그 사이 GC가 회수 못 하는 기간 존재
- **수정**: `pipeline.processFrame()` 직후 `frameBuffer.clear()` 추가
- **규칙 승격**: ✅ CLAUDE.md "메모리 관리" 섹션

### [2026-04-09] 미사용 파라미터/import 방치
- **발견 위치**: ARViewModel.kt `savedStateHandle`, `LocationProvider` import
- **패턴**: 리팩토링 후 더 이상 사용하지 않는 파라미터와 import가 남아있음
- **원인**: IDE 자동 정리 없이 수동 편집 시 놓치기 쉬움
- **수정**: 미사용 항목 제거
- **규칙 승격**: ✅ AGENTS.md 금지 목록: "미사용 import/변수 방치"

### [2026-04-09] POLL_INTERVAL_MS 코드-문서 불일치
- **발견 위치**: OutdoorSpatialProvider.kt
- **패턴**: 주석과 PROJECT_MAP.md는 "5초"라고 명시, 실제 코드는 `1500L` (1.5초)
- **원인**: 테스트 중 값을 변경하고 되돌리지 않음
- **수정**: `5000L`로 복원, 주석은 `POLL_INTERVAL_MS` 참조로 변경
- **규칙 승격**: ✅ AGENTS.md 체크리스트: 상수 변경 시 PROJECT_MAP.md 동기화

### [2026-04-09] askQuestion 미사용 errorMsg 변수
- **발견 위치**: GeminiClient.kt `askQuestion()`
- **패턴**: `errorMsg` 변수를 정의하고 catch 블록에서 사용하지 않음 (직접 문자열 사용)
- **원인**: 에러 처리 리팩토링 후 잔재
- **수정**: 미사용 변수 제거
- **규칙 승격**: ✅ AGENTS.md 금지 목록 (미사용 변수 방치)

### [2026-04-09] onCleared에서 lastCapturedFrame/frameBuffer 미정리
- **발견 위치**: ARViewModel.kt onCleared()
- **패턴**: `arSensorProvider.stop()`, `ttsManager.shutdown()`은 호출하지만 Bitmap 참조 정리 누락
- **원인**: 센서/TTS는 명시적 정리가 필요하다고 인식했지만, Bitmap 참조 해제는 간과
- **수정**: `lastCapturedFrame = null` + `frameBuffer.clear()` 추가
- **규칙 승격**: ✅ CLAUDE.md 메모리 관리 섹션 갱신, PROJECT_MAP.md 반영

### [2026-04-09] pre-commit hook shell 호환성 버그 3건
- **발견 위치**: .git/hooks/pre-commit
- **패턴**: `continue 2` (중첩루프 아닌데 사용), `echo -n` (zsh 비호환), 금지파일 패턴 불완전
- **원인**: bash 스크립트 작성 시 macOS zsh 환경 미고려
- **수정**: `printf` 전환, 루프 로직 재구성, 패턴 보강, 6번 검사(try-finally) 추가
- **규칙 승격**: 미반영 (hook 자체의 버그, 코드 규칙 아님)

### [2026-04-09] .gitignore *.txt 패턴이 과도하게 광범위
- **발견 위치**: .gitignore
- **패턴**: `*.txt`가 모든 텍스트 파일을 무시 → 향후 .txt 문서 파일도 무시될 위험
- **원인**: 빌드 로그 파일(build_output.txt 등)을 무시하려다 패턴을 너무 넓게 설정
- **수정**: `build_*.txt`, `compile_*.txt`, `output*.log`로 구체화
- **규칙 승격**: 미반영 (gitignore 관리 패턴, 코드 규칙 아님)

## 2026-04-10 ~ 2026-04-12 세션 기록

### [2026-04-10] CameraScreen에서 DI 우회 (default parameter로 GeminiClient() 직접 생성)
- **발견 위치**: CameraScreen.kt 라인 60, 722
- **패턴**: `remember { LocalFirstDocentGateway(GeminiClient()) }` — Hilt 싱글톤 우회
- **원인**: Composable 함수에서 default parameter 사용 시 DI 그래프 밖에서 인스턴스 생성
- **수정**: ViewModel.gateway 경유로 변경. QuestionBottomSheet의 default 제거.
- **규칙 승격**: ✅ AGENTS.md "Composable에서 직접 인스턴스 생성 금지" 패턴 인식

### [2026-04-10] OutdoorSpatialProvider cold flow → 구독자마다 중복 폴링
- **발견 위치**: OutdoorSpatialProvider.kt getSpatialContext()
- **패턴**: cold flow라서 MemorySessionHandler 추가 구독 시 GPS 폴링 2배
- **원인**: `flow { while(true) { ... } }`는 collect마다 독립 코루틴
- **수정**: `shareIn(WhileSubscribed(5000), replay=1)` hot flow 전환
- **규칙 승격**: ✅ CLAUDE.md "GPS 폴링은 한 곳에서만" 패턴 강화

### [2026-04-10] 위치 비교에 위경도 도(degree) 차이 사용 → 위도별 부정확
- **발견 위치**: MemorySessionHandler.kt LOCATION_THRESHOLD_DEG
- **패턴**: `0.0001°` 임계값이 적도에서 11m이지만 서울에서 경도 방향 8.9m
- **원인**: 경도 1°의 실제 거리가 위도에 따라 다름
- **수정**: SpatialCalculator.calcDistanceM() hav 거리 기반으로 교체 (LOCATION_THRESHOLD_M=10f)
- **규칙 승격**: ✅ CLAUDE.md "GPS 비교는 hav 거리 사용" 패턴 추가

### [2026-04-12] 문서(MD파일) 자동 갱신 누락 — 코드 변경 후 문서 미동기화
- **발견 위치**: PROJECT_MAP.md, CLAUDE.md, AGENTS.md, FEEDBACK_LOG.md
- **패턴**: 4/10~4/12 사이 대규모 아키텍처 변경(서버 4개 서비스, EventBus 연결, RemoteGateway 등)이 문서에 반영 안 됨
- **원인**: pre-commit hook이 .kt 파일만 감지, .py 파일 변경은 감지 안 함. 서버 서비스 관련 규칙도 AGENTS.md에 없었음
- **수정**: 전체 문서 일괄 갱신 + pre-commit hook에 .py + services/ 감지 추가
- **규칙 승격**: ✅ pre-commit hook 강화, AGENTS.md 체크리스트에 서버 항목 추가

## 2026-04-18 세션 기록

### [2026-04-18] 대규모 하드코딩 데이터 추가 시 파일 크기 급증
- **발견 위치**: LocalHeritageKnowledgeSource.kt (465줄 → 2,042줄)
- **패턴**: 14개 전각 확장 시 단일 파일이 2,000줄 이상으로 증가. 향후 DB 마이그레이션이 필수
- **원인**: 현재 아키텍처가 하드코딩 기반 (HeritageKnowledgeSource interface → LocalHeritageKnowledgeSource 구현)
- **수정**: 현재 아키텍처 유지 (향후 DB 마이그레이션 시 일괄 전환 예정)
- **규칙 승격**: 미반영 (아키텍처 변경은 별도 스프린트)

### [2026-04-18] 광화문 청크에 titleMap/contentMap 누락 (기존 코드)
- **발견 위치**: LocalHeritageKnowledgeSource.kt 광화문 HeritageChunk 4개
- **패턴**: 기존 광화문 청크만 titleMap/contentMap 없이 content만 사용 (근정전/경회루는 완비)
- **원인**: 초기 구현 시 다국어 맵 표준화 전에 추가됨
- **수정**: 이번 세션에서는 미수정 (기존 코드 변경 범위 밖, 별도 작업으로)
- **규칙 승격**: 미반영 (1회성, 향후 청크 추가 시 자연스럽게 해결)

### [2026-04-18] palaceNameMap 반복 패턴 — 모든 전각이 동일 값
- **발견 위치**: Strings.kt palaceNameMap (17개 항목 모두 동일한 경복궁 매핑)
- **패턴**: 전각별로 개별 매핑하지만 값이 모두 동일 → 향후 다른 궁궐 확장 시 구조 변경 필요
- **원인**: 현재 경복궁만 지원, 향후 창덕궁 등 확장 대비 구조
- **수정**: 현재 구조 유지 (기능적으로 문제 없음, 확장 시 fallback 로직 도입 검토)
- **규칙 승격**: 미반영 (설계 결정, 현재 정상 동작)

### [2026-04-18] 서버 knowledge.py 동기화 누락 가능성
- **발견 위치**: services/orchestrator/knowledge.py, services/agent-retrieval/knowledge.py
- **패턴**: Android 데이터 추가 시 서버 knowledge.py에 동기화 필요하지만 자동화 없음
- **원인**: Android↔서버 간 데이터 동기화 메커니즘 부재
- **수정**: orchestrator/knowledge.py에 메타데이터 수동 추가 완료. retrieval/knowledge.py 청크는 2차 작업
- **규칙 승격**: ✅ AGENTS.md 체크리스트에 "Android 전각 추가 시 서버 knowledge.py 동기화" 항목 추가 권고

## 2026-04-19 세션 기록

### [2026-04-19] material-icons-core에 Mic/MicOff/Share 아이콘 없음
- **발견 위치**: CameraScreen.kt, DetailScreen.kt
- **패턴**: `Icons.Filled.Mic`, `Icons.Filled.Share` 사용 시 Unresolved reference
- **원인**: 기본 material-icons-core 라이브러리는 기본 아이콘만 포함. 확장 아이콘은 `androidx.compose.material:material-icons-extended` 의존성 필요
- **수정**: 추가 의존성 도입 대신 emoji(🎤/🎙️/📤)로 대체 — 가볍고 다국어 환경에서도 잘 보임
- **규칙 승격**: ✅ AGENTS.md 체크리스트: "Icons.Filled/Outlined 사용 시 core 포함 여부 확인, 없으면 emoji 대체"

### [2026-04-19] 중국어 스마트쿼트 재오염 (광화문 contentMap)
- **발견 위치**: LocalHeritageKnowledgeSource.kt 광화문 gw_01 zh 콘텐츠
- **패턴**: 4개국어 보완 시 또 다시 중국어 텍스트에 ASCII `"` 삽입
- **원인**: Edit tool 입력에서 중국어 인용 자동 완성이 ASCII 큰따옴표로 변환됨
- **수정**: Python 스크립트로 opening/closing 유니코드 스마트쿼트(\u201C/\u201D)로 자동 교체
- **규칙 승격**: ✅ pre-commit hook에 "중국어 텍스트 라인에 ASCII 큰따옴표 4개 이상" 검사 추가 권고

### [2026-04-19] 유닛 테스트 부재 → 하드코딩 콘텐츠 확장 시 무결성 보장 안 됨
- **발견 위치**: 기존에는 ExampleUnitTest.kt 하나만 존재
- **패턴**: 17개 전각 × 4개국어 × 다양한 필드 조합에서 누락이 발생해도 빌드는 통과
- **원인**: 데이터 무결성(alias, titleMap, chunk linkedChunkId 등)을 보증하는 자동 검증 없음
- **수정**: SpatialCalculator / LocalHeritageKnowledgeSource / RuleRetriever / QuizRepository 테스트 총 37개 추가
- **규칙 승격**: ✅ AGENTS.md 체크리스트: "데이터 추가 시 대응 테스트 업데이트" 추가

## 2026-04-19 세션 3 기록

### [2026-04-19] 대규모 데이터 확장 시 테스트 규칙 유연화 필요
- **발견 위치**: LocalHeritageKnowledgeSourceTest - `every chunk has all 4 language contentMap`
- **패턴**: PDF 공식 청크(pdf_official)는 ko 원문만 있어 4개국어 강제 테스트 실패
- **원인**: 테스트가 "모든 청크 = 4개국어"로 하드코딩되어 일부 특수 청크 유형 수용 불가
- **수정**: `filterNot { endsWith("_pdf_official") }`로 예외 처리 + 별도 테스트로 "공식 청크 존재 + ko 내용 유효" 검증
- **규칙 승격**: ✅ 특수 청크 유형 도입 시 테스트 분리 원칙 확립

## 2026-04-19 세션 2 기록

### [2026-04-19] macOS HFS 파일명 NFD 유니코드 정규화 문제
- **발견 위치**: scripts/extract_pdfs.py (한글 포함 PDF 파일명 매칭)
- **패턴**: macOS 파일시스템에서 한글 파일명이 NFD 형태로 저장되어 `re.search('경복궁', name)`가 실패
- **원인**: Python은 NFC 문자열로 패턴 매칭하지만 HFS+는 NFD 분해 형태 저장
- **수정**: `unicodedata.normalize('NFC', filename)` 선처리 후 매칭
- **규칙 승격**: ✅ 스크립트에 "macOS 한글 파일명 처리 시 NFC 정규화 필수" 주석 추가

### [2026-04-19] Python 3.9에서 `str | None` 새 유니온 문법 미지원
- **발견 위치**: services/agent-retrieval/knowledge.py, models.py
- **패턴**: `HeritageData | None` 타입 힌트가 Python 3.10+ 문법이라 3.9에서 TypeError
- **원인**: PEP 604 union syntax는 3.10부터 런타임 평가 지원. Pydantic은 런타임 평가 필요
- **수정**: `from __future__ import annotations` 추가 + `eval_type_backport` 설치로 Pydantic 보완
- **규칙 승격**: 미반영 (환경 의존, 프로덕션에서는 Python 3.10+ 사용 권장)

### [2026-04-19] material-icons-extended 없이 이미지 로딩 해결
- **발견 위치**: DetailScreen 이미지 표시 요구사항
- **패턴**: Coil/Glide 같은 외부 라이브러리 없이도 assets 이미지 로딩 필요
- **원인**: 프로젝트 원칙 — 외부 의존성 최소화
- **수정**: BitmapFactory + LaunchedEffect로 커스텀 AssetImage Composable 작성. inSampleSize로 메모리 절감
- **규칙 승격**: ✅ CLAUDE.md "외부 라이브러리 전 로컬 BitmapFactory 검토" 패턴 추가 권고

---

## 패턴 통계

| 카테고리 | 발견 횟수 | 규칙 승격 여부 |
|----------|----------|--------------|
| 메모리 누수 (Bitmap) | 4 | ✅ AGENTS.md + CLAUDE.md + hook |
| Dead code / 미사용 | 3 | ✅ AGENTS.md |
| Race condition (Flow) | 1 | ✅ CLAUDE.md + AGENTS.md |
| 코드-문서 불일치 | 2 | ✅ AGENTS.md 체크리스트 + hook 강화 |
| DI 우회 (Composable) | 1 | ✅ ViewModel 경유로 수정 |
| GPS 중복 폴링 | 1 | ✅ hot flow 전환 |
| 위경도 비교 부정확 | 1 | ✅ hav 거리 전환 |
| 문서 자동갱신 누락 | 1 | ✅ hook 강화 + AGENTS.md |
| 중복 계산 | 1 | 미반영 (일반적 최적화) |
| Shell 호환성 (hook) | 1 | 미반영 (인프라 버그) |
| gitignore 과대 범위 | 1 | 미반영 (설정 관리) |
| 하드코딩 파일 크기 급증 | 1 | 미반영 (DB 마이그레이션 예정) |
| 다국어 맵 일관성 누락 | 1 | 미반영 (기존 코드, 별도 보완) |
| 서버 데이터 동기화 누락 | 1 | ✅ AGENTS.md 체크리스트 추가 권고 |
| Icons 확장 라이브러리 미포함 | 1 | ✅ emoji 대체 패턴 확립 |
| 중국어 스마트쿼트 재오염 | 2 | ✅ pre-commit hook 추가 권고 |
| 유닛 테스트 부재 | 1 | ✅ 데이터 무결성 테스트 37개 추가 |
