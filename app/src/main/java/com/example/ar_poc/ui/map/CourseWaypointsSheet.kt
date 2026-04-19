package com.example.ar_poc.ui.map

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.Strings
import com.example.ar_poc.domain.model.CourseWaypoint
import com.example.ar_poc.domain.model.TourCourse
import com.example.ar_poc.domain.spatial.SpatialCalculator

/**
 * 선택된 코스의 웨이포인트 목록 + 현재 진행 상태를 표시하는 Bottom Sheet.
 *
 * - 진행 바 (방문/전체)
 * - 각 stop의 상태 뱃지 (✓ 방문 완료 / 📍 현재 지점 / 거리 미터)
 * - stop 탭 → linkedChunkId 없이 heritage DetailScreen으로 이동
 * - "코스 종료" 버튼 → onStopCourse
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseWaypointsSheet(
    course: TourCourse,
    visitedOrders: Set<Int>,
    nextWaypointOrder: Int?,
    currentLocation: Location?,
    isNavigating: Boolean,
    targetLanguage: String,
    onNavigateToDetail: (heritageId: String, chunkId: String?) -> Unit,
    onStartNavigation: () -> Unit,
    onStopNavigation: () -> Unit,
    onStopCourse: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val stops = course.stops
    val progressFraction = if (stops.isEmpty()) 0f
        else visitedOrders.count { order -> stops.any { it.order == order } }.toFloat() / stops.size
    val isComplete = stops.isNotEmpty() && stops.all { it.order in visitedOrders }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color(0xFF2D1500)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = course.localizedName(targetLanguage),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD280)
                    )
                    Text(
                        text = Strings.getCourseProgressLabel(
                            targetLanguage,
                            visitedOrders.count { o -> stops.any { it.order == o } },
                            stops.size
                        ),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
                TextButton(onClick = onStopCourse) {
                    Text(
                        text = Strings.getCourseStopButtonLabel(targetLanguage),
                        color = Color(0xFFFF6B6B),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progressFraction },
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFFD280),
                trackColor = Color(0xFF4A2A00)
            )

            if (isComplete) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = Strings.getCourseAllDoneLabel(targetLanguage),
                    fontSize = 14.sp,
                    color = Color(0xFF90EE90),
                    fontWeight = FontWeight.Bold
                )
            }

            // 안내 시작/중지 버튼 — 구글맵 파란색
            if (!isComplete) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (isNavigating) onStopNavigation() else {
                            onStartNavigation()
                            onDismiss()   // 내비 시작 시 시트를 닫아 지도 시야 확보
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isNavigating) Color(0xFFEA4335)   // Google Red (stop)
                        else Color(0xFF4285F4)                                  // Google Blue (start)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = if (isNavigating)
                            Strings.getStopNavigationLabel(targetLanguage)
                        else
                            Strings.getStartNavigationLabel(targetLanguage),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(stops) { wp ->
                    WaypointRow(
                        waypoint = wp,
                        visited = wp.order in visitedOrders,
                        isNext = wp.order == nextWaypointOrder,
                        currentLocation = currentLocation,
                        targetLanguage = targetLanguage,
                        onClick = {
                            val hid = wp.heritageId
                            if (hid != null) onNavigateToDetail(hid, null)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun WaypointRow(
    waypoint: CourseWaypoint,
    visited: Boolean,
    isNext: Boolean,
    currentLocation: Location?,
    targetLanguage: String,
    onClick: () -> Unit
) {
    val badgeColor = when {
        visited -> Color(0xFF2E7D32)
        isNext -> Color(0xFFD32F2F)
        else -> Color(0xFFB8860B)
    }
    val backgroundColor = if (isNext) Color.Black.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.25f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier.size(28.dp).background(badgeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (visited) {
                Text("✓", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            } else {
                Text("${waypoint.order}", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = waypoint.localizedName(targetLanguage),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            val statusText = when {
                visited -> Strings.getCourseCompletedLabel(targetLanguage)
                isNext -> Strings.getCourseCurrentLabel(targetLanguage)
                currentLocation != null -> {
                    val dist = SpatialCalculator.calcDistanceM(
                        currentLocation.latitude, currentLocation.longitude,
                        waypoint.latitude, waypoint.longitude
                    ).toInt()
                    Strings.getCoursePendingLabel(targetLanguage, dist)
                }
                else -> ""
            }
            if (statusText.isNotEmpty()) {
                Text(
                    text = statusText,
                    fontSize = 11.sp,
                    color = when {
                        visited -> Color(0xFF90EE90)
                        isNext -> Color(0xFFFF8C8C)
                        else -> Color.White.copy(alpha = 0.6f)
                    }
                )
            }
        }
        if (waypoint.heritageId != null) {
            TextButton(onClick = onClick, contentPadding = PaddingValues(horizontal = 8.dp)) {
                Text(
                    text = "📖",
                    fontSize = 16.sp
                )
            }
        }
    }
}
