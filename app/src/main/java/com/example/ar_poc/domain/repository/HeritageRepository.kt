package com.example.ar_poc.domain.repository

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
 */
class HeritageRepository(
    private val knowledgeSource: HeritageKnowledgeSource = LocalHeritageKnowledgeSource()
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
                "gwanghwamun" -> PoiType.GATE
                else -> PoiType.HERITAGE
            }
            heritage.toPoi(type)
        }
        val extraPois = knowledgeSource.getExtraPoiList()
        return heritagePois + extraPois
    }

    /**
     * Retrieves and ranks chunks relevant to the query using multi-field scoring.
     *
     * Scoring weights:
     *   +4  keyword stems match query stems (bi-directional)
     *   +3  keyword exact match
     *   +2  chunk title match
     *   +1  chunk body (content) match
     *
     * Falls back to ALL chunks if no match found (never sends empty context to Gemini).
     */
    fun getRelevantChunks(landmarkId: String, query: String? = null): List<HeritageChunk> {
        val content = getHeritageContent(landmarkId) ?: return emptyList()
        if (query == null) return content.chunks

        val queryStems = extractStems(query)
        if (queryStems.isEmpty()) return content.chunks

        val scored = content.chunks.mapNotNull { chunk ->
            var score = 0
            
            // 0. Direct chunk ID match (+100)
            if (chunk.chunkId == query) score += 100

            val chunkKeywords = chunk.keywords.map { it.lowercase() }
            
            for (token in queryStems) {
                // 1. Exact match (+5)
                if (chunkKeywords.any { kw -> kw == token }) score += 5
                
                // 2. Partial stem match (+3): handles compound nouns like "건축물" -> "건축"
                if (chunkKeywords.any { kw -> token.contains(kw) || kw.contains(token) }) score += 3
                
                // 3. Title match (+2)
                if (chunk.title.lowercase().contains(token)) score += 2
                
                // 4. Content match (+1)
                if (chunk.content.lowercase().contains(token)) score += 1
            }
            if (score > 0) Pair(chunk, score) else null
        }

        return if (scored.isEmpty()) {
            content.chunks  // fallback: all chunks
        } else {
            scored.sortedByDescending { it.second }.map { it.first }
        }
    }

    /**
     * Extracts meaningful stems from a query string.
     *
     * Handles:
     * - Korean verb/adjective endings (한가요, 인가요, 인지, 나요, 습니까, 었나요, 이죠 ...)
     * - Korean particles (은/는/이/가/을/를/의/에/서/로/부터/까지)
     * - Split by spaces and punctuation
     * - Removes tokens shorter than 2 chars (after stemming)
     */
    private fun extractStems(query: String): List<String> {
        val stopWords = listOf(
            "어떤", "어떻게", "무엇", "무슨", "알려줘", "설명해줘", "알려주세요",
            "대해", "대해서", "대한", "있는", "있나요", "인가요", "인지"
        )
        val koreanEndings = listOf(
            "한가요", "인가요", "했나요", "었나요", "셨나요", "하나요",
            "일까요", "ㄹ까요", "을까요", "는지요", "인지요", "이죠",
            "습니까", "ㅂ니까", "나요", "군요", "네요",
            "했는지", "하는지", "하는데", "인데", "이에요", "예요",
            "했어요", "해요", "이야", "이야요", "습니다", "합니다"
        )
        val koreanParticles = listOf(
            "들은", "들이", "들을", "은", "는", "이", "가", "을", "를", 
            "의", "에", "게", "서", "로", "으로", "부터", "까지", "와", "과", "에서"
        )
        val compoundSuffixes = listOf(
            "물", "양식", "구조", "형태", "모습", "특징", "의미", "역사", "이유"
        )

        return query
            .lowercase()
            .split(" ", "?", ".", ",", "!", "·", "~", "\n", "'", "\"")
            .filter { it.isNotBlank() && !stopWords.contains(it) }
            .flatMap { token ->
                var stem = token
                // 1. Strip verb endings
                for (ending in koreanEndings.sortedByDescending { it.length }) {
                    if (stem.endsWith(ending) && stem.length > ending.length + 1) {
                        stem = stem.dropLast(ending.length)
                        break
                    }
                }
                // 2. Strip particles
                for (particle in koreanParticles.sortedByDescending { it.length }) {
                    if (stem.endsWith(particle) && stem.length > particle.length + 1) {
                        stem = stem.dropLast(particle.length)
                        break
                    }
                }
                
                // 3. Extract core noun from common compounds (e.g. "건축물" -> "건축", "건축물")
                val results = mutableListOf(stem)
                if (stem.length >= 3) {
                    for (suffix in compoundSuffixes) {
                        if (stem.endsWith(suffix)) {
                            results.add(stem.dropLast(suffix.length))
                            break
                        }
                    }
                }
                results
            }
            .filter { it.length >= 2 }
            .distinct()
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
