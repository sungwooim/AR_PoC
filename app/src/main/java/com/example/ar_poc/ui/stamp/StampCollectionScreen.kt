package com.example.ar_poc.ui.stamp

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.Strings
import com.example.ar_poc.domain.model.HeritageContent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 도장 수집 화면 — AR 인식을 통해 발견한 유산을 스탬프로 모으는 화면
 * - 발견한 유산: 컬러 카드 (도장 찍힘 표시)
 * - 미발견 유산: 흑백 + 잠금 표시
 */
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun StampCollectionScreen(
    discoveredHeritages: Set<String>,
    discoveredSubElements: Set<String>,
    heritageList: List<HeritageContent>,
    targetLanguage: String,
    getDiscoveryTime: (String) -> Long? = { null },
    onClose: () -> Unit
) {
    var selectedHeritage by remember { mutableStateOf<HeritageContent?>(null) }
    
    val totalCount = heritageList.size
    val discoveredCount = discoveredHeritages.size
    val progressFraction = if (totalCount > 0) discoveredCount.toFloat() / totalCount else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A0A00),
                        Color(0xFF2D1500)
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = Strings.getStampLabels(targetLanguage).title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFFFFD280)
                    )
                    Text(
                        text = Strings.getStampScoreLabel(targetLanguage, discoveredCount, totalCount),
                        fontSize = 14.sp,
                        color = Color(0xFFFFD280).copy(alpha = 0.7f)
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = Strings.getCloseLabel(targetLanguage),
                        tint = Color.White
                    )
                }
            }

            // Progress bar
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                LinearProgressIndicator(
                    progress = { progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFFFD280),
                    trackColor = Color.White.copy(alpha = 0.15f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (discoveredCount == totalCount && totalCount > 0) {
                        Strings.getStampLabels(targetLanguage).completeMessage
                    } else {
                        Strings.getStampRemainingLabel(targetLanguage, totalCount - discoveredCount)
                    },
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stamp grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(heritageList) { heritage ->
                    val isDiscovered = discoveredHeritages.contains(heritage.id)
                    StampCard(
                        heritage = heritage,
                        isDiscovered = isDiscovered,
                        targetLanguage = targetLanguage,
                        discoveryTimeMs = if (isDiscovered) getDiscoveryTime(heritage.id) else null,
                        onClick = {
                            // 발견/미발견 무관하게 바텀시트 표시
                            // 미발견은 ❓ 상태로 서브엘리먼트 힌트 제공 (동기 부여)
                            if (heritage.subElements.isNotEmpty()) {
                                selectedHeritage = heritage
                            }
                        }
                    )
                }
            }
        }

        // 바텀 시트 (이스터에그 명단 팝업)
        if (selectedHeritage != null) {
            val heritage = selectedHeritage!!
            var expandedHintId by remember { mutableStateOf<String?>(null) }
            
            androidx.compose.material3.ModalBottomSheet(
                onDismissRequest = { 
                    selectedHeritage = null
                    expandedHintId = null
                },
                containerColor = Color(0xFF2D1500)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val title = heritage.titleMap[targetLanguage] ?: heritage.title
                    Text(
                        text = Strings.getHiddenTreasureTitle(targetLanguage, title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD280),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(heritage.subElements) { subElem ->
                            val isSubDiscovered = discoveredSubElements.contains(subElem.id)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .clickable(enabled = !isSubDiscovered) {
                                        expandedHintId = if (expandedHintId == subElem.id) null else subElem.id
                                    }
                                    .padding(12.dp)
                            ) {
                                if (isSubDiscovered) {
                                    val subTitle = subElem.displayName[targetLanguage] ?: subElem.displayName["en"] ?: "Detail"
                                    Text("✨", fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(subTitle, color = Color.White, fontWeight = FontWeight.Bold)
                                        Text(Strings.getStampLabels(targetLanguage).discoveredLabel, color = Color(0xFFFFD280), fontSize = 12.sp)
                                    }
                                } else {
                                    Text("❓", fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp).alpha(0.5f))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            Strings.getHiddenTreasureUnknown(targetLanguage), 
                                            color = Color.Gray
                                        )
                                        if (expandedHintId == subElem.id) {
                                            val linkedChunk = heritage.chunks.find { it.chunkId == subElem.linkedChunkId }
                                            if (linkedChunk != null) {
                                                val chunkTitle = linkedChunk.titleMap[targetLanguage] ?: linkedChunk.title
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = Strings.getHintMessage(targetLanguage, chunkTitle),
                                                    color = Color(0xFFFFD280).copy(alpha = 0.9f),
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun StampCard(
    heritage: HeritageContent,
    isDiscovered: Boolean,
    targetLanguage: String,
    discoveryTimeMs: Long? = null,
    onClick: () -> Unit
) {
    val localizedTitle = heritage.titleMap[targetLanguage] ?: heritage.title
    val discoveryDateStr = discoveryTimeMs?.let {
        SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(Date(it))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDiscovered)
                Color(0xFF3D1F00)
            else
                Color(0xFF1A1A1A)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDiscovered) 6.dp else 2.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .then(if (!isDiscovered) Modifier.alpha(0.4f) else Modifier),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 도장 이모지 (건물별)
                val emoji = when (heritage.id) {
                    "geunjeongjeon" -> "🏛"
                    "gyeonghoeru" -> "🏞"
                    "gwanghwamun" -> "🏯"
                    else -> "🏛"
                }
                Text(
                    text = emoji,
                    fontSize = 44.sp,
                    modifier = Modifier.then(
                        if (!isDiscovered) Modifier.blur(3.dp) else Modifier
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = localizedTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDiscovered) Color(0xFFFFD280) else Color.Gray,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                if (!isDiscovered) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = Strings.getStampLabels(targetLanguage).lockedLabel,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                // 발견 시각 표시
                if (isDiscovered && discoveryDateStr != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = discoveryDateStr,
                        fontSize = 10.sp,
                        color = Color(0xFFFFD280).copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // 발견 도장 오버레이
            if (isDiscovered) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(36.dp)
                        .background(Color(0xFFFFD280), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", fontSize = 18.sp, color = Color(0xFF1A0A00), fontWeight = FontWeight.Bold)
                }
                // "발견" 배지
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                        .background(
                            Color(0xFFFFD280).copy(alpha = 0.15f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = Strings.getStampLabels(targetLanguage).discoveredLabel,
                        fontSize = 11.sp,
                        color = Color(0xFFFFD280),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
