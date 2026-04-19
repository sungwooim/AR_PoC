package com.example.ar_poc.domain.spatial

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.abs

/**
 * SpatialCalculator 순수 함수 테스트.
 *
 * 경복궁 내부의 실제 전각 좌표를 사용해 거리·방위각이
 * 기대 범위 안에 드는지 검증한다.
 */
class SpatialCalculatorTest {

    // 경복궁 주요 좌표 (PROJECT_MAP.md 기준)
    private val geunjeongjeonLat = 37.5796
    private val geunjeongjeonLng = 126.9770
    private val gyeonghoeruLat = 37.5800
    private val gyeonghoeruLng = 126.9749
    private val gwanghwamunLat = 37.5759
    private val gwanghwamunLng = 126.9769

    @Test
    fun `calcDistanceM returns 0 for identical points`() {
        val dist = SpatialCalculator.calcDistanceM(
            geunjeongjeonLat, geunjeongjeonLng,
            geunjeongjeonLat, geunjeongjeonLng
        )
        assertEquals(0f, dist, 0.001f)
    }

    @Test
    fun `calcDistanceM between geunjeongjeon and gyeonghoeru is within expected range`() {
        // 실제 직선 거리 약 190m (경도 차 0.0021°)
        val dist = SpatialCalculator.calcDistanceM(
            geunjeongjeonLat, geunjeongjeonLng,
            gyeonghoeruLat, gyeonghoeruLng
        )
        assertTrue("Distance should be 150-250m, was $dist", dist in 150f..250f)
    }

    @Test
    fun `calcDistanceM between geunjeongjeon and gwanghwamun is within expected range`() {
        // 남북 400m 정도 (위도 차 0.0037°)
        val dist = SpatialCalculator.calcDistanceM(
            geunjeongjeonLat, geunjeongjeonLng,
            gwanghwamunLat, gwanghwamunLng
        )
        assertTrue("Distance should be 380-450m, was $dist", dist in 380f..450f)
    }

    @Test
    fun `calcBearing returns near 0 degrees for point directly north`() {
        // 근정전에서 정확히 북쪽 100m 지점 (위도만 증가)
        val bearing = SpatialCalculator.calcBearing(
            geunjeongjeonLat, geunjeongjeonLng,
            geunjeongjeonLat + 0.001, geunjeongjeonLng
        )
        assertTrue("Bearing should be near 0 or 360, was $bearing", bearing < 5f || bearing > 355f)
    }

    @Test
    fun `calcBearing returns near 90 degrees for point directly east`() {
        // 근정전에서 동쪽 (경도만 증가)
        val bearing = SpatialCalculator.calcBearing(
            geunjeongjeonLat, geunjeongjeonLng,
            geunjeongjeonLat, geunjeongjeonLng + 0.001
        )
        assertTrue("Bearing should be near 90, was $bearing", abs(bearing - 90f) < 5f)
    }

    @Test
    fun `calcBearing gyeonghoeru from geunjeongjeon is northwest-ish`() {
        // 경회루는 근정전 서북서 방향 — bearing 약 280도
        val bearing = SpatialCalculator.calcBearing(
            geunjeongjeonLat, geunjeongjeonLng,
            gyeonghoeruLat, gyeonghoeruLng
        )
        assertTrue("Bearing should be NW (250-310), was $bearing", bearing in 250f..310f)
    }

    @Test
    fun `azimuthToCardinal maps 0 to north`() {
        val result = SpatialCalculator.azimuthToCardinal(0f, "ko")
        assertTrue("Should contain 북 for 0deg Korean, got $result", result.contains("북"))
    }

    @Test
    fun `azimuthToCardinal maps 90 to east`() {
        val result = SpatialCalculator.azimuthToCardinal(90f, "en")
        assertTrue("Should contain E for 90deg English, got $result", result.contains("E"))
    }
}
