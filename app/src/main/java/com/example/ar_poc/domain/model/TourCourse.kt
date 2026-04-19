package com.example.ar_poc.domain.model

/**
 * 경복궁 관람 코스.
 *
 * 궁능유적본부 관람안내 PDF에 정의된 40/60/90분 코스를 모델링.
 * 각 코스는 순서대로 방문할 [CourseWaypoint] 리스트를 가진다.
 *
 * 라우팅 API(유료)를 사용하지 않고 Polyline으로 직접 그리므로,
 * 경유점을 포함한 [CourseWaypoint] 전체 경로가 곧 지도에 그려지는 선이 된다.
 */
data class TourCourse(
    val id: String,
    /** 분 단위 예상 소요 시간 */
    val durationMinutes: Int,
    val nameMap: Map<String, String>,
    val descriptionMap: Map<String, String>,
    val waypoints: List<CourseWaypoint>,
    /** 대략적 총 이동 거리 (미터) */
    val approxDistanceM: Int
) {
    fun localizedName(lang: String): String =
        nameMap[lang] ?: nameMap["ko"] ?: id

    fun localizedDescription(lang: String): String =
        descriptionMap[lang] ?: descriptionMap["ko"] ?: ""

    /** 순서가 지정된 방문지 (heritageId 또는 poiId가 있는 것만) */
    val stops: List<CourseWaypoint>
        get() = waypoints.filter { it.heritageId != null || it.poiId != null }
}

/**
 * 코스 상의 개별 웨이포인트.
 *
 * 두 종류:
 *  - 방문지 (stop): [heritageId] 또는 [poiId]가 있는 번호가 매겨진 지점
 *  - 경유점 (path): heritage/poi 모두 null — 건물 사이 경로 보정용
 *
 * 경유점은 Polyline 곡선화에 사용되며 지도에 마커로 표시되지 않는다.
 */
data class CourseWaypoint(
    val order: Int,
    val heritageId: String? = null,
    val poiId: String? = null,
    val latitude: Double,
    val longitude: Double,
    val displayNameMap: Map<String, String> = emptyMap()
) {
    fun localizedName(lang: String): String =
        displayNameMap[lang] ?: displayNameMap["ko"] ?: "?"

    val isStop: Boolean get() = heritageId != null || poiId != null
    val targetId: String? get() = heritageId ?: poiId
}
