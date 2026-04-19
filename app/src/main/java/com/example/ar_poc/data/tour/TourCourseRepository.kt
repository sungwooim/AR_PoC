package com.example.ar_poc.data.tour

import com.example.ar_poc.domain.model.CourseWaypoint
import com.example.ar_poc.domain.model.TourCourse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 경복궁 관람 코스 하드코딩 저장소.
 *
 * 궁능유적본부 관람안내 PDF(관람코스_경복궁)의 40분 / 60분 / 90분 코스 기준.
 * 웨이포인트 좌표는 실제 전각 GPS + 건물 사이 통행 경로 보정점으로 구성.
 *
 * 각 stop waypoint의 좌표는 [LocalHeritageKnowledgeSource]의 heritage 좌표와 일치해야 한다.
 * (현장 테스트 결과로 미세 조정 가능)
 */
@Singleton
class TourCourseRepository @Inject constructor() {

    private val courses: List<TourCourse> = listOf(
        // ═══════════════════════════════════════════════════════════════
        // 40분 코스 — 경복궁 핵심 전각 빠르게 둘러보기
        // ═══════════════════════════════════════════════════════════════
        TourCourse(
            id = "course_40",
            durationMinutes = 40,
            nameMap = mapOf(
                "ko" to "40분 핵심 코스",
                "en" to "40-min Essential Course",
                "ja" to "40分 ハイライトコース",
                "zh" to "40分钟 精华路线"
            ),
            descriptionMap = mapOf(
                "ko" to "경복궁의 핵심 전각 5곳을 빠르게 둘러보는 코스입니다. 시간이 제한된 관람객에게 추천합니다.",
                "en" to "A quick tour covering the 5 most essential halls of Gyeongbokgung. Recommended for visitors with limited time.",
                "ja" to "景福宮の中核となる5つの殿閣を素早く巡るコース。時間に限りのある方におすすめ。",
                "zh" to "快速游览景福宫五处核心殿阁。适合时间紧张的游客。"
            ),
            approxDistanceM = 800,
            waypoints = listOf(
                stopGate(1, "gate_heungnyemun", 37.5770, 126.9769, "흥례문", "Heungnyemun", "興禮門", "兴礼门"),
                path(2, 37.5779, 126.9770),
                stopHeritage(3, "geunjeongmun", 37.5788, 126.9770, "근정문", "Geunjeongmun", "勤政門", "勤政门"),
                stopHeritage(4, "geunjeongjeon", 37.5796, 126.9770, "근정전", "Geunjeongjeon", "勤政殿", "勤政殿"),
                stopHeritage(5, "sajeongjeon", 37.5802, 126.9770, "사정전", "Sajeongjeon", "思政殿", "思政殿"),
                stopHeritage(6, "gangnyeongjeon", 37.5808, 126.9770, "강녕전", "Gangnyeongjeon", "康寧殿", "康宁殿"),
                stopHeritage(7, "gyotaejeon", 37.5813, 126.9770, "교태전", "Gyotaejeon", "交泰殿", "交泰殿"),
                // 남쪽 방향 출구로 돌아가는 경유점
                path(8, 37.5795, 126.9770),
                stopHeritage(9, "gwanghwamun", 37.5759, 126.9769, "광화문", "Gwanghwamun", "光化門", "光化门")
            )
        ),

        // ═══════════════════════════════════════════════════════════════
        // 60분 코스 — 경회루 포함 주요 전각
        // ═══════════════════════════════════════════════════════════════
        TourCourse(
            id = "course_60",
            durationMinutes = 60,
            nameMap = mapOf(
                "ko" to "60분 주요 코스",
                "en" to "60-min Standard Course",
                "ja" to "60分 標準コース",
                "zh" to "60分钟 标准路线"
            ),
            descriptionMap = mapOf(
                "ko" to "40분 코스에 수정전·경회루·자경전을 더한 표준 코스입니다. 가장 권장되는 관람 동선입니다.",
                "en" to "The standard route adding Sujeongjeon, Gyeonghoeru pavilion, and Jagyeongjeon. The most recommended tour.",
                "ja" to "40分コースに修政殿・慶会楼・慈慶殿を加えた標準コース。最もおすすめの観覧動線。",
                "zh" to "在40分钟路线基础上增加修政殿、庆会楼、慈庆殿的标准路线。最推荐的游览动线。"
            ),
            approxDistanceM = 1500,
            waypoints = listOf(
                stopGate(1, "gate_heungnyemun", 37.5770, 126.9769, "흥례문", "Heungnyemun", "興禮門", "兴礼门"),
                stopHeritage(2, "geunjeongmun", 37.5788, 126.9770, "근정문", "Geunjeongmun", "勤政門", "勤政门"),
                stopHeritage(3, "geunjeongjeon", 37.5796, 126.9770, "근정전", "Geunjeongjeon", "勤政殿", "勤政殿"),
                stopHeritage(4, "sajeongjeon", 37.5802, 126.9770, "사정전", "Sajeongjeon", "思政殿", "思政殿"),
                // 서쪽 수정전·경회루로 이동하는 경유점
                path(5, 37.5802, 126.9765),
                stopHeritage(6, "sujeongjeon", 37.5801, 126.9760, "수정전", "Sujeongjeon", "修政殿", "修政殿"),
                stopHeritage(7, "gyeonghoeru", 37.5800, 126.9749, "경회루", "Gyeonghoeru", "慶會樓", "庆会楼"),
                // 다시 동쪽 강녕전으로
                path(8, 37.5805, 126.9760),
                stopHeritage(9, "gangnyeongjeon", 37.5808, 126.9770, "강녕전", "Gangnyeongjeon", "康寧殿", "康宁殿"),
                stopHeritage(10, "gyotaejeon", 37.5813, 126.9770, "교태전", "Gyotaejeon", "交泰殿", "交泰殿"),
                // 북동쪽 자경전으로
                path(11, 37.5813, 126.9775),
                stopHeritage(12, "jagyeongjeon", 37.5812, 126.9779, "자경전", "Jagyeongjeon", "慈慶殿", "慈庆殿"),
                // 남쪽 광화문 출구로
                path(13, 37.5795, 126.9775),
                stopHeritage(14, "gwanghwamun", 37.5759, 126.9769, "광화문", "Gwanghwamun", "光化門", "光化门")
            )
        ),

        // ═══════════════════════════════════════════════════════════════
        // 90분 코스 — 경복궁 완주
        // ═══════════════════════════════════════════════════════════════
        TourCourse(
            id = "course_90",
            durationMinutes = 90,
            nameMap = mapOf(
                "ko" to "90분 완주 코스",
                "en" to "90-min Full Tour",
                "ja" to "90分 完走コース",
                "zh" to "90分钟 全程路线"
            ),
            descriptionMap = mapOf(
                "ko" to "경복궁 전역 13개 전각을 모두 둘러보는 완주 코스. 후원 영역(향원정·건청궁·집옥재)까지 포함.",
                "en" to "The complete tour of all 13 main halls, including the rear garden (Hyangwonjeong, Geoncheongung, Jibokjae).",
                "ja" to "景福宮全域13の殿閣を巡る完走コース。後苑エリア（香遠亭・乾清宮・集玉斎）まで含む。",
                "zh" to "游览景福宫全部13处主要殿阁的完整路线。包括后苑区域（香远亭、乾清宫、集玉斋）。"
            ),
            approxDistanceM = 2500,
            waypoints = listOf(
                stopGate(1, "gate_heungnyemun", 37.5770, 126.9769, "흥례문", "Heungnyemun", "興禮門", "兴礼门"),
                stopHeritage(2, "geunjeongmun", 37.5788, 126.9770, "근정문", "Geunjeongmun", "勤政門", "勤政门"),
                stopHeritage(3, "geunjeongjeon", 37.5796, 126.9770, "근정전", "Geunjeongjeon", "勤政殿", "勤政殿"),
                stopHeritage(4, "sajeongjeon", 37.5802, 126.9770, "사정전", "Sajeongjeon", "思政殿", "思政殿"),
                path(5, 37.5802, 126.9765),
                stopHeritage(6, "sujeongjeon", 37.5801, 126.9760, "수정전", "Sujeongjeon", "修政殿", "修政殿"),
                stopHeritage(7, "gyeonghoeru", 37.5800, 126.9749, "경회루", "Gyeonghoeru", "慶會樓", "庆会楼"),
                path(8, 37.5805, 126.9760),
                stopHeritage(9, "gangnyeongjeon", 37.5808, 126.9770, "강녕전", "Gangnyeongjeon", "康寧殿", "康宁殿"),
                stopHeritage(10, "gyotaejeon", 37.5813, 126.9770, "교태전", "Gyotaejeon", "交泰殿", "交泰殿"),
                path(11, 37.5813, 126.9775),
                stopHeritage(12, "jagyeongjeon", 37.5812, 126.9779, "자경전", "Jagyeongjeon", "慈慶殿", "慈庆殿"),
                // 동궁 영역
                path(13, 37.5806, 126.9780),
                stopHeritage(14, "donggung", 37.5803, 126.9782, "동궁", "Donggung", "東宮", "东宫"),
                // 북쪽 후원 영역
                path(15, 37.5815, 126.9775),
                stopHeritage(16, "hyangwonjeong", 37.5822, 126.9768, "향원정", "Hyangwonjeong", "香遠亭", "香远亭"),
                stopHeritage(17, "geoncheongung", 37.5826, 126.9770, "건청궁", "Geoncheongung", "乾清宮", "乾清宫"),
                stopHeritage(18, "jibokjae", 37.5829, 126.9775, "집옥재", "Jibokjae", "集玉齋", "集玉斋"),
                // 서쪽 태원전
                path(19, 37.5822, 126.9765),
                stopHeritage(20, "taeweonjeon", 37.5815, 126.9755, "태원전", "Taeweonjeon", "泰元殿", "泰元殿"),
                // 북문(신무문) 출구
                path(21, 37.5822, 126.9760),
                stopGate(22, "gate_north", 37.5830, 126.9766, "신무문", "Sinmumun", "神武門", "神武门")
            )
        )
    )

