package com.example.ar_poc.ui.map

import android.location.Location
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.PoiType
import com.example.ar_poc.ui.ar.PoiInfoDialog

@Composable
fun MapScreen(
    currentLocation: Location?,
    discoveredHeritages: Set<String>,
    poiList: List<Poi>,
    azimuth: Float = 0f,
    targetLanguage: String = "ko",
    onPoiClick: (String) -> Unit = {},
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gyeongbokgungCenter = LatLng(37.5796, 126.9770)

    // 방위각 부드럽게 보간
    val animatedBearing by animateFloatAsState(
        targetValue = azimuth,
        animationSpec = tween(durationMillis = 300),
        label = "map_bearing"
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder()
            .target(gyeongbokgungCenter)
            .zoom(17f)
            .bearing(azimuth)
            .build()
    }

    // 자동 추적 모드: true=내 위치 따라감, false=자유 패닝
    var isFollowing by remember { mutableStateOf(true) }

    // 일반 POI 정보 팝업 상태
    var selectedInfoPoi by remember { mutableStateOf<Poi?>(null) }

    // 사용자 제스처 감지를 위해 Compose 상태 대신 직접 터치 감지 사용
    // cameraPositionState.isMoving 은 업데이트와 충돌 시 간헐적으로 무시되므로 손가락이 닿는 즉시 전환합니다.

    // [통합 카메라 로직] 위치 갱신 + 방위각 회전 제어
    LaunchedEffect(isFollowing, currentLocation, animatedBearing) {
        val currentPos = cameraPositionState.position
        
        // 아프리카 버그 방지: 초기화 전 target이 (0,0) 일 때는 포지션 덮어쓰기 보류
        if (Math.abs(currentPos.target.latitude) < 0.1 && Math.abs(currentPos.target.longitude) < 0.1) {
            return@LaunchedEffect
        }

        // 자유 모드일 때는 코드에 의한 강제 강제 카메라 업데이트를 '전면' 중지하여 제스처 방해 원천 차단
        if (!isFollowing) {
            return@LaunchedEffect
        }

        val currentLoc = currentLocation
        if (currentLoc != null && Math.abs(currentLoc.latitude) > 0.001) {
            // 자동 추적 모드: 내 위치 중심점 + 앱 방위각
            cameraPositionState.position = CameraPosition.Builder()
                .target(LatLng(currentLoc.latitude, currentLoc.longitude))
                .zoom(currentPos.zoom.coerceIn(15f, 20f))
                .bearing(animatedBearing)
                .tilt(0f)
                .build()
        } else {
            // 위치가 아직 없을 때: 기존 중심점 유지 + 앱 방위각
            cameraPositionState.position = CameraPosition.Builder()
                .target(currentPos.target)
                .zoom(currentPos.zoom)
                .bearing(animatedBearing)
                .tilt(currentPos.tilt)
                .build()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            // 지도를 터치(Press)하는 순간 즉시 "자유 패닝 모드" 로 변경하여 카메라 강제 갱신 차단
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        if (event.changes.any { it.pressed }) {
                            isFollowing = false
                        }
                    }
                }
            }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = currentLocation != null,
                mapType = MapType.NORMAL
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,  // 커스텀 버튼으로 교체
                compassEnabled = false,
                zoomControlsEnabled = false
            )
        ) {
            // ── 내 위치 마커 ───────────────────────────────────────────────
            currentLocation?.let { loc ->
                MarkerComposable(
                    keys = arrayOf("my_location", loc.latitude, loc.longitude),
                    state = MarkerState(position = LatLng(loc.latitude, loc.longitude)),
                    zIndex = 10f
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0x331565C0), androidx.compose.foundation.shape.CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(Modifier.size(12.dp).background(Color(0xFF1565C0), androidx.compose.foundation.shape.CircleShape))
                        }
                        Text(
                            text = when (targetLanguage) { "en" -> "Me"; "ja" -> "現在地"; "zh" -> "我"; else -> "내 위치" },
                            fontSize = 9.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.Bold,
                            modifier = Modifier.background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(3.dp)).padding(horizontal = 3.dp)
                        )
                    }
                }
            }

            // ── POI 마커 ─────────────────────────────────────────────────
            poiList.forEach { poi ->
                if (poi.latitude != 0.0 && poi.longitude != 0.0) {
                    val isHeritage = poi.linkedHeritage != null
                    val isDiscovered = discoveredHeritages.contains(poi.id)
                    val position = LatLng(poi.latitude, poi.longitude)
                    val label = poi.localizedTitle(targetLanguage)

                    MarkerComposable(
                        keys = arrayOf(poi.id, isDiscovered, targetLanguage),
                        state = MarkerState(position = position),
                        onClick = {
                            if (poi.linkedHeritage != null) {
                                onPoiClick(poi.linkedHeritage.id)
                            } else {
                                selectedInfoPoi = poi
                            }
                            true
                        }
                    ) {
                        PoiMapLabel(
                            poi = poi,
                            label = label,
                            isHeritage = isHeritage,
                            isDiscovered = isDiscovered
                        )
                    }
                }
            }
        }

        // Header
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .background(Color(0xF5FFFFFF), RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = when (targetLanguage) {
                            "en" -> "🗺️ AR Palace Map"
                            "ja" -> "🗺️ AR 宮殿マップ"
                            "zh" -> "🗺️ AR 宫殿地图"
                            else -> "🗺️ AR 궁궐 지도"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFF1A1A2E)
                    )
                    Spacer(Modifier.height(2.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        // 현재 방위각 방향 표시
                        val dirLabel = when {
                            azimuth < 22.5f || azimuth >= 337.5f -> when(targetLanguage) { "en" -> "↑ N"; "ja" -> "↑ 北"; "zh" -> "↑ 北"; else -> "↑ 북" }
                            azimuth < 67.5f  -> when(targetLanguage) { "en" -> "↗ NE"; "ja" -> "↗ 北東"; "zh" -> "↗ 东北"; else -> "↗ 북동" }
                            azimuth < 112.5f -> when(targetLanguage) { "en" -> "→ E"; "ja" -> "→ 東"; "zh" -> "→ 东"; else -> "→ 동" }
                            azimuth < 157.5f -> when(targetLanguage) { "en" -> "↘ SE"; "ja" -> "↘ 南東"; "zh" -> "↘ 东南"; else -> "↘ 남동" }
                            azimuth < 202.5f -> when(targetLanguage) { "en" -> "↓ S"; "ja" -> "↓ 南"; "zh" -> "↓ 南"; else -> "↓ 남" }
                            azimuth < 247.5f -> when(targetLanguage) { "en" -> "↙ SW"; "ja" -> "↙ 南西"; "zh" -> "↙ 西南"; else -> "↙ 남서" }
                            azimuth < 292.5f -> when(targetLanguage) { "en" -> "← W"; "ja" -> "← 西"; "zh" -> "← 西"; else -> "← 서" }
                            else             -> when(targetLanguage) { "en" -> "↖ NW"; "ja" -> "↖ 北西"; "zh" -> "↖ 西北"; else -> "↖ 북서" }
                        }
                        Text(dirLabel, fontSize = 11.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.Bold)
                        val heritageOnlyCount = poiList.count { it.latitude != 0.0 && it.linkedHeritage != null }
                        Text("🔴 ${when(targetLanguage){"en"->"Heritage";"ja"->"文化財";"zh"->"文物";else->"유산"}}",
                            fontSize = 11.sp, color = Color(0xFF880000))
                        Text("🔵 ${when(targetLanguage){"en"->"Facility";"ja"->"施設";"zh"->"设施";else->"시설"}}",
                            fontSize = 11.sp, color = Color(0xFF1565C0))
                        Text("${discoveredHeritages.size}/$heritageOnlyCount ✨",
                            fontSize = 11.sp, color = Color(0xFF666666))
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "닫기", tint = Color(0xFF555555))
                }
            }
        }

        // ── 내 위치로 돌아가기 FAB ────────────────────────────────────────
        FloatingActionButton(
            onClick = { isFollowing = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp)
                .size(48.dp),
            containerColor = if (isFollowing) Color(0xFF1565C0) else Color.White,
            contentColor = if (isFollowing) Color.White else Color(0xFF1565C0),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = when(targetLanguage) { "en" -> "My Location"; "ja" -> "現在地"; "zh" -> "我的位置"; else -> "내 위치" },
                modifier = Modifier.size(22.dp)
            )
        }

        // 일반 POI 정보 팝업
        selectedInfoPoi?.let { poi ->
            PoiInfoDialog(
                poi = poi,
                targetLanguage = targetLanguage,
                onDismiss = { selectedInfoPoi = null }
            )
        }
    }
}


