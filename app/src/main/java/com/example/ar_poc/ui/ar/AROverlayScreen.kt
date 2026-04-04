package com.example.ar_poc.ui.ar

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.PoiType
import com.example.ar_poc.domain.spatial.SpatialCalculator

// ─────────────────────────────────────────────────────────────────────────────
// 유틸리티 함수 — SpatialCalculator 위임 Shim (상단 이동)
// ─────────────────────────────────────────────────────────────────────────────

/** AR 카드의 화면 픽셀 좌표 계산. */
fun calcARScreenPos(
    userLat: Double, userLng: Double,
    targetLat: Double, targetLng: Double,
    azimuth: Float, pitch: Float,
    screenW: Float, screenH: Float,
    fovMargin: Float = SpatialCalculator.FOV_MARGIN
): Pair<Float, Float>? = SpatialCalculator.calcScreenPos(
    userLat = userLat, userLng = userLng,
    targetLat = targetLat, targetLng = targetLng,
    azimuth = azimuth, pitch = pitch,
    screenW = screenW, screenH = screenH,
    fovMargin = fovMargin
)

/** 두 GPS 좌표 간 하버사인 거리(미터) 계산. */
fun calcDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float =
    SpatialCalculator.calcDistanceM(lat1, lng1, lat2, lng2)

private const val AR_VISIBLE_RADIUS_M = 500f   // AR 카드 표시 최대 거리 (미터)
private const val AR_MAX_VISIBLE_POIS = 5       // 동시에 표시할 최대 POI 수

