package com.example.ar_poc.domain.repository

import com.example.ar_poc.core.retrieval.RetrievalEngine
import com.example.ar_poc.core.retrieval.RetrievalQuery
import com.example.ar_poc.core.retrieval.RuleRetriever
import com.example.ar_poc.data.heritage.HeritageKnowledgeSource
import com.example.ar_poc.data.heritage.LocalHeritageKnowledgeSource
import com.example.ar_poc.domain.model.HeritageChunk
import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.PoiType
import com.example.ar_poc.domain.model.toPoi

/**
 * Repository for managing heritage information.
 * Implements the "Retrieval-First" RAG strategy.
 *
 * 검색 로직은 [RetrievalEngine]에 위임한다.
 * 기본값은 [RuleRetriever] (키워드 매칭). 벡터 DB 도입 시 Hilt에서 교체.
 */
class HeritageRepository(
    private val knowledgeSource: HeritageKnowledgeSource = LocalHeritageKnowledgeSource(),
    private val retrievalEngine: RetrievalEngine = RuleRetriever(knowledgeSource)
) {
    fun getHeritageContent(landmarkId: String): HeritageContent? =
        knowledgeSource.getHeritageById(landmarkId)

    fun getHeritageList(): List<HeritageContent> =
        knowledgeSource.getHeritageList()

    /**
     * 유산(HeritageContent → Poi 변환) + 일반 POI(카페, 안내소 등)를 합산하여 반환.
     * PoiType은 id 기반으로 결정: gwanghwamun → GATE, 나머지 유산 → HERITAGE.
     * UI는 이 목록 하나만 바라보면 됨.
     */
    fun getAllPois(): List<Poi> {
        val heritagePois = getHeritageList().map { heritage ->
            val type = when (heritage.id) {
                "gwanghwamun", "geunjeongmun" -> PoiType.GATE
                "yeongjeogyo" -> PoiType.VIEWPOINT
                else -> PoiType.HERITAGE
            }
            val basePoi = heritage.toPoi(type)
            // 경회루는 연못 중앙이 아닌 동쪽 조용한 누각 시야 지점에 마커 표시.
            // Recognition은 기존 heritage 좌표(pavilion 중앙) 그대로 사용해 정확도 유지.
            if (heritage.id == "gyeonghoeru") {
                basePoi.copy(latitude = 37.5800, longitude = 126.9755)
            } else basePoi
        }
        val extraPois = knowledgeSource.getExtraPoiList()
        return heritagePois + extraPois
    }

    /**
     * 쿼리에 대해 관련 청크를 검색한다. RetrievalEngine에 위임.
     */
    fun getRelevantChunks(landmarkId: String, query: String? = null): List<HeritageChunk> {
        val result = retrievalEngine.retrieve(RetrievalQuery(heritageId = landmarkId, query = query))
        return result.chunks.map { it.chunk }
    }

    /**
     * Builds a structured RAG context string from relevant chunks for Gemini.
     * Caps at [maxChunks] to keep prompt tokens reasonable.
     * Includes a note that context is in Korean when query language differs.
     */
    fun buildRagContext(
        landmarkId: String,
        query: String,
        maxChunks: Int = 3
    ): String {
        val content = getHeritageContent(landmarkId) ?: return ""
        val relevantChunks = getRelevantChunks(landmarkId, query).take(maxChunks)

        val chunkContext = relevantChunks.joinToString("\n\n") { chunk ->
            """
            <chunk id="${chunk.chunkId}" section="${chunk.section}">
            TITLE: ${chunk.title}
            CONTENT: ${chunk.content}
            </chunk>
            """.trimIndent()
        }

        return """
            <heritage_info>
              <name>${content.title}</name>
              <location>${content.palace}</location>
              <overview>${content.shortDescription}</overview>
            </heritage_info>

            <relevant_knowledge_chunks source_language="Korean">
            $chunkContext
            </relevant_knowledge_chunks>
        """.trimIndent()
    }
}
