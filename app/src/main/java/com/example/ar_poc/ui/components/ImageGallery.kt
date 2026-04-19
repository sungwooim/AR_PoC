package com.example.ar_poc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

/**
 * 수평 스크롤 이미지 갤러리.
 *
 * @param imagePaths `assets/` 기준 상대 경로 리스트
 * @param itemHeight 썸네일 높이 (dp). 기본 160dp — 4:3 비율로 너비 약 213dp.
 */
@Composable
fun ImageGallery(
    imagePaths: List<String>,
    contentDescriptionPrefix: String,
    modifier: Modifier = Modifier,
    itemHeight: Int = 160,
    itemAspectRatio: Float = 4f / 3f
) {
    if (imagePaths.isEmpty()) return

    val itemWidth = (itemHeight * itemAspectRatio).toInt()

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 0.dp)
    ) {
        items(imagePaths) { path ->
            AssetImage(
                assetPath = path,
                contentDescription = contentDescriptionPrefix,
                modifier = Modifier
                    .height(itemHeight.dp)
                    .width(itemWidth.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                inSampleSize = 2
            )
        }
    }
}
