package com.example.ar_poc.data.heritage

import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.model.Poi

/**
 * Interface for heritage knowledge data sources.
 */
interface HeritageKnowledgeSource {
    fun getHeritageById(id: String): HeritageContent?
    fun getHeritageList(): List<HeritageContent>

    /**
     * 유산 외의 일반 POI(카페, 화장실, 전망 포인트 등) 목록을 반환.
     * 기본 구현은 빈 목록 (하위 호환성 유지).
     */
    fun getExtraPoiList(): List<Poi> = emptyList()
}
