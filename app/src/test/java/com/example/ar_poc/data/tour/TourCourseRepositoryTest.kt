package com.example.ar_poc.data.tour

import com.example.ar_poc.data.heritage.LocalHeritageKnowledgeSource
import com.example.ar_poc.domain.spatial.SpatialCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * TourCourseRepository 데이터 무결성 검증.
 */
class TourCourseRepositoryTest {

    private val repo = TourCourseRepository()
    private val heritageSource = LocalHeritageKnowledgeSource()

    @Test
    fun `exactly 3 courses defined (40, 60, 90 min)`() {
        val courses = repo.getCourses()
        assertEquals(3, courses.size)
        assertEquals(setOf(40, 60, 90), courses.map { it.durationMinutes }.toSet())
    }

    @Test
    fun `every course has valid id and 4-language name`() {
        val requiredLangs = setOf("ko", "en", "ja", "zh")
        repo.getCourses().forEach { course ->
            assertTrue("Empty id", course.id.isNotBlank())
            assertTrue("${course.id} missing name languages",
                course.nameMap.keys.containsAll(requiredLangs))
            assertTrue("${course.id} missing description languages",
                course.descriptionMap.keys.containsAll(requiredLangs))
        }
    }

    @Test
    fun `course stop counts match user specification`() {
        // 사용자 지정 고정 카운트:
        //   40분 = 4곳 (근정전, 수정전, 경회루, 사정전)
        //   60분 = 10곳
        //   90분 = 14곳 (향원정·건청궁은 하나의 stop으로 합쳐짐)
        assertEquals(4, repo.getCourseById("course_40")!!.stops.size)
        assertEquals(10, repo.getCourseById("course_60")!!.stops.size)
        assertEquals(14, repo.getCourseById("course_90")!!.stops.size)
    }

    @Test
    fun `90-min course visits more stops than 40-min course`() {
        val c40 = repo.getCourseById("course_40")!!
        val c90 = repo.getCourseById("course_90")!!
        assertTrue(
            "90-min course should have more stops (${c90.stops.size}) than 40-min (${c40.stops.size})",
            c90.stops.size > c40.stops.size
        )
    }

    @Test
    fun `waypoint orders are strictly increasing`() {
        repo.getCourses().forEach { course ->
            val orders = course.waypoints.map { it.order }
            assertEquals("${course.id} orders not sorted", orders.sorted(), orders)
            assertEquals("${course.id} duplicate orders", orders.size, orders.toSet().size)
        }
    }

    @Test
    fun `every stop waypoint has a heritageId or poiId`() {
        repo.getCourses().forEach { course ->
            course.stops.forEach { wp ->
                assertTrue(
                    "${course.id} waypoint ${wp.order} has neither heritageId nor poiId",
                    wp.heritageId != null || wp.poiId != null
                )
            }
        }
    }

    @Test
    fun `all heritage references exist in LocalHeritageKnowledgeSource`() {
        val validIds = heritageSource.getHeritageList().map { it.id }.toSet()
        repo.getCourses().forEach { course ->
            course.stops.mapNotNull { it.heritageId }.forEach { hid ->
                assertTrue(
                    "${course.id} references missing heritage: $hid",
                    validIds.contains(hid)
                )
            }
        }
    }

    @Test
    fun `waypoints are within gyeongbokgung bounds`() {
        // 경복궁 대략 범위: 37.574 ~ 37.584, 126.973 ~ 126.980
        repo.getCourses().forEach { course ->
            course.waypoints.forEach { wp ->
                assertTrue(
                    "${course.id} wp ${wp.order} lat out of range: ${wp.latitude}",
                    wp.latitude in 37.574..37.585
                )
                assertTrue(
                    "${course.id} wp ${wp.order} lng out of range: ${wp.longitude}",
                    wp.longitude in 126.973..126.980
                )
            }
        }
    }

    @Test
    fun `approxDistance is within reasonable bounds for each course`() {
        // 경복궁 내부는 400m × 500m 규모. 코스별 사용자 지정 stop 수와 동선에 맞춰:
        //   40분(4곳): 300~800m
        //   60분(10곳, 동궁까지): 800~1800m
        //   90분(14곳, 후원 포함): 1500~3500m
        assertTrue(repo.getCourseById("course_40")!!.approxDistanceM in 300..800)
        assertTrue(repo.getCourseById("course_60")!!.approxDistanceM in 800..1800)
        assertTrue(repo.getCourseById("course_90")!!.approxDistanceM in 1500..3500)
    }

    @Test
    fun `cumulative polyline distance approximates approxDistance`() {
        // 웨이포인트 연결 거리가 선언된 approxDistanceM과 대략 일치해야 함 (±50%)
        repo.getCourses().forEach { course ->
            val path = course.waypoints.sortedBy { it.order }
            var total = 0f
            for (i in 0 until path.size - 1) {
                total += SpatialCalculator.calcDistanceM(
                    path[i].latitude, path[i].longitude,
                    path[i + 1].latitude, path[i + 1].longitude
                )
            }
            val declared = course.approxDistanceM
            val ratio = total / declared
            assertTrue(
                "${course.id} declared=${declared}m, computed=${total.toInt()}m (ratio=$ratio)",
                ratio in 0.5f..1.8f
            )
        }
    }
}
