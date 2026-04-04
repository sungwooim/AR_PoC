package com.example.ar_poc.domain.spatial

import com.example.ar_poc.domain.model.Poi

/**
 * XR 엔진의 핵심 공간 상태(Spatial State) 스냅샷.
 *
 * UI 레이어는 이 단일 모델만 구독하여 지도/AR 오버레이/내비게이션을 렌더링한다.
 * GPS 기반(Outdoor)과 실내 측위(Indoor) 모두 동일한 구조로 발행하므로
 * Provider를 교체해도 UI 코드를 수정할 필요가 없다.
 *
 * @param siteId      현재 방문 사이트 ID (e.g. "gyeongbokgung"). 사이트 밖이면 null.
 * @param zoneId      사이트 내 세부 구역 ID (e.g. "main_court"). 미분류면 null.
 * @param currentPoi  사용자 위치에 가장 가까운 POI (반경 20m 이내). 없으면 null.
 * @param nearbyPois  현재 방향/위치 기준 주변 POI 목록 (거리 오름차순, 최대 10개).
 * @param isIndoor    실내 측위 활성 여부. true면 BLE/UWB 기반, false면 GPS 기반.
 * @param userLat     현재 위도 (GPS 또는 Indoor 변환 좌표).
 * @param userLng     현재 경도.
 * @param accuracyM   위치 정확도(미터). Indoor 시 예상 정확도 사용.
 */
data class SpatialContext(
    val siteId: String?,
    val zoneId: String?,
    val currentPoi: Poi?,
    val nearbyPois: List<Poi>,
    val isIndoor: Boolean,
    val userLat: Double,
    val userLng: Double,
    val accuracyM: Float
) {
    companion object {
        /** 사이트 밖/GPS 미획득 초기값 */
        val EMPTY = SpatialContext(
            siteId = null,
            zoneId = null,
            currentPoi = null,
            nearbyPois = emptyList(),
            isIndoor = false,
            userLat = 0.0,
            userLng = 0.0,
            accuracyM = Float.MAX_VALUE
        )
    }

    /** 유효한 위치 정보를 보유하고 있는지 여부 (0,0 근처의 잘못된 값 필터링) */
    val hasValidLocation: Boolean
        get() = Math.abs(userLat) > 0.0001 && Math.abs(userLng) > 0.0001

    /** 사이트 진입 여부 */
    val isInsideSite: Boolean
        get() = siteId != null
}
