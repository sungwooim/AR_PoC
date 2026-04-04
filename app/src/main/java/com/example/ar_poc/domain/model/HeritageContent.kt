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
    val subElements: List<SubElement> = emptyList()
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
    val linkedChunkId: String? = null
)
