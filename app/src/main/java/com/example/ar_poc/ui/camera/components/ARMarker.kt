package com.example.ar_poc.ui.camera.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun ARMarker(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "marker")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(modifier = modifier) {
        val strokeWidth = 4.dp.toPx()
        val cornerLength = 30.dp.toPx()
        val color = Color.Cyan.copy(alpha = alpha)

        // Top Left
        drawLine(color, Offset(0f, 0f), Offset(cornerLength, 0f), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(0f, 0f), Offset(0f, cornerLength), strokeWidth, StrokeCap.Round)

        // Top Right
        drawLine(color, Offset(size.width, 0f), Offset(size.width - cornerLength, 0f), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(size.width, 0f), Offset(size.width, cornerLength), strokeWidth, StrokeCap.Round)

        // Bottom Left
        drawLine(color, Offset(0f, size.height), Offset(cornerLength, size.height), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(0f, size.height), Offset(0f, size.height - cornerLength), strokeWidth, StrokeCap.Round)

        // Bottom Right
        drawLine(color, Offset(size.width, size.height), Offset(size.width - cornerLength, size.height), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(size.width, size.height), Offset(size.width, size.height - cornerLength), strokeWidth, StrokeCap.Round)
    }
}
