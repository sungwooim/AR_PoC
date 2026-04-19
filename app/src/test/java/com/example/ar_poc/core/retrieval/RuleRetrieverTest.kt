package com.example.ar_poc.core.retrieval

import com.example.ar_poc.data.heritage.LocalHeritageKnowledgeSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * RuleRetriever 검색 동작 검증.
 *
 * RAG 컨텍스트 빌드의 핵심 로직이므로, 질문 → 관련 청크
 * 매칭이 깨지지 않는지 확인한다.
 */
class RuleRetrieverTest {

    private val source = LocalHeritageKnowledgeSource()
    private val retriever = RuleRetriever(source)

    @Test
    fun `returns all chunks when query is null`() {
        val result = retriever.retrieve(RetrievalQuery(heritageId = "geunjeongjeon", query = null))
        val heritage = source.getHeritageById("geunjeongjeon")!!
        assertEquals(heritage.chunks.size, result.chunks.size)
    }

    @Test
    fun `returns empty for non-existent heritage`() {
        val result = retriever.retrieve(RetrievalQuery(heritageId = "nonexistent", query = "anything"))
        assertTrue(result.chunks.isEmpty())
    }

    @Test
    fun `chunkId direct match scores highest`() {
        val heritage = source.getHeritageById("geunjeongjeon")!!
        val targetChunkId = heritage.chunks.first().chunkId
        val result = retriever.retrieve(RetrievalQuery(heritageId = "geunjeongjeon", query = targetChunkId))

        assertFalse(result.chunks.isEmpty())
        assertEquals("Direct chunkId match should return that chunk first",
            targetChunkId, result.chunks.first().chunk.chunkId)
        assertTrue("Score should be >= 100 for chunkId match",
            result.chunks.first().score >= 100f)
    }

    @Test
    fun `historical keyword returns history chunk first`() {
        // "역사" 키워드 또는 관련 어간이 들어가면 history 청크가 상위에 와야 함
        val result = retriever.retrieve(
            RetrievalQuery(heritageId = "geunjeongjeon", query = "근정전의 역사는 어떻게 되나요?")
        )
        assertFalse(result.chunks.isEmpty())
        // 결과 상위에 역사 섹션 청크가 하나는 있어야 함
        val topSections = result.chunks.take(2).map { it.chunk.section }
        assertTrue("Top results should contain 역사 section, got $topSections",
            topSections.contains("역사"))
    }

    @Test
    fun `architectural keyword returns architecture chunk`() {
        val result = retriever.retrieve(
            RetrievalQuery(heritageId = "geunjeongjeon", query = "건축 양식이 어떤가요?")
        )
        assertFalse(result.chunks.isEmpty())
        val topSections = result.chunks.take(2).map { it.chunk.section }
        assertTrue("Should find 건축 section for 건축 query, got $topSections",
            topSections.any { it == "건축" })
    }

    @Test
    fun `retrieve respects maxResults`() {
        val result = retriever.retrieve(
            RetrievalQuery(heritageId = "geunjeongjeon", query = "근정전", maxResults = 2)
        )
        assertTrue("Should respect maxResults=2", result.chunks.size <= 2)
    }

    @Test
    fun `fallback returns all chunks when no match`() {
        // 의도적으로 매칭 안되는 질의
        val result = retriever.retrieve(
            RetrievalQuery(heritageId = "geunjeongjeon", query = "xyz12345abcdefg")
        )
        // fallback 전략: no-match인 경우 전체 청크 반환
        assertFalse("Fallback should return chunks, not empty", result.chunks.isEmpty())
    }

    @Test
    fun `new p1 heritage sajeongjeon retrieval works`() {
        val result = retriever.retrieve(
            RetrievalQuery(heritageId = "sajeongjeon", query = "세종대왕과 경연")
        )
        assertFalse("Sajeongjeon retrieval should not be empty", result.chunks.isEmpty())
    }

    @Test
    fun `new p1 heritage gyotaejeon retrieval works`() {
        val result = retriever.retrieve(
            RetrievalQuery(heritageId = "gyotaejeon", query = "아미산 굴뚝")
        )
        assertFalse("Gyotaejeon retrieval should not be empty", result.chunks.isEmpty())
    }
}