    fun getCourses(): List<TourCourse> = courses

    fun getCourseById(id: String): TourCourse? = courses.find { it.id == id }

    // ─────────────────────────────────────────────────────────
    // 헬퍼 함수 — 가독성을 위한 DSL 스타일
    // ─────────────────────────────────────────────────────────

    /** Heritage를 방문지로 사용하는 waypoint 생성. */
    private fun stopHeritage(
        order: Int, id: String, lat: Double, lng: Double,
        ko: String, en: String, ja: String, zh: String
    ) = CourseWaypoint(
        order = order,
        heritageId = id,
        latitude = lat,
        longitude = lng,
        displayNameMap = mapOf("ko" to ko, "en" to en, "ja" to ja, "zh" to zh)
    )

    /** extraPoiList의 POI를 방문지로 사용하는 waypoint 생성. */
    private fun stopGate(
        order: Int, id: String, lat: Double, lng: Double,
        ko: String, en: String, ja: String, zh: String
    ) = CourseWaypoint(
        order = order,
        poiId = id,
        latitude = lat,
        longitude = lng,
        displayNameMap = mapOf("ko" to ko, "en" to en, "ja" to ja, "zh" to zh)
    )

    /** 건물 사이 경로 보정용 경유점 (마커 표시 안 됨, Polyline만). */
    private fun path(order: Int, lat: Double, lng: Double) = CourseWaypoint(
        order = order,
        latitude = lat,
        longitude = lng
    )
}
