package com.example.ar_poc.data.location

import android.location.Location
import com.example.ar_poc.Config
import com.example.ar_poc.di.DevModeManager
import com.example.ar_poc.domain.location.LocationProvider
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.PoiType
import com.example.ar_poc.domain.repository.HeritageRepository
import com.example.ar_poc.domain.spatial.SpatialCalculator
import com.example.ar_poc.domain.spatial.SpatialContext
import com.example.ar_poc.domain.spatial.SpatialContextProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * GPS 기반 SpatialContext 발행 구현체.
 *
 * - GPS 위치를 [POLL_INTERVAL_MS]ms 주기로 폴링하여 [SpatialContext]를 발행한다.
 * - DevMode 활성 시 [Config.MOCK_LATITUDE]/[Config.MOCK_LONGITUDE]를 사용한다.
 * - POI 목록은 [HeritageRepository.getAllPois()]를 사용하며,
 *   [HeritageContent.toPoi()]를 통해 유산을 Poi로 변환한 통합 목록이다.
 *
 * **확장 전략**: Indoor Provider으로 교체 시 이 클래스 대신 [IndoorSpatialProvider]를
 * Hilt @Provides에 바인딩하거나, [isIndoor] 감지 후 이 클래스 내부에서 위임한다.
 */
class OutdoorSpatialProvider @Inject constructor(
    private val locationProvider: LocationProvider,
    private val repository: HeritageRepository,
    private val devModeManager: DevModeManager
) : SpatialContextProvider {

    companion object {
        private const val POLL_INTERVAL_MS = 5000L

        // 경복궁 사이트 경계 (Google Maps 실측)
        private const val SITE_ID = "gyeongbokgung"
        private const val LAT_MIN = 37.5765
        private const val LAT_MAX = 37.5836
        private const val LNG_MIN = 126.9742
        private const val LNG_MAX = 126.9795

        // 'currentPoi' 판정 반경 (미터)
        private const val CURRENT_POI_RADIUS_M = 20f

        // nearbyPois 최대 수
        private const val MAX_NEARBY = 10
    }

    override fun getSpatialContext(): Flow<SpatialContext> = flow {
        val allPois = repository.getAllPois()

        while (true) {
            val context = buildContext(allPois)
            emit(context)
            delay(POLL_INTERVAL_MS)
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // Build SpatialContext from current GPS fix
    // ─────────────────────────────────────────────────────────────────

    private suspend fun buildContext(allPois: List<Poi>): SpatialContext {
        val location = if (devModeManager.isDevMode) {
            // Mock Location 생성 시 즉시 좌표가 유효하도록 초기화 순서 보정
            android.location.Location("Mock").apply {
                latitude = Config.MOCK_LATITUDE
                longitude = Config.MOCK_LONGITUDE
                accuracy = 5f
                time = System.currentTimeMillis()
            }
        } else {
            locationProvider.getCurrentLocation()
        }

        if (location == null) return SpatialContext.EMPTY

        val lat = location.latitude
        val lng = location.longitude

        // 사이트 진입 여부
        val insideSite = lat in LAT_MIN..LAT_MAX && lng in LNG_MIN..LNG_MAX
        val siteId = if (insideSite) SITE_ID else null

        // 각 POI까지 거리 계산 → 가까운 순 정렬 (SpatialCalculator 사용)
        val poisWithDist = allPois
            .filter { it.latitude != 0.0 && it.longitude != 0.0 }
            .map { poi -> Pair(poi, SpatialCalculator.calcDistanceM(lat, lng, poi.latitude, poi.longitude)) }
            .sortedBy { it.second }

        // currentPoi: 반경 내에서 가장 가까운 POI
        val currentPoi = poisWithDist
            .firstOrNull { it.second <= CURRENT_POI_RADIUS_M }
            ?.first

        // zoneId: currentPoi의 linkedHeritage.zone 또는 HERITAGE POI의 zone
        val zoneId = currentPoi?.linkedHeritage?.zone
            ?: poisWithDist.firstOrNull { it.first.type == PoiType.HERITAGE && it.second <= 50f }
                ?.first?.linkedHeritage?.zone

        // nearbyPois: 거리 오름차순, 최대 10개
        val nearbyPois = poisWithDist.take(MAX_NEARBY).map { it.first }

        return SpatialContext(
            siteId = siteId,
            zoneId = zoneId,
            currentPoi = currentPoi,
            nearbyPois = nearbyPois,
            isIndoor = false,
            userLat = lat,
            userLng = lng,
            accuracyM = location.accuracy
        )
    }
}
