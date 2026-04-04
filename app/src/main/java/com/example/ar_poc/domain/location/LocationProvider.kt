package com.example.ar_poc.domain.location

import android.location.Location

/**
 * 플랫폼 독립적인 위치 정보를 제공하는 인터페이스.
 */
interface LocationProvider {
    /** 현재 위치 획득 (Cached 또는 Fresh) */
    suspend fun getCurrentLocation(): Location?
    
    /** 좌표 기반으로 유적지(경복궁 등) 이름을 반환. 아니면 null. */
    fun getPalaceName(location: Location): String?
}