// ─────────────────────────────────────────────────────────────────────────────
// MarkerComposable 내부에 렌더링되는 POI 레이블 뷰
// 유산(🔴 굵은 테두리)과 시설(🔵 얇은 테두리)을 명확히 구분
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PoiMapLabel(
    poi: Poi,
    label: String,
    isHeritage: Boolean,
    isDiscovered: Boolean
) {
    val bgColor: Color
    val borderColor: Color
    val textColor: Color
    val iconText: String

    when {
        isHeritage && isDiscovered -> {
            bgColor = Color(0xFFFF6B00)
            borderColor = Color(0xFFFF8C00)
            textColor = Color.White
            iconText = "✨"
        }
        isHeritage -> {
            bgColor = Color(0xFFCC0000)
            borderColor = Color(0xFFFF3300)
            textColor = Color.White
            iconText = poi.type.icon
        }
        else -> {
            bgColor = Color(0xFF1565C0)
            borderColor = Color(0xFF42A5F5)
            textColor = Color.White
            iconText = poi.type.icon
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // 이름 레이블 — 항상 표시
        Box(
            modifier = Modifier
                .background(bgColor.copy(alpha = 0.92f), RoundedCornerShape(8.dp))
                .padding(horizontal = 7.dp, vertical = 3.dp)
        ) {
            Text(
                text = "$iconText $label",
                color = textColor,
                fontSize = if (isHeritage) 12.sp else 10.sp,
                fontWeight = if (isHeritage) FontWeight.Bold else FontWeight.Normal,
                fontStyle = if (!isHeritage) FontStyle.Italic else FontStyle.Normal,
                maxLines = 1
            )
        }
        // 핀 아래 삼각형
        Box(
            modifier = Modifier
                .size(width = 10.dp, height = 6.dp)
                .background(bgColor.copy(alpha = 0.92f),
                    RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
        )
    }
}
