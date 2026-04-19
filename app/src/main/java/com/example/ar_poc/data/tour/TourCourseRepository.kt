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

    // ─────────────────────────────────────────────────────────
    // 경회루 마커 위치 override.
    // 실제 pavilion은 연못(126.9749) 중앙이지만, 코스에서는 연못 동쪽 다리 입구
    // (조용한 누각 시야 지점)에 마커를 배치해 경로가 물을 가로지르지 않도록 함.
    // Heritage recognition은 original 좌표(pavilion 중앙)를 그대로 사용.
    // ─────────────────────────────────────────────────────────
    private val gyeonghoeruViewLat = 37.5800
    private val gyeonghoeruViewLng = 126.9755

    private val courses: List<TourCourse> = listOf(
        // ═══════════════════════════════════════════════════════════════
        // 40분 코스 — 핵심 4곳
        // 1.근정전 → 2.수정전 → 3.경회루 → 4.사정전
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
                "ko" to "근정전·수정전·경회루·사정전을 짧은 시간에 둘러보는 핵심 4곳 코스.",
                "en" to "A quick tour of the 4 essentials: Geunjeongjeon, Sujeongjeon, Gyeonghoeru, Sajeongjeon.",
                "ja" to "勤政殿・修政殿・慶会楼・思政殿の4か所を短時間で巡るコース。",
                "zh" to "快速游览勤政殿、修政殿、庆会楼、思政殿四处精华。"
            ),
            approxDistanceM = 400,
            waypoints = listOf(
                stopHeritage(1, "geunjeongjeon", 37.5796, 126.9770, "근정전", "Geunjeongjeon", "勤政殿", "勤政殿"),
                // 근정전 → 수정전 (서쪽)
                path(2, 37.5799, 126.9765),
                stopHeritage(3, "sujeongjeon", 37.5801, 126.9760, "수정전", "Sujeongjeon", "修政殿", "修政殿"),
                // 수정전 → 경회루 동쪽 뷰포인트 (조용한 연못 옆)
                path(4, 37.5800, 126.9758),
                stopHeritage(5, "gyeonghoeru", gyeonghoeruViewLat, gyeonghoeruViewLng, "경회루", "Gyeonghoeru", "慶會樓", "庆会楼"),
                // 경회루 → 사정전 (동쪽 복귀)
                path(6, 37.5802, 126.9765),
                stopHeritage(7, "sajeongjeon", 37.5802, 126.9770, "사정전", "Sajeongjeon", "思政殿", "思政殿")
            )
        ),

        // ═══════════════════════════════════════════════════════════════
        // 60분 코스 — 사용자 지정 10곳 순서
        // 1.근정전 2.수정전 3.경회루 4.사정전 5.강녕전 6.교태전
        // 7.흥복전 8.동궁 9.소주방 10.자경전
        // ═══════════════════════════════════════════════════════════════
        TourCourse(
            id = "course_60",
            durationMinutes = 60,
            nameMap = mapOf(
                "ko" to "60분 표준 코스",
                "en" to "60-min Standard Course",
                "ja" to "60分 標準コース",
                "zh" to "60分钟 标准路线"
            ),
            descriptionMap = mapOf(
                "ko" to "40분 코스에 강녕전·교태전·흥복전·동궁·소주방·자경전을 더한 표준 10곳 코스.",
                "en" to "Standard 10-stop tour adding Gangnyeongjeon, Gyotaejeon, Heungbokjeon, Donggung, Sojubang, Jagyeongjeon.",
                "ja" to "40分コースに康寧殿・交泰殿・興福殿・東宮・焼厨房・慈慶殿を加えた標準10か所コース。",
                "zh" to "在40分钟路线上增加康宁殿、交泰殿、兴福殿、东宫、烧厨房、慈庆殿的10处标准路线。"
            ),
            approxDistanceM = 1000,
            waypoints = listOf(
                stopHeritage(1, "geunjeongjeon", 37.5796, 126.9770, "근정전", "Geunjeongjeon", "勤政殿", "勤政殿"),
                path(2, 37.5799, 126.9765),
                stopHeritage(3, "sujeongjeon", 37.5801, 126.9760, "수정전", "Sujeongjeon", "修政殿", "修政殿"),
                path(4, 37.5800, 126.9758),
                stopHeritage(5, "gyeonghoeru", gyeonghoeruViewLat, gyeonghoeruViewLng, "경회루", "Gyeonghoeru", "慶會樓", "庆会楼"),
                path(6, 37.5802, 126.9765),
                stopHeritage(7, "sajeongjeon", 37.5802, 126.9770, "사정전", "Sajeongjeon", "思政殿", "思政殿"),
                stopHeritage(8, "gangnyeongjeon", 37.5808, 126.9770, "강녕전", "Gangnyeongjeon", "康寧殿", "康宁殿"),
                stopHeritage(9, "gyotaejeon", 37.5813, 126.9770, "교태전", "Gyotaejeon", "交泰殿", "交泰殿"),
                // 교태전 → 흥복전 (서쪽)
                path(10, 37.5815, 126.9766),
                stopHeritage(11, "heungbokjeon", 37.5815, 126.9762, "흥복전", "Heungbokjeon", "興福殿", "兴福殿"),
                // 흥복전 → 동궁 (남동쪽)
                path(12, 37.5810, 126.9770),
                path(13, 37.5806, 126.9780),
                stopHeritage(14, "donggung", 37.5803, 126.9782, "동궁", "Donggung", "東宮", "东宫"),
                // 동궁 → 소주방 (북서쪽으로 복귀)
                path(15, 37.5808, 126.9778),
                stopHeritage(16, "sojubang", 37.5810, 126.9775, "소주방", "Sojubang", "焼厨房", "烧厨房"),
                // 소주방 → 자경전 (북동쪽)
                stopHeritage(17, "jagyeongjeon", 37.5812, 126.9779, "자경전", "Jagyeongjeon", "慈慶殿", "慈庆殿")
            )
        ),

        // ═══════════════════════════════════════════════════════════════
        // 90분 코스 — 사용자 지정 14곳 완주 순서
        // 1.근정전 2.수정전 3.경회루 4.사정전 5.강녕전 6.교태전
        // 7.흥복전 8.동궁 9.소주방 10.자경전
        // 11.함화당·집경당 12.향원정·건청궁 13.집옥재 14.태원전
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
                "ko" to "경복궁 14곳을 모두 둘러보는 완주 코스. 함화당·집경당, 향원정·건청궁, 집옥재, 태원전까지 후원 영역 포함.",
                "en" to "Complete 14-stop tour including the rear garden (Hamhwadang-Jipgyeongdang, Hyangwonjeong-Geoncheongung, Jibokjae, Taeweonjeon).",
                "ja" to "景福宮14か所を全て巡る完走コース。咸和堂・緝敬堂、香遠亭・乾清宮、集玉斎、泰元殿の後苑エリアまで含む。",
                "zh" to "游览景福宫全部14处的完整路线。包括咸和堂·缉敬堂、香远亭·乾清宫、集玉斋、泰元殿等后苑区域。"
            ),
            approxDistanceM = 1800,
            waypoints = listOf(
                // 1~10 60분 코스와 동일 순서
                stopHeritage(1, "geunjeongjeon", 37.5796, 126.9770, "근정전", "Geunjeongjeon", "勤政殿", "勤政殿"),
                path(2, 37.5799, 126.9765),
                stopHeritage(3, "sujeongjeon", 37.5801, 126.9760, "수정전", "Sujeongjeon", "修政殿", "修政殿"),
                path(4, 37.5800, 126.9758),
                stopHeritage(5, "gyeonghoeru", gyeonghoeruViewLat, gyeonghoeruViewLng, "경회루", "Gyeonghoeru", "慶會樓", "庆会楼"),
                path(6, 37.5802, 126.9765),
                stopHeritage(7, "sajeongjeon", 37.5802, 126.9770, "사정전", "Sajeongjeon", "思政殿", "思政殿"),
                stopHeritage(8, "gangnyeongjeon", 37.5808, 126.9770, "강녕전", "Gangnyeongjeon", "康寧殿", "康宁殿"),
                stopHeritage(9, "gyotaejeon", 37.5813, 126.9770, "교태전", "Gyotaejeon", "交泰殿", "交泰殿"),
                path(10, 37.5815, 126.9766),
                stopHeritage(11, "heungbokjeon", 37.5815, 126.9762, "흥복전", "Heungbokjeon", "興福殿", "兴福殿"),
                path(12, 37.5810, 126.9770),
                path(13, 37.5806, 126.9780),
                stopHeritage(14, "donggung", 37.5803, 126.9782, "동궁", "Donggung", "東宮", "东宫"),
                path(15, 37.5808, 126.9778),
                stopHeritage(16, "sojubang", 37.5810, 126.9775, "소주방", "Sojubang", "焼厨房", "烧厨房"),
                stopHeritage(17, "jagyeongjeon", 37.5812, 126.9779, "자경전", "Jagyeongjeon", "慈慶殿", "慈庆殿"),
                // 11. 함화당·집경당 (자경전 북서쪽)
                path(18, 37.5816, 126.9773),
                stopPoi(19, "hamhwadang_jipgyeongdang", 37.5818, 126.9768,
                    "함화당·집경당", "Hamhwadang & Jipgyeongdang", "咸和堂・緝敬堂", "咸和堂·缉敬堂"),
                // 12. 향원정·건청궁 — 향원지 동쪽·북쪽으로 우회 (물 회피)
                path(20, 37.5823, 126.9776),
                path(21, 37.5826, 126.9770),
                stopHeritage(22, "hyangwonjeong", 37.5822, 126.9768, "향원정·건청궁", "Hyangwonjeong & Geoncheongung", "香遠亭・乾清宮", "香远亭·乾清宫"),
                // (13번은 건청궁으로 카운트하지만 waypoint 번호는 내부 순차)
                path(23, 37.5826, 126.9770),
                // 13. 집옥재
                stopHeritage(24, "jibokjae", 37.5829, 126.9775, "집옥재", "Jibokjae", "集玉齋", "集玉斋"),
                // 14. 태원전 (향원지 서쪽으로 우회)
                path(25, 37.5827, 126.9762),
                path(26, 37.5822, 126.9758),
                stopHeritage(27, "taeweonjeon", 37.5815, 126.9755, "태원전", "Taeweonjeon", "泰元殿", "泰元殿")
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

    /** extraPoiList의 POI(문, 함화당 등)를 방문지로 사용하는 waypoint 생성. */
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

    /** stopGate의 의미상 별칭 — heritage가 아닌 POI 전반용 (함화당 등). */
    private fun stopPoi(
        order: Int, id: String, lat: Double, lng: Double,
        ko: String, en: String, ja: String, zh: String
    ) = stopGate(order, id, lat, lng, ko, en, ja, zh)

    /** 건물 사이 경로 보정용 경유점 (마커 표시 안 됨, Polyline만). */
    private fun path(order: Int, lat: Double, lng: Double) = CourseWaypoint(
        order = order,
        latitude = lat,
        longitude = lng
    )
}
