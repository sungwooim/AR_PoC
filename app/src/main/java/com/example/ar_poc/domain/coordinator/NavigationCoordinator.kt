package com.example.ar_poc.domain.coordinator

import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.spatial.SpatialCalculator
import com.example.ar_poc.domain.spatial.SpatialContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 추천 경로 수: 방문 동선 최대 길이.
 * 3개면 충분히 "다음 코스" 제시 가능하고, 더 길면 UX에 부담.
 */
private const val MAX_COURSE_STOPS = 3

/**
 * 다음 방문지 추천 및 방향 힌트 생성을 담당하는 코디네이터.
 *
 * ARViewModel에서 분리된 책임:
 * - nextPoiIds: 미발견 POI 중 가까운 3개 추천
 * - navigationHint: "북쪽 방향 120m → 경회루" 같은 방향 힌트
 *
 * ViewModel의 viewModelScope가 아닌 외부 scope를 받아
 * Flow가 scope 종료 시 자동 취소되도록 한다.
 */
@Singleton
class NavigationCoordinator @Inject constructor() {

    /**
     * 주어진 spatialContext와 발견 목록을 조합하여 nextPoiIds + navigationHint를 생성한다.
     *
     * @param spatialContext GPS 기반 공간 상태 Flow
     * @param discoveredHeritages 이미 발견한 유산 ID Set Flow
     * @param languageProvider 현재 언어 코드 제공자 (람다로 최신값 참조)
     * @param scope Flow를 stateIn할 CoroutineScope (보통 viewModelScope)
     */
    fun bind(
        spatialContext: StateFlow<SpatialContext>,
        discoveredHeritages: StateFlow<Set<String>>,
        languageProvider: () -> String,
        scope: CoroutineScope
    ): NavigationState {
        val nextPoiIds = combine(spatialContext, discoveredHeritages) { ctx, discovered ->
            val candidates = ctx.nearbyPois.filter { poi -> poi.id !in discovered }
            if (!ctx.hasValidLocation) {
                candidates.take(MAX_COURSE_STOPS).map { it.id }
            } else {
                nearestNeighborRoute(
                    startLat = ctx.userLat,
                    startLng = ctx.userLng,
                    candidates = candidates,
                    maxStops = MAX_COURSE_STOPS
                ).map { it.id }
            }
        }.stateIn(scope, SharingStarted.Eagerly, emptyList())

        val navigationHint = combine(spatialContext, nextPoiIds) { ctx, ids ->
            val nextId = ids.firstOrNull() ?: return@combine null
            val nextPoi = ctx.nearbyPois.find { it.id == nextId } ?: return@combine null
            if (!ctx.hasValidLocation) return@combine null

            val bearing = SpatialCalculator.calcBearing(
                ctx.userLat, ctx.userLng,
                nextPoi.latitude, nextPoi.longitude
            )
            val lang = languageProvider()
            val cardinal = SpatialCalculator.azimuthToCardinal(bearing, lang)
            val distM = SpatialCalculator.calcDistanceM(
                ctx.userLat, ctx.userLng,
                nextPoi.latitude, nextPoi.longitude
            ).toInt()
            val title = nextPoi.localizedTitle(lang)

            if (lang == "ko") "$cardinal 방향 ${distM}m → $title"
            else "$cardinal · ${distM}m → $title"
        }.stateIn(scope, SharingStarted.Eagerly, null)

        return NavigationState(nextPoiIds, navigationHint)
    }

    /**
     * Nearest-neighbor TSP 근사로 방문 순서를 최적화한다.
     *
     * 알고리즘:
     *  1. 사용자 현위치에서 가장 가까운 미발견 POI를 첫 방문지로 선택
     *  2. 그 POI에서 가장 가까운 아직 미선택 POI를 다음 방문지로 선택
     *  3. maxStops 개수까지 반복
     *
     * 단순 "가장 가까운 N개" 추천 대비 장점:
     *  - 경로 총 길이가 짧아짐 (동선 효율)
     *  - 사용자가 반대 방향을 왔다갔다하지 않음
     *  - 근접한 전각들(예: 강녕전-교태전)을 묶어서 추천
     *
     * O(maxStops × N) 시간복잡도로, candidates 수가 작아 실용적.
     */
    private fun nearestNeighborRoute(
        startLat: Double,
        startLng: Double,
        candidates: List<Poi>,
        maxStops: Int
    ): List<Poi> {
        if (candidates.isEmpty()) return emptyList()
        val route = mutableListOf<Poi>()
        val remaining = candidates.toMutableList()
        var curLat = startLat
        var curLng = startLng

        repeat(minOf(maxStops, candidates.size)) {
            val nearest = remaining.minByOrNull { poi ->
                SpatialCalculator.calcDistanceM(curLat, curLng, poi.latitude, poi.longitude)
            } ?: return@repeat
            route.add(nearest)
            remaining.remove(nearest)
            curLat = nearest.latitude
            curLng = nearest.longitude
        }
        return route
    }
}

/** NavigationCoordinator.bind()의 결과. ViewModel이 UI에 노출할 StateFlow 묶음. */
data class NavigationState(
    val nextPoiIds: StateFlow<List<String>>,
    val navigationHint: StateFlow<String?>
)
