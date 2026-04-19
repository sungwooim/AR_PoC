package com.example.ar_poc.domain.model

/**
 * Point of Interest — 지도/AR 오버레이에 표시되는 모든 위치 데이터의 상위 개념.
 * 기존 HeritageContent는 변경 없이 linkedHeritage로 포함될 수 있다.
 */
data class Poi(
    val id: String,
    val type: PoiType,
    val title: String,
    val titleMap: Map<String, String>,
    val latitude: Double,
    val longitude: Double,
    /** 유산 건물(heritage/gate 등)인 경우 기존 HeritageContent 연결 (optional) */
    val linkedHeritage: HeritageContent? = null,
    /**
     * POI 대표 이미지 asset 경로 (상대).
     * 예: "heritage/poi_gate_east_cover.jpg"
     * null이면 이미지 없음 — PoiInfoDialog는 아이콘만 표시.
     */
    val imageAsset: String? = null,
    /**
     * POI 설명 (다국어).
     * PDF 원문 기반 짧은 소개. PoiInfoDialog에서 표시.
     */
    val descriptionMap: Map<String, String> = emptyMap()
) {
    /** 표시 언어에 맞는 제목 반환. 없으면 기본 title로 fallback */
    fun localizedTitle(lang: String): String = titleMap[lang] ?: title

    /** 표시 언어에 맞는 설명 반환. 없으면 빈 문자열 */
    fun localizedDescription(lang: String): String =
        descriptionMap[lang] ?: descriptionMap["ko"] ?: ""
}

// ─────────────────────────────────────────────────────────────────────
// PoiType
// ─────────────────────────────────────────────────────────────────────

enum class PoiType(
    /** 지도/오버레이 표시 아이콘 이모지 */
    val icon: String,
    /** 한국어 라벨 */
    val labelKo: String
) {
    HERITAGE("🏛", "문화유산"),
    EXHIBIT("🖼", "전시"),
    GATE("🚪", "문/입구"),
    VIEWPOINT("📷", "전망 포인트"),
    INFO("ℹ️", "안내"),
    CAFE("☕", "카페"),
    SHOP("🛍", "기념품")
}

// ─────────────────────────────────────────────────────────────────────
// 변환 헬퍼
// ─────────────────────────────────────────────────────────────────────

/**
 * 기존 HeritageContent를 Poi로 변환하는 확장 함수.
 * 기존 코드를 수정하지 않고 Poi 시스템으로 점진적 이관 시 사용.
 *
 * @param type 기본값 HERITAGE. 광화문처럼 GATE로 지정할 수도 있음.
 */
fun HeritageContent.toPoi(type: PoiType = PoiType.HERITAGE): Poi = Poi(
    id = this.id,
    type = type,
    title = this.title,
    titleMap = this.titleMap,
    latitude = this.latitude,
    longitude = this.longitude,
    linkedHeritage = this
)
