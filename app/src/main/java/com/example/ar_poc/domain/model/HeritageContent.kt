package com.example.ar_poc.domain.model

/**
 * Structured heritage information for the "Retrieval-First" architecture.
 */
data class HeritageContent(
    val id: String,
    val title: String,
    val titleMap: Map<String, String> = emptyMap(),
    val aliases: List<String>,
    val palace: String,
    val zone: String,
    val shortDescription: String,
    val shortDescMap: Map<String, String> = emptyMap(),
    val chunks: List<HeritageChunk>,
    /** GPS centre of the physical building — used for bearing-based candidate ranking. */
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    /** Visually recognisable sub-elements within / on this building. */
    val subElements: List<SubElement> = emptyList(),
    /**
     * 대표 이미지 asset 경로 (상대).
     * 예: "heritage/geunjeongjeon_cover.jpg" → app/src/main/assets/heritage/geunjeongjeon_cover.jpg
     * null 또는 공백이면 이미지 없음 — DetailScreen은 플레이스홀더 표시.
     */
    val coverImageAsset: String? = null,
    /**
     * 갤러리 이미지 asset 경로 목록 (cover 외).
     * DetailScreen에서 수평 슬라이드로 표시.
     * 경로 예: "heritage/geunjeongjeon_1.jpg", "heritage/geunjeongjeon_2.jpg", ...
     */
    val galleryImageAssets: List<String> = emptyList()
)

data class HeritageChunk(
    val chunkId: String,
    val section: String,
    val title: String,
    val titleMap: Map<String, String> = emptyMap(),
    val keywords: List<String>,
    val content: String,
    val contentMap: Map<String, String> = emptyMap()
)

/**
 * A visually distinct architectural or decorative element belonging to a building.
 *
 * @param id          Stable identifier used for UI and retrieval
 * @param displayName Localized display names mapped by language code (e.g., "ko", "en")
 * @param visualHints Short English descriptions sent to Gemini Vision as candidate options
 * @param linkedChunkId The HeritageChunk that explains this element in detail (nullable)
 */
data class SubElement(
    val id: String,
    val displayName: Map<String, String>,
    val visualHints: List<String>,   // these go into the Gemini candidate list
    val linkedChunkId: String? = null,
    /**
     * 어디에서 이 보물을 찾을 수 있는지에 대한 다국어 힌트.
     * 예: "근정전 내부 천장 중앙을 올려다보세요" (칠조룡)
     * StampCollectionScreen에서 미발견 상태일 때 사용자에게 보여준다.
     */
    val locationHint: Map<String, String> = emptyMap()
)