/** AR 오버레이. 카메라 프리뷰 위에 표시되며 각 POI를 방위각+GPS 기반으로 허공에 카드로 위치시킵니다. */
@Composable
fun AROverlayScreen(
    poiList: List<Poi>,
    currentLat: Double,
    currentLng: Double,
    azimuth: Float,
    pitch: Float,
    targetLanguage: String,
    discoveredHeritages: Set<String>,
    onHeritageClick: (String) -> Unit,
    onClose: () -> Unit,
    isDevMode: Boolean = false
) {
    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    val density = LocalDensity.current
    
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    Box(modifier = Modifier.fillMaxSize()) {
        // 일반 POI(linkedHeritage=null) 클릭 시 표시할 팝업 상태
        var selectedInfoPoi by remember { mutableStateOf<Poi?>(null) }

        // 표시할 POI 필터: 유산/문 타입만 + GPS 있음 + AR_VISIBLE_RADIUS_M 이내 + 거리 오름차순
        val visiblePois = poiList
            .filter { it.type == PoiType.HERITAGE || it.type == PoiType.GATE }
            .filter { it.latitude != 0.0 && it.longitude != 0.0 }
            .map { poi -> Pair(poi, calcDistance(currentLat, currentLng, poi.latitude, poi.longitude)) }
            .filter { (_, dist) -> dist <= AR_VISIBLE_RADIUS_M }
            .sortedBy { (_, dist) -> dist }
            .take(AR_MAX_VISIBLE_POIS)
            .map { (poi, _) -> poi }

        // 각 POI에 대해 화면 좌표 계산 — 겹침 방지를 위해 일괄 계산 후 Y 분산
        data class CardPos(val poi: Poi, var xDp: Float, var yDp: Float)

        val cardPositions = visiblePois.mapNotNull { poi ->
            val pos = calcARScreenPos(
                userLat = currentLat, userLng = currentLng,
                targetLat = poi.latitude, targetLng = poi.longitude,
                azimuth = azimuth, pitch = pitch,
                screenW = screenWidthPx, screenH = screenHeightPx,
                fovMargin = if (isDevMode) 99f else 1.1f
            ) ?: return@mapNotNull null
            CardPos(poi, with(density) { pos.first.toDp().value }, with(density) { pos.second.toDp().value })
        }.toMutableList()

        // 겹침 방지: X축 기준으로 군집화하여 Y축 분산 (카드 가로 180dp, 세로 90dp 기준)
        val spreadThresholdDp = 190f
        val spreadStepDp = 110f // 세로(90dp)보다 충분히 크게 간격을 줌
        
        cardPositions.sortBy { it.xDp }
        
        val clusters = mutableListOf<MutableList<CardPos>>()
        for (card in cardPositions) {
            if (clusters.isEmpty()) {
                clusters.add(mutableListOf(card))
            } else {
                val lastCluster = clusters.last()
                val lastCard = lastCluster.last()
                if (card.xDp - lastCard.xDp < spreadThresholdDp) {
                    lastCluster.add(card)
                } else {
                    clusters.add(mutableListOf(card))
                }
            }
        }

        // 각 클러스터(군집)별로 세로로 분산
        for (cluster in clusters) {
            if (cluster.size > 1) {
                val baseY = cluster.map { it.yDp }.average().toFloat()
                val groupSize = cluster.size
                cluster.forEachIndexed { index, c ->
                    c.yDp = baseY + (index - groupSize / 2f + 0.5f) * spreadStepDp
                }
            }
        }

        // 카드 렌더링 — 겹침 방지 처리된 cardPositions 사용
        val cardW = 180.dp
        val cardH = 90.dp
        cardPositions.forEach { cardPos ->
            val poi = cardPos.poi
            val xDp = cardPos.xDp.dp
            val yDp = cardPos.yDp.dp

            key(poi.id) {
                val distance = calcDistance(currentLat, currentLng, poi.latitude, poi.longitude)
                val title = poi.localizedTitle(targetLanguage)
                val isDiscovered = poi.type == PoiType.HERITAGE && poi.id in discoveredHeritages

                ARHeritageCard(
                    modifier = Modifier.offset(x = xDp - cardW / 2, y = yDp - cardH / 2),
                    title = title,
                    icon = poi.type.icon,
                    distanceM = distance,
                    isDiscovered = isDiscovered,
                    onClick = {
                        val heritage = poi.linkedHeritage
                        if (heritage != null) {
                            onHeritageClick(heritage.id)
                        } else {
                            selectedInfoPoi = poi
                        }
                    }
                )
            }
        }


        // ── 일반 POI 정보 팝업 ──────────────────────────────
        selectedInfoPoi?.let { poi ->
            PoiInfoDialog(
                poi = poi,
                targetLanguage = targetLanguage,
                onDismiss = { selectedInfoPoi = null }
            )
        }

        // ── 상단 UI 바 ──────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 40.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // AR 모드 라벨
            Surface(
                color = Color.Black.copy(alpha = 0.55f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("AR 오버레이", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            // 닫기 버튼
            Surface(
                onClick = onClose,
                color = Color.Black.copy(alpha = 0.55f),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "AR 닫기",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp).size(20.dp)
                )
            }
        }

        // ── 하단 안내 ──────────────────────────────────────
        Text(
            text = if (targetLanguage == "ko") "📐 폰을 눕히면 지도로 전환" else "📐 Tilt phone flat for map",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
                .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

/** 개별 AR 유산 카드 */
@Composable
private fun ARHeritageCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: String = "📍",
    distanceM: Float,
    isDiscovered: Boolean,
    onClick: () -> Unit
) {
    // 둥실둥실 부유 애니메이션
    val floatTransition = rememberInfiniteTransition(label = "float_${title}")
    val floatY by floatTransition.animateFloat(
        initialValue = 0f, targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "float_y"
    )

    val cardColor = if (isDiscovered) Color(0xFF1A0D00).copy(alpha = 0.92f)
    else Color(0xFF0A1525).copy(alpha = 0.88f)
    val accentColor = if (isDiscovered) Color(0xFFFFD280) else Color(0xFF64B5F6)

    Card(
        modifier = modifier
            .width(180.dp)
            .offset(y = floatY.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column {
            // 상단 컬러 바
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(
                            if (isDiscovered) listOf(Color(0xFFFFD280), Color(0xFFFF8C00))
                            else listOf(Color(0xFF64B5F6), Color(0xFF1565C0))
                        )
                    )
            )

            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                // 타이틀
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(if (isDiscovered) "✨" else icon, fontSize = 14.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = title,
                        color = accentColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        maxLines = 2
                    )
                }

                Spacer(Modifier.height(6.dp))

                // 거리 표시
                val distLabel = if (distanceM < 1000f) "${distanceM.toInt()}m 앞"
                                else "${"%.1f".format(distanceM / 1000f)}km"
                Text(
                    text = distLabel,
                    color = Color.LightGray,
                    fontSize = 11.sp
                )

                Spacer(Modifier.height(8.dp))

                // 방향 포인터 (중앙 점)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(7.dp)
                        .background(accentColor, CircleShape)
                )
            }
        }
    }
}



