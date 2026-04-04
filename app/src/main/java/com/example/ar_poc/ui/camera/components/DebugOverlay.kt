package com.example.ar_poc.ui.camera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.domain.DebugInfo

/**
 * Semi-transparent debug panel shown only in DEV_MODE.
 * Displays GPS, compass, candidate ranking, and Gemini raw response.
 */
@Composable
fun DebugOverlay(
    info: DebugInfo,
    currentAzimuth: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.Black.copy(alpha = 0.75f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .widthIn(min = 220.dp)
    ) {
        DebugTitle("🛠 DEBUG OVERLAY")

        Spacer(Modifier.height(6.dp))

        // GPS
        DebugSection("📍 GPS")
        DebugRow("Lat", "%.6f".format(info.latitude))
        DebugRow("Lng", "%.6f".format(info.longitude))
        DebugRow("Accuracy", "%.1fm".format(info.gpsAccuracy))
        DebugRow("In Palace", if (info.isInsidePalace) "✅ YES" else "❌ NO")

        DebugDivider()

        // Compass
        DebugSection("🧭 Compass")
        DebugRow("Live azimuth", "%.1f°".format(currentAzimuth))
        DebugRow("Last frame az", "%.1f°".format(info.azimuth))

        DebugDivider()

        // Candidate Ranking
        DebugSection("🎯 Candidates (GPS bearing rank)")
        if (info.rankedCandidates.isEmpty()) {
            DebugRow("", "— no frame processed yet —")
        } else {
            info.rankedCandidates.forEachIndexed { idx, (name, diff) ->
                val shortName = name.substringAfter("(").trimEnd(')')
                val marker = if (idx == 0) "▶ " else "   "
                DebugRow("${marker}#${idx + 1}", "$shortName  Δ${diff.toInt()}°")
            }
        }

        DebugDivider()

        // Gemini
        DebugSection("🤖 Gemini")
        DebugRow("Raw response", info.geminiRawResponse)
        DebugRow("Resolved ID", info.resolvedId ?: "null")
    }
}

@Composable
private fun DebugTitle(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        color = Color.Cyan
    )
}

@Composable
private fun DebugSection(label: String) {
    Text(
        text = label,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Monospace,
        color = Color(0xFFFFD700) // gold
    )
}

@Composable
private fun DebugRow(key: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (key.isNotEmpty()) {
            Text(
                text = "$key:",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray,
                modifier = Modifier.widthIn(min = 80.dp)
            )
        }
        Text(
            text = value,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.White
        )
    }
}

@Composable
private fun DebugDivider() {
    Spacer(Modifier.height(4.dp))
    HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 0.5.dp)
    Spacer(Modifier.height(4.dp))
}
