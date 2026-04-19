package com.example.ar_poc.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.Strings

/**
 * 내비게이션 HUD (구글맵·티맵 스타일).
 *
 * 카메라/상세/스탬프 어떤 화면에 있어도 상단에 떠 있는 작은 배너.
 * - 왼쪽: 화살표 (다음 웨이포인트 방향을 폰 방향 기준 상대 각도로 회전)
 * - 가운데: 다음 웨이포인트 이름 + 남은 거리
 * - 오른쪽: ✕ 내비 종료
 *
 * 탭 → 지도 화면으로 이동 (`onOpenMap`)
 *
 * @param waypointName 다음 방문지 이름 (현지화)
 * @param distanceM    남은 거리 (미터)
 * @param relativeBearingDeg 폰 정면을 0으로 한 상대 각도 (음수=왼쪽, 양수=오른쪽)
 */
@Composable
fun NavigationHud(
    waypointName: String,
    distanceM: Int,
    relativeBearingDeg: Float,
    targetLanguage: String,
    onOpenMap: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val arrived = distanceM <= 10

    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (arrived) Color(0xFF34A853)  // Google Green (arrived)
                    else Color(0xFF1A73E8),                  // Google Blue (navigating)
                    shape = RoundedCornerShape(28.dp)
                )
                .clickable(onClick = onOpenMap)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            // 화살표 원형 배지 — 상대 베어링만큼 회전
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // ▲를 회전 — 기본 "정면(0°)"은 위쪽. 각도 음수면 왼쪽, 양수면 오른쪽.
                Text(
                    text = if (arrived) "✓" else "▲",
                    color = Color.White,
                    fontSize = if (arrived) 26.sp else 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.rotate(if (arrived) 0f else relativeBearingDeg)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (arrived) {
                        Strings.getNavHudGoalReached(targetLanguage, waypointName)
                    } else {
                        "${distanceM}m"
                    },
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                if (!arrived) {
                    Text(
                        text = waypointName,
                        color = Color.White.copy(alpha = 0.92f),
                        fontSize = 13.sp,
                        maxLines = 1
                    )
                }
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = Strings.getCloseLabel(targetLanguage),
                    tint = Color.White
                )
            }
        }
    }
}
