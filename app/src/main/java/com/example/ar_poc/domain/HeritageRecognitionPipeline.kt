package com.example.ar_poc.domain

import android.graphics.Bitmap
import android.location.Location
import com.example.ar_poc.ai.GeminiClient
import com.example.ar_poc.data.heritage.HeritageKnowledgeSource
import com.example.ar_poc.data.heritage.LocalHeritageKnowledgeSource
import com.example.ar_poc.domain.location.LocationProvider
import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.spatial.SpatialCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** 유산 인식 결과 및 디버깅 데이터 */
data class DebugInfo(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val gpsAccuracy: Float = 0f,
    val azimuth: Float = 0f,
    val rankedCandidates: List<Pair<String, Float>> = emptyList(),
    val geminiRawResponse: String = "-",
    val resolvedId: String? = null,
    val isInsidePalace: Boolean = false,
    val subElementRawResponse: String = "-",
    val resolvedSubElementId: String? = null
)

class HeritageRecognitionPipeline(
    private val geminiClient: GeminiClient,
    private val locationProvider: LocationProvider,
    private val knowledgeSource: HeritageKnowledgeSource = LocalHeritageKnowledgeSource()
) {
    private var recognizedLandmarkId: String? = null
    private var recognizedSubElementId: String? = null

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _debugInfo = MutableStateFlow(DebugInfo())
    val debugInfo = _debugInfo.asStateFlow()

    suspend fun processFrame(bitmaps: List<Bitmap>, azimuth: Float): String? {
        if (_isProcessing.value) return null
        _isProcessing.value = true

        try {
            val location = locationProvider.getCurrentLocation()
            if (location == null) return null

            val palaceName = locationProvider.getPalaceName(location)
            val insidePalace = palaceName != null

            if (!insidePalace) {
                _debugInfo.value = _debugInfo.value.copy(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    gpsAccuracy = location.accuracy,
                    azimuth = azimuth,
                    isInsidePalace = false
                )
                return null
            }

            if (bitmaps.isEmpty()) return null

            // ── Stage 1: Building recognition ──────────────────────────────
            // getRankedCandidates returns (HeritageContent, angularDiff) — reuse diffs for debug/hint
            val ranked = getRankedCandidates(location, azimuth)
            val locationHint = buildCandidateHint(azimuth, ranked)
            val debugCandidates = ranked.map { (heritage, diff) -> Pair(heritage.title, diff) }

            val landmarkName = geminiClient.classifyLandmark(
                bitmap = bitmaps.last(),
                candidates = ranked.map { (h, _) -> "${h.id}: ${h.title.substringAfter("(").trimEnd(')')}" },
                palaceName = palaceName ?: "Unknown Location",
                locationHint = locationHint
            )

            val cleanLandmarkName = landmarkName.trim()
            val resolvedId = if (!cleanLandmarkName.startsWith("Error", ignoreCase = true) && !cleanLandmarkName.equals("Unknown", ignoreCase = true))
                resolveToId(cleanLandmarkName) else null

            recognizedLandmarkId = resolvedId
            recognizedSubElementId = null

            _debugInfo.value = DebugInfo(
                latitude = location.latitude,
                longitude = location.longitude,
                gpsAccuracy = location.accuracy,
                azimuth = azimuth,
                rankedCandidates = debugCandidates,
                geminiRawResponse = landmarkName,
                resolvedId = resolvedId,
                isInsidePalace = true,
                subElementRawResponse = "-",
                resolvedSubElementId = null
            )
            return resolvedId
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            _isProcessing.value = false
        }
    }

    suspend fun processSubElementFrame(bitmaps: List<Bitmap>): String? {
        if (_isProcessing.value || bitmaps.isEmpty()) return null
        val currentBuildingId = recognizedLandmarkId ?: return null

        _isProcessing.value = true

        try {
            val buildingContent = knowledgeSource.getHeritageById(currentBuildingId) ?: return null
            
            if (buildingContent.subElements.isNotEmpty()) {
                val location = locationProvider.getCurrentLocation()
                val palaceName = location?.let { locationProvider.getPalaceName(it) } ?: "Unknown Palace"

                val subCandidates = buildingContent.subElements.flatMap { el ->
                    el.visualHints.map { hint -> Pair(el.id, hint) }
                }
                
                val matchedId = geminiClient.classifySubElement(
                    bitmap = bitmaps.last(),
                    buildingId = buildingContent.id,
                    buildingTitle = buildingContent.title,
                    palaceName = palaceName,
                    candidates = subCandidates
                )

                val subElementRaw = matchedId ?: "Unknown"
                // GeminiClient.classifySubElement() already filters buildingId → null,
                // so matchedId is either a valid subElement id or null.
                val resolvedSubId = matchedId?.let { mid ->
                    buildingContent.subElements.find { it.id == mid }?.id
                }
                
                recognizedSubElementId = resolvedSubId
                _debugInfo.value = _debugInfo.value.copy(
                    subElementRawResponse = subElementRaw,
                    resolvedSubElementId = resolvedSubId
                )
                
                return resolvedSubId
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            _isProcessing.value = false
        }
    }

    private fun getRankedCandidates(userLocation: Location, azimuth: Float): List<Pair<HeritageContent, Float>> {
        return knowledgeSource.getHeritageList()
            .filter { it.latitude != 0.0 && it.longitude != 0.0 }
            .map { heritage ->
                val bearing = SpatialCalculator.calcBearing(
                    userLocation.latitude, userLocation.longitude,
                    heritage.latitude, heritage.longitude
                )
                Pair(heritage, SpatialCalculator.angularDifference(azimuth, bearing))
            }
            .sortedBy { it.second }
    }

    private fun buildCandidateHint(azimuth: Float, ranked: List<Pair<HeritageContent, Float>>): String {
        val cardinalDir = SpatialCalculator.azimuthToCardinal(azimuth, "en")
        val candidateList = ranked.take(3).joinToString(", ") { (heritage, diff) ->
            "${heritage.id}(${diff.toInt()}°off)"
        }
        return "User facing $cardinalDir. GPS bearing diffs from smallest: $candidateList"
    }

    private fun resolveToId(landmarkName: String): String? {
        val normalized = landmarkName.trim().lowercase()
        return knowledgeSource.getHeritageList().firstOrNull { heritage ->
            heritage.id == normalized ||
            heritage.aliases.any { alias -> alias.lowercase() == normalized } ||
            heritage.aliases.any { alias -> normalized.contains(alias.lowercase()) } ||
            normalized.contains(heritage.id)
        }?.id
    }

    fun clearResult() {
        recognizedLandmarkId = null
        recognizedSubElementId = null
    }
}
