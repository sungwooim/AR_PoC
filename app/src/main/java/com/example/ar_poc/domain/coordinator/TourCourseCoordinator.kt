package com.example.ar_poc.domain.coordinator

import com.example.ar_poc.data.tour.TourCourseRepository
import com.example.ar_poc.domain.model.CourseWaypoint
import com.example.ar_poc.domain.model.TourCourse
import com.example.ar_poc.domain.spatial.SpatialCalculator
import com.example.ar_poc.domain.spatial.SpatialContext
import com.example.ar_poc.domain.spatial.SpatialContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 관람 코스 선택·진행·자동 체크를 담당하는 코디네이터.
 *
 * ## 역할
 * - 현재 선택된 [TourCourse] 상태 관리
 * - 사용자 GPS와 각 stop waypoint 거리 비교 → [AUTO_CHECK_RADIUS_M] 이내면 자동으로 방문 기록
 * - 다음 방문지 인덱스 계산 (아직 방문 안 한 가장 낮은 order)
 * - 방문 진행률 및 완료 여부 노출
 *
 * ## 자동 체크 임계
 * GPS 정확도 고려하여 `AUTO_CHECK_RADIUS_M = 10f` (미터).
 * 너무 타이트하면 놓치고, 너무 느슨하면 오탐. 10m는 경복궁 현장 테스트 기준.
 */
@Singleton
class TourCourseCoordinator @Inject constructor(
    private val repository: TourCourseRepository
) {

    private val _selectedCourse = MutableStateFlow<TourCourse?>(null)
    val selectedCourse: StateFlow<TourCourse?> = _selectedCourse.asStateFlow()

    /** 방문 완료한 waypoint의 order 집합 (선택된 코스 내부에서만 유효) */
    private val _visitedOrders = MutableStateFlow<Set<Int>>(emptySet())
    val visitedOrders: StateFlow<Set<Int>> = _visitedOrders.asStateFlow()

    /**
     * 내비게이션 활성 상태.
     *
     * - `false` (기본): 코스 **프리뷰** 모드 — 지도에 전체 경로가 표시되지만 따라다니지 않음
     * - `true`: 내비게이션 **진행** 모드 — 지도 카메라가 사용자 위치를 따라가고 HUD가 다른 화면에도 나타남
     */
    private val _isNavigating = MutableStateFlow(false)
    val isNavigating: StateFlow<Boolean> = _isNavigating.asStateFlow()

    /** 선택 가능한 모든 코스 */
    fun getAllCourses(): List<TourCourse> = repository.getCourses()

    /** 코스 선택. 기존 진행 상태는 초기화된다. */
    fun selectCourse(courseId: String) {
        val course = repository.getCourseById(courseId) ?: return
        _selectedCourse.value = course
        _visitedOrders.value = emptySet()
    }

    /** 선택 해제 → 지도에서 코스가 사라지고 진행 상태 초기화. */
    fun clearSelection() {
        _selectedCourse.value = null
        _visitedOrders.value = emptySet()
        _isNavigating.value = false
    }

    /** 내비게이션 시작. 선택된 코스가 있어야만 활성화. */
    fun startNavigation() {
        if (_selectedCourse.value != null) _isNavigating.value = true
    }

    /** 내비게이션만 중지 — 코스는 유지되어 다시 시작 가능. */
    fun stopNavigation() {
        _isNavigating.value = false
    }

    /**
     * GPS 업데이트 시 호출. [AUTO_CHECK_RADIUS_M] 이내 stop waypoint를 방문 처리.
     *
     * 이미 방문한 곳은 건너뛰며, 여러 개가 동시에 반경 안에 들어와도 모두 방문 처리.
     */
    fun onLocationUpdate(lat: Double, lng: Double) {
        val course = _selectedCourse.value ?: return
        val current = _visitedOrders.value
        val newlyVisited = course.stops
            .filter { it.order !in current }
            .filter {
                SpatialCalculator.calcDistanceM(lat, lng, it.latitude, it.longitude) <= AUTO_CHECK_RADIUS_M
            }
            .map { it.order }
            .toSet()
        if (newlyVisited.isNotEmpty()) {
            _visitedOrders.value = current + newlyVisited
        }
    }

    /** 수동으로 방문 체크/해제 (UI에서 길게 누르기 등). */
    fun toggleVisited(order: Int) {
        val current = _visitedOrders.value
        _visitedOrders.value = if (order in current) current - order else current + order
    }

    /**
     * 현재 사용자 위치와 [selectedCourse]를 조합한 진행 상태 Flow를 만든다.
     *
     * @param spatialProvider GPS/실내 측위 공급자
     * @param scope ViewModel scope
     */
    fun bindProgress(
        spatialProvider: SpatialContextProvider,
        scope: CoroutineScope
    ): TourProgressState {
        // GPS 업데이트마다 자동 체크
        val spatialContext = spatialProvider.getSpatialContext()

        val nextWaypoint: StateFlow<CourseWaypoint?> =
            combine(_selectedCourse, _visitedOrders, spatialContext) { course, visited, ctx ->
                if (course == null) return@combine null
                // GPS 기반 자동 체크 트리거 (side effect)
                if (ctx.hasValidLocation) {
                    onLocationUpdate(ctx.userLat, ctx.userLng)
                }
                course.stops.firstOrNull { it.order !in visited }
            }.stateIn(scope, SharingStarted.Eagerly, null)

        val progressPercent: StateFlow<Int> =
            combine(_selectedCourse, _visitedOrders) { course, visited ->
                if (course == null || course.stops.isEmpty()) 0
                else (visited.size * 100 / course.stops.size)
            }.stateIn(scope, SharingStarted.Eagerly, 0)

        val isCompleted: StateFlow<Boolean> =
            combine(_selectedCourse, _visitedOrders) { course, visited ->
                course != null &&
                    course.stops.isNotEmpty() &&
                    course.stops.all { it.order in visited }
            }.stateIn(scope, SharingStarted.Eagerly, false)

        return TourProgressState(nextWaypoint, progressPercent, isCompleted)
    }

    companion object {
        /** 자동 방문 체크 반경 (미터). GPS 정확도 ±5m + 사용자 여유 5m = 10m. */
        const val AUTO_CHECK_RADIUS_M = 10f
    }
}

/** ViewModel이 UI에 노출할 진행 상태 묶음. */
data class TourProgressState(
    val nextWaypoint: StateFlow<CourseWaypoint?>,
    val progressPercent: StateFlow<Int>,
    val isCompleted: StateFlow<Boolean>
)