// ─────────────────────────────────────────────────────────────────────────────
// 일반 POI 정보 팝업 (linkedHeritage == null 인 POI 클릭 시)
// ─────────────────────────────────────────────────────────────────────────────

/**
 * linkHeritage가 없는 일반 POI(카페, 안내소 등) 클릭 시 표시되는 정보 팝업.
 * 아이콘, 타입 라벨, 제목을 보여주고 닫기로 dismiss.
 */
@Composable
fun PoiInfoDialog(
    poi: Poi,
    targetLanguage: String,
    onDismiss: () -> Unit
) {
    val typeLabel = when (targetLanguage) {
        "en" -> when (poi.type) {
            PoiType.CAFE     -> "Café"
            PoiType.SHOP     -> "Souvenir Shop"
            PoiType.INFO     -> "Information"
            PoiType.VIEWPOINT -> "Photo Spot"
            PoiType.GATE     -> "Gate / Entrance"
            PoiType.EXHIBIT  -> "Exhibition"
            else             -> "Facility"
        }
        "ja" -> when (poi.type) {
            PoiType.CAFE     -> "カフェ"
            PoiType.SHOP     -> "お土産店"
            PoiType.INFO     -> "案内所"
            PoiType.VIEWPOINT -> "フォトスポット"
            PoiType.GATE     -> "門 / 入口"
            PoiType.EXHIBIT  -> "展示"
            else             -> "施設"
        }
        "zh" -> when (poi.type) {
            PoiType.CAFE     -> "咖啡厅"
            PoiType.SHOP     -> "纪念品店"
            PoiType.INFO     -> "咨询中心"
            PoiType.VIEWPOINT -> "拍照点"
            PoiType.GATE     -> "门 / 入口"
            PoiType.EXHIBIT  -> "展览"
            else             -> "设施"
        }
        else -> when (poi.type) {
            PoiType.CAFE     -> "카페"
            PoiType.SHOP     -> "기념품점"
            PoiType.INFO     -> "안내소"
            PoiType.VIEWPOINT -> "포토 스팟"
            PoiType.GATE     -> "문 / 입구"
            PoiType.EXHIBIT  -> "전시"
            else             -> "편의시설"
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // 헤더 행
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(poi.type.icon, fontSize = 28.sp)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                text = typeLabel,
                                color = Color(0xFF64B5F6),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = poi.localizedTitle(targetLanguage),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "닫기",
                            tint = Color.LightGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 안내 문구
                val guideText = when (targetLanguage) {
                    "en" -> "This is a ${typeLabel.lowercase()} facility within Gyeongbokgung Palace."
                    "ja" -> "景福宮内の${typeLabel}施設です。"
                    "zh" -> "这是景福宫内的${typeLabel}设施。"
                    else -> "경복궁 내 ${typeLabel} 시설입니다."
                }
                Text(
                    text = guideText,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(20.dp))

                // 닫기 버튼
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = when (targetLanguage) {
                            "en" -> "Close"
                            "ja" -> "閉じる"
                            "zh" -> "关闭"
                            else -> "닫기"
                        },
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
