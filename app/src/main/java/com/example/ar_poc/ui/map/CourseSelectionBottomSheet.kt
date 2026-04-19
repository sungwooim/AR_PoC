package com.example.ar_poc.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.Strings
import com.example.ar_poc.domain.model.TourCourse

/**
 * 관람 코스 3개 중 하나를 선택하는 Bottom Sheet.
 *
 * 각 코스 카드는 이름, 설명, 방문지 수, 대략 거리를 표시.
 * 클릭 시 [onSelect]로 `courseId` 전달.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseSelectionBottomSheet(
    courses: List<TourCourse>,
    targetLanguage: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = Strings.getCourseSelectTitle(targetLanguage),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD280),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            courses.forEach { course ->
                CourseCard(
                    course = course,
                    targetLanguage = targetLanguage,
                    onClick = { onSelect(course.id) }
                )
            }
        }
    }
}

@Composable
private fun CourseCard(
    course: TourCourse,
    targetLanguage: String,
    onClick: () -> Unit
) {
    val emoji = when (course.durationMinutes) {
        40 -> "⚡"
        60 -> "🌿"
        90 -> "🏯"
        else -> "🗺️"
    }
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A1F00)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 32.sp, modifier = Modifier.padding(end = 12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = course.localizedName(targetLanguage),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD280)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = course.localizedDescription(targetLanguage),
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f),
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Text(
                        text = Strings.getCourseStopsLabel(targetLanguage, course.stops.size),
                        fontSize = 11.sp,
                        color = Color(0xFFFFD280).copy(alpha = 0.8f),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = " · ",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = Strings.getCourseDistanceLabel(targetLanguage, course.approxDistanceM),
                        fontSize = 11.sp,
                        color = Color(0xFFFFD280).copy(alpha = 0.8f),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
