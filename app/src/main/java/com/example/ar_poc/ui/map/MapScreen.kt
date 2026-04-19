package com.example.ar_poc.ui.map

import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.example.ar_poc.Strings
import com.example.ar_poc.domain.model.CourseWaypoint
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.PoiType
import com.example.ar_poc.domain.model.TourCourse
import com.example.ar_poc.domain.spatial.SpatialCalculator
import com.example.ar_poc.ui.ar.PoiInfoDialog

@Composable
fun MapScreen(
    currentLocation: Location?,
    discoveredHeritages: Set<String>,
    poiList: List<Poi>,
    azimuth: Float = 0f,
    targetLanguage: String = "ko",
    tourCourses: List<TourCourse> = emptyList(),
    selectedCourse: TourCourse? = null,
    visitedOrders: Set<Int> = emptySet(),
    nextWaypointOrder: Int? = null,
    isNavigating: Boolean = false,
    onSelectCourse: (String) -> Unit = {},
    onClearCourse: () -> Unit = {},
    onToggleWaypointVisited: (Int) -> Unit = {},
    onStartNavigation: () -> Unit = {},
    onStopNavigation: () -> Unit = {},
    onPoiClick: (String) -> Unit = {},
    onNavigateToDetail: (heritageId: String, chunkId: String?) -> Unit = { _, _ -> },
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isOnline = remember { isNetworkAvailable(context) }

    // 오프라인 → 정적 fallback 지도
    if (!isOnline) {
        OfflineMapFallback(
            currentLocation = currentLocation,
            poiList = poiList,
            discoveredHeritages = discoveredHeritages,
            targetLanguage = targetLanguage,
            onClose = onClose,
        )
        return
    }

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
    LaunchedEffect(isFollowing, currentLocation, animatedBearing, isNavigating) {
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
            // 내비 모드: 줌 업(18.5f) + 60도 틸트(구글맵 내비 스타일)
            // 일반 모드: 기존 탑다운 뷰
            val targetZoom = if (isNavigating) 18.5f else currentPos.zoom.coerceIn(15f, 20f)
            val targetTilt = if (isNavigating) 55f else 0f
            cameraPositionState.position = CameraPosition.Builder()
                .target(LatLng(currentLoc.latitude, currentLoc.longitude))
                .zoom(targetZoom)
                .bearing(animatedBearing)
                .tilt(targetTilt)
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
            // ── 내 위치 마커 (구글맵 스타일 펄스 애니메이션) ────────────────
            currentLocation?.let { loc ->
                MarkerComposable(
                    keys = arrayOf("my_location", loc.latitude, loc.longitude),
                    state = MarkerState(position = LatLng(loc.latitude, loc.longitude)),
                    anchor = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
                    zIndex = 10f
                ) {
                    PulsingLocationMarker()
                }
            }

            // ── POI 마커 (코스 선택 시 필터링) ─────────────────────────────
            // 코스 선택 시:
            //  - 코스 경로상 정지점은 CourseWaypointMarker로 번호 배지 렌더링됨 → 중복 방지
            //  - 카페/안내/기념품점/포토스팟 같은 편의 시설만 추가로 노출
            // 코스 미선택 시: 기존대로 모든 POI 노출
            val courseStopIds: Set<String> = selectedCourse
                ?.stops
                ?.mapNotNull { it.targetId }
                ?.toSet()
                .orEmpty()
            val facilityTypes = setOf(
                PoiType.CAFE, PoiType.SHOP, PoiType.INFO, PoiType.VIEWPOINT
            )
            val visiblePois = if (selectedCourse != null) {
                poiList.filter { poi ->
                    poi.type in facilityTypes && poi.id !in courseStopIds
                }
            } else {
                poiList
            }

            visiblePois.forEach { poi ->
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

            // ── 코스 Polyline + 번호 마커 ─────────────────────────────────
            selectedCourse?.let { course ->
                // 구글맵 네비게이션 표준 색상
                val googleRouteBlue = Color(0xFF4285F4)         // 앞으로 갈 길
                val googleTraveledGray = Color(0xFF9AA0A6)      // 이미 지나온 길

                val sortedWaypoints = course.waypoints.sortedBy { it.order }
                // 마지막 방문한 stop의 order (없으면 -1)
                val lastVisitedStopOrder = course.stops
                    .filter { it.order in visitedOrders }
                    .maxOfOrNull { it.order } ?: -1

                // 지나온 경로와 앞으로 갈 경로 분리.
                // lastVisitedStopOrder 까지의 waypoint들은 "지나온 길"로 회색 처리.
                val traveledPoints = mutableListOf<LatLng>()
                val upcomingPoints = mutableListOf<LatLng>()
                var splitReached = false
                sortedWaypoints.forEach { wp ->
                    val pt = LatLng(wp.latitude, wp.longitude)
                    if (!splitReached && wp.order <= lastVisitedStopOrder) {
                        traveledPoints.add(pt)
                    } else {
                        // 연결점은 양쪽 모두에 포함해 선이 끊어지지 않게
                        if (!splitReached && traveledPoints.isNotEmpty()) {
                            traveledPoints.add(pt)  // 경계에서 마지막 한 점 공유
                        }
                        upcomingPoints.add(pt)
                        splitReached = true
                    }
                }

                // 1a) 지나온 경로 — 회색
                if (traveledPoints.size >= 2) {
                    Polyline(
                        points = traveledPoints,
                        color = googleTraveledGray,
                        width = 12f,
                        jointType = com.google.android.gms.maps.model.JointType.ROUND,
                        startCap = com.google.android.gms.maps.model.RoundCap(),
                        endCap = com.google.android.gms.maps.model.RoundCap(),
                        zIndex = 3f
                    )
                }
                // 1b) 앞으로 갈 경로 — 파랑
                if (upcomingPoints.size >= 2) {
                    Polyline(
                        points = upcomingPoints,
                        color = googleRouteBlue,
                        width = 14f,
                        jointType = com.google.android.gms.maps.model.JointType.ROUND,
                        startCap = com.google.android.gms.maps.model.RoundCap(),
                        endCap = com.google.android.gms.maps.model.RoundCap(),
                        zIndex = 4f
                    )
                }

                // 2) 내비게이션 모드: 내 위치 → 다음 웨이포인트까지 실선 강조 (파랑 굵게)
                if (isNavigating && currentLocation != null && nextWaypointOrder != null) {
                    val nextWp = course.stops.firstOrNull { it.order == nextWaypointOrder }
                    if (nextWp != null) {
                        Polyline(
                            points = listOf(
                                LatLng(currentLocation.latitude, currentLocation.longitude),
                                LatLng(nextWp.latitude, nextWp.longitude)
                            ),
                            color = googleRouteBlue,
                            width = 18f,
                            jointType = com.google.android.gms.maps.model.JointType.ROUND,
                            zIndex = 6f
                        )
                    }
                }
                // 번호 마커: stop waypoint만 표시 (경유점 제외)
                course.stops.forEach { wp ->
                    val visited = wp.order in visitedOrders
                    val isNext = wp.order == nextWaypointOrder
                    MarkerComposable(
                        keys = arrayOf("wp_${course.id}_${wp.order}", visited, isNext, targetLanguage),
                        state = MarkerState(position = LatLng(wp.latitude, wp.longitude)),
                        onClick = {
                            val hid = wp.heritageId
                            if (hid != null) onNavigateToDetail(hid, null)
                            true
                        },
                        zIndex = 8f
                    ) {
                        CourseWaypointMarker(
                            order = wp.order,
                            name = wp.localizedName(targetLanguage),
                            visited = visited,
                            isNext = isNext
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
                        if (selectedCourse != null) {
                            // 코스 선택 시: 진행률만 간결하게 표시
                            val stopCount = selectedCourse.stops.size
                            val visitedCount = visitedOrders.count { o -> selectedCourse.stops.any { it.order == o } }
                            Text(
                                text = "🗺️ ${selectedCourse.localizedName(targetLanguage)}",
                                fontSize = 11.sp,
                                color = Color(0xFFB8860B),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$visitedCount/$stopCount ✓",
                                fontSize = 11.sp,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            val heritageOnlyCount = poiList.count { it.latitude != 0.0 && it.linkedHeritage != null }
                            Text("🔴 ${when(targetLanguage){"en"->"Heritage";"ja"->"文化財";"zh"->"文物";else->"유산"}}",
                                fontSize = 11.sp, color = Color(0xFF880000))
                            Text("🔵 ${when(targetLanguage){"en"->"Facility";"ja"->"施設";"zh"->"设施";else->"시설"}}",
                                fontSize = 11.sp, color = Color(0xFF1565C0))
                            Text("${discoveredHeritages.size}/$heritageOnlyCount ✨",
                                fontSize = 11.sp, color = Color(0xFF666666))
                        }
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

        // ── 관람 코스 FAB ───────────────────────────────────────────────
        var showCourseSelection by remember { mutableStateOf(false) }
        var showWaypointsList by remember { mutableStateOf(false) }

        FloatingActionButton(
            onClick = {
                if (selectedCourse != null) showWaypointsList = true
                else showCourseSelection = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 160.dp),
            containerColor = if (selectedCourse != null) Color(0xFFB8860B) else Color.White,
            contentColor = if (selectedCourse != null) Color.White else Color(0xFFB8860B),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = if (selectedCourse != null) {
                        "🗺️ ${selectedCourse.durationMinutes}${when(targetLanguage){"en"->"min";"ja"->"分";"zh"->"分";else->"분"}}"
                    } else {
                        Strings.getCourseButtonLabel(targetLanguage)
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (showCourseSelection) {
            CourseSelectionBottomSheet(
                courses = tourCourses,
                targetLanguage = targetLanguage,
                onSelect = { courseId ->
                    onSelectCourse(courseId)
                    showCourseSelection = false
                    showWaypointsList = true
                },
                onDismiss = { showCourseSelection = false }
            )
        }

        if (showWaypointsList && selectedCourse != null) {
            CourseWaypointsSheet(
                course = selectedCourse,
                visitedOrders = visitedOrders,
                nextWaypointOrder = nextWaypointOrder,
                currentLocation = currentLocation,
                isNavigating = isNavigating,
                targetLanguage = targetLanguage,
                onNavigateToDetail = onNavigateToDetail,
                onStartNavigation = onStartNavigation,
                onStopNavigation = onStopNavigation,
                onStopCourse = {
                    onClearCourse()
                    showWaypointsList = false
                },
                onDismiss = { showWaypointsList = false }
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

// ─────────────────────────────────────────────────────────────────────────────
// 오프라인 Fallback 지도
// Google Maps 타일을 로드할 수 없을 때 POI 리스트를 텍스트로 표시
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun OfflineMapFallback(
    currentLocation: Location?,
    poiList: List<Poi>,
    discoveredHeritages: Set<String>,
    targetLanguage: String,
    onClose: () -> Unit,
) {
    val offlineTitle = when (targetLanguage) {
        "en" -> "Offline Map"
        "ja" -> "オフラインマップ"
        "zh" -> "离线地图"
        else -> "오프라인 지도"
    }
    val offlineMsg = when (targetLanguage) {
        "en" -> "Internet is required for the map.\nHere are nearby points of interest:"
        "ja" -> "地図にはインターネットが必要です。\n付近のスポット:"
        "zh" -> "地图需要互联网。\n以下是附近的景点:"
        else -> "지도를 보려면 인터넷이 필요합니다.\n주변 관심 지점 목록:"
    }

    // POI를 현재 위치 기준 거리순 정렬 (위치 없으면 원래 순서)
    val sortedPois = remember(currentLocation, poiList) {
        if (currentLocation != null) {
            poiList
                .filter { it.latitude != 0.0 }
                .map { poi ->
                    poi to SpatialCalculator.calcDistanceM(
                        currentLocation.latitude, currentLocation.longitude,
                        poi.latitude, poi.longitude
                    )
                }
                .sortedBy { it.second }
        } else {
            poiList.filter { it.latitude != 0.0 }.map { it to 0f }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            // 헤더
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🗺️ $offlineTitle",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "닫기", tint = Color.White)
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = offlineMsg,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(16.dp))

            // POI 리스트
            androidx.compose.foundation.lazy.LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sortedPois.size) { index ->
                    val (poi, distance) = sortedPois[index]
                    val isDiscovered = poi.id in discoveredHeritages
                    val title = poi.localizedTitle(targetLanguage)
                    val distText = if (distance > 0f) "${distance.toInt()}m" else ""
                    val bgColor = when {
                        poi.linkedHeritage != null && isDiscovered -> Color(0xFF2E1A00)
                        poi.linkedHeritage != null -> Color(0xFF1A0000)
                        else -> Color(0xFF0A1525)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor, RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isDiscovered) "✨" else poi.type.icon,
                                fontSize = 18.sp
                            )
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = title,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = if (poi.linkedHeritage != null) FontWeight.Bold else FontWeight.Normal
                                )
                                if (poi.linkedHeritage == null) {
                                    Text(
                                        text = poi.type.labelKo,
                                        color = Color.White.copy(alpha = 0.5f),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                        if (distText.isNotEmpty()) {
                            Text(
                                text = distText,
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        ?: return false
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

/**
 * 내 위치 마커 — 구글맵 스타일 펄스 애니메이션.
 *
 * 구성:
 *  1. 바깥쪽 확장 링: 반복적으로 커지며 페이드 아웃 (구글 파랑 그라데이션)
 *  2. 두 번째 링: 첫 링과 시차를 두고 확장 → 연속적으로 퍼지는 효과
 *  3. 흰색 테두리 원 (배경 구분)
 *  4. 중앙 단색 파란 점 (고정)
 */
@Composable
private fun PulsingLocationMarker() {
    val googleBlue = Color(0xFF4285F4)
    val transition = rememberInfiniteTransition(label = "loc_pulse")

    val scale1 by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 2.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_scale_1"
    )
    val alpha1 by transition.animateFloat(
        initialValue = 0.55f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha_1"
    )

    Box(
        modifier = Modifier.size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        // 1) 바깥 펄스 링 (확장 + 페이드)
        Box(
            modifier = Modifier
                .size(40.dp)
                .graphicsLayer {
                    scaleX = scale1
                    scaleY = scale1
                    this.alpha = alpha1
                }
                .background(googleBlue, CircleShape)
        )

        // 2) 흰색 외곽 (배경 대비)
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(Color.White, CircleShape)
        )

        // 3) 중앙 단색 파란 점 (구글맵 시그니처 도트)
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(googleBlue, CircleShape)
        )
    }
}

/**
 * 코스 번호 마커.
 *
 * - `isNext == true` → 빨간색 테두리 + 펄스 효과로 "다음 방문지" 강조
 * - `visited == true` → ✓ 표시로 완료된 방문지 표시
 * - 그 외 → 금색 배지 + 순서 번호
 */
@Composable
private fun CourseWaypointMarker(
    order: Int,
    name: String,
    visited: Boolean,
    isNext: Boolean
) {
    val bg = when {
        visited -> Color(0xFF2E7D32)      // 초록
        isNext -> Color(0xFFD32F2F)       // 빨강 (강조)
        else -> Color(0xFFB8860B)         // 금박
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(bg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (visited) {
                Text("✓", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            } else {
                Text("$order", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = name,
            fontSize = 9.sp,
            color = if (isNext) Color(0xFFD32F2F) else Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.95f), RoundedCornerShape(4.dp))
                .padding(horizontal = 4.dp, vertical = 1.dp)
        )
    }
}
