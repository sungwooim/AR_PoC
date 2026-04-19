package com.example.ar_poc.data.heritage

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 유산 데이터 무결성 검증.
 *
 * 대규모 콘텐츠 확장(17개 전각)에서 누락/오타가 있으면
 * UI에서 숨은 버그가 되므로, 핵심 필드의 일관성을 테스트한다.
 */
class LocalHeritageKnowledgeSourceTest {

    private val source = LocalHeritageKnowledgeSource()

    @Test
    fun `heritage list contains all 17 planned buildings`() {
        val list = source.getHeritageList()
        assertEquals("Should have 17 heritages after P1+P2 expansion", 17, list.size)
    }

    @Test
    fun `all core p1 heritages are present`() {
        val expectedP1 = listOf(
            "geunjeongjeon", "gyeonghoeru", "gwanghwamun",
            "sajeongjeon", "sujeongjeon", "gangnyeongjeon", "gyotaejeon",
            "jagyeongjeon", "hyangwonjeong", "geoncheongung", "jibokjae", "donggung"
        )
        val ids = source.getHeritageList().map { it.id }
        expectedP1.forEach { id ->
            assertTrue("Missing P1 heritage: $id", ids.contains(id))
        }
    }

    @Test
    fun `all p2 heritages are present`() {
        val expectedP2 = listOf(
            "taeweonjeon", "sojubang", "heungbokjeon",
            "geunjeongmun", "yeongjeogyo"
        )
        val ids = source.getHeritageList().map { it.id }
        expectedP2.forEach { id ->
            assertTrue("Missing P2 heritage: $id", ids.contains(id))
        }
    }

    @Test
    fun `every heritage has valid gps coordinates near gyeongbokgung`() {
        source.getHeritageList().forEach { heritage ->
            // 경복궁은 약 37.575~37.585, 126.974~126.980 범위
            assertTrue("${heritage.id} lat out of range: ${heritage.latitude}",
                heritage.latitude in 37.574..37.585)
            assertTrue("${heritage.id} lng out of range: ${heritage.longitude}",
                heritage.longitude in 126.973..126.980)
        }
    }

    @Test
    fun `every heritage has all 4 language titles`() {
        val requiredLangs = setOf("ko", "en", "ja", "zh")
        source.getHeritageList().forEach { heritage ->
            assertTrue("${heritage.id} missing languages in titleMap",
                heritage.titleMap.keys.containsAll(requiredLangs))
            assertTrue("${heritage.id} missing languages in shortDescMap",
                heritage.shortDescMap.keys.containsAll(requiredLangs))
        }
    }

    @Test
    fun `every chunk has all 4 language contentMap`() {
        val requiredLangs = setOf("ko", "en", "ja", "zh")
        source.getHeritageList().forEach { heritage ->
            heritage.chunks.forEach { chunk ->
                assertTrue("${heritage.id}/${chunk.chunkId} missing contentMap languages",
                    chunk.contentMap.keys.containsAll(requiredLangs))
                assertTrue("${heritage.id}/${chunk.chunkId} missing titleMap languages",
                    chunk.titleMap.keys.containsAll(requiredLangs))
            }
        }
    }

    @Test
    fun `every heritage has at least one chunk`() {
        source.getHeritageList().forEach { heritage ->
            assertFalse("${heritage.id} has no chunks", heritage.chunks.isEmpty())
        }
    }

    @Test
    fun `every heritage has at least 2 aliases`() {
        source.getHeritageList().forEach { heritage ->
            assertTrue("${heritage.id} should have >=2 aliases, has ${heritage.aliases.size}",
                heritage.aliases.size >= 2)
        }
    }

    @Test
    fun `heritage ids are unique`() {
        val ids = source.getHeritageList().map { it.id }
        assertEquals("Heritage IDs should be unique", ids.size, ids.toSet().size)
    }

    @Test
    fun `all chunk ids are unique across heritages`() {
        val allChunkIds = source.getHeritageList().flatMap { it.chunks.map { c -> c.chunkId } }
        assertEquals("Chunk IDs should be globally unique",
            allChunkIds.size, allChunkIds.toSet().size)
    }

    @Test
    fun `all sub-element linkedChunkIds are valid within their heritage`() {
        source.getHeritageList().forEach { heritage ->
            val chunkIds = heritage.chunks.map { it.chunkId }.toSet()
            heritage.subElements.forEach { sub ->
                val linked = sub.linkedChunkId
                if (linked != null) {
                    assertTrue("${heritage.id}/${sub.id} links to non-existent chunk: $linked",
                        chunkIds.contains(linked))
                }
            }
        }
    }

    @Test
    fun `getHeritageById is case-insensitive`() {
        val upper = source.getHeritageById("GEUNJEONGJEON")
        val lower = source.getHeritageById("geunjeongjeon")
        assertNotNull("Should find with uppercase", upper)
        assertNotNull("Should find with lowercase", lower)
        assertEquals(upper?.id, lower?.id)
    }

    @Test
    fun `extraPoiList is not empty`() {
        val pois = source.getExtraPoiList()
        assertTrue("Should have extra POIs (gates, cafes, etc.)", pois.size >= 6)
    }
}
