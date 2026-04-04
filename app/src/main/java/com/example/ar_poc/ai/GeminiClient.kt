package com.example.ar_poc.ai

import android.graphics.Bitmap
import com.example.ar_poc.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Gemini AI Client for heritage landmark recognition, translation, and QA.
 */
class GeminiClient {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    /**
     * Gemini 전송 전 비트맵을 다운스케일하여 API 레이턴시 개선.
     * 640x480 원본 → 512px 너비로 축소 시 데이터량 약 35% 감소.
     */
    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int = 512): Bitmap {
        if (bitmap.width <= maxWidth) return bitmap
        val ratio = maxWidth.toFloat() / bitmap.width
        val newWidth = maxWidth
        val newHeight = (bitmap.height * ratio).toInt()
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    // ─────────────────────────────────────────────
    // Stage 1: Building identification
    // ─────────────────────────────────────────────

    suspend fun classifyLandmark(
        bitmap: Bitmap,
        candidates: List<String>,
        palaceName: String,
        locationHint: String = ""
    ): String {
        val candidatesWithIds = candidates.joinToString(", ")
        val hintSection = if (locationHint.isNotBlank())
            "\n\n# GPS/Compass Context (for reference only):\n- $locationHint\n- WARNING: GPS bearing may be inaccurate indoors. ALWAYS prioritize what you visually see in the image over this data."
        else ""
        val prompt = """
            # Role: Expert Heritage Identification Agent
            # Task: Identify which building is visible in the image.
            
            # Context:
            - Palace: [$palaceName]
            - Candidates (ID: Name): [$candidatesWithIds]$hintSection
            
            # Constraints:
            1. BASE YOUR ANSWER ON WHAT YOU VISUALLY SEE IN THE IMAGE. Visual evidence is the primary source of truth.
            2. Response MUST be ONLY the 'landmark_id' string from the Candidates list.
            3. If the building is not in the list or cannot be identified, reply "Unknown".
            4. STRICTLY prohibited: Do not include any preamble, quotes, markdown, or punctuation.
            5. If multiple buildings are visible, identify the most prominent one in the center.
            
            # Output Format:
            {landmark_id} or Unknown
        """.trimIndent()

        val scaled = scaleBitmap(bitmap)
        return try {
            val response = generativeModel.generateContent(
                content {
                    image(scaled)
                    text(prompt)
                }
            )
            response.text?.trim() ?: "Unknown"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message}"
        } finally {
            if (scaled !== bitmap) scaled.recycle()
        }
    }

    // ─────────────────────────────────────────────
    // Stage 2: Sub-element identification
    // ─────────────────────────────────────────────

    /**
     * Identifies a specific architectural or decorative element within a known building.
     *
     * @param candidates List of Pair(subElementId, visualHint)
     * @return Matched sub-element id, or null if nothing is clearly visible
     */
    suspend fun classifySubElement(
        bitmap: Bitmap,
        buildingId: String,
        buildingTitle: String,
        palaceName: String,
        candidates: List<Pair<String, String>>
    ): String? {
        // 건물 외관 자체(buildingId)는 후보에서 제외: 항상 건물 ID가 반환되는 혼동 방지
        val subCandidatesOnly = candidates.filter { (id, _) -> id != buildingId }
        if (subCandidatesOnly.isEmpty()) return null

        val candidateText = subCandidatesOnly.joinToString("\n") { (id, hint) -> "- $id: $hint" }
        val prompt = """
            # Role: Korean Heritage Detail Recognition Expert
            # Task: Identify a SPECIFIC ARCHITECTURAL DETAIL or DECORATIVE ELEMENT in this photo.

            # Context:
            - Location: '$buildingTitle' inside $palaceName
            - The camera is pointing at a CLOSE-UP DETAIL, NOT the full building exterior.

            # Candidates (id: visual description):
            $candidateText

            # Rules:
            1. Focus ONLY on small, specific objects — not the building as a whole.
            2. Match the photo to the BEST candidate based on the visual description.
            3. If NONE of the candidates clearly match what is visible, reply exactly: Unknown
            4. Reply with ONLY the candidate id. No extra text, no quotes, no punctuation.

            # Output:
            {candidate_id} or Unknown
        """.trimIndent()

        val scaled = scaleBitmap(bitmap)
        return try {
            val response = generativeModel.generateContent(
                content {
                    image(scaled)
                    text(prompt)
                }
            )
            val raw = response.text?.trim() ?: return null
            // 건물 ID나 Unknown/Error가 반환되면 null 처리
            if (raw.equals("Unknown", ignoreCase = true) ||
                raw.equals(buildingId, ignoreCase = true) ||
                raw.startsWith("Error")) null else raw
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            if (scaled !== bitmap) scaled.recycle()
        }
    }

    // ─────────────────────────────────────────────
    // Translation
    // ─────────────────────────────────────────────

    suspend fun translateText(text: String, targetLanguage: String): String {
        if (targetLanguage == "ko") return text
        if (text.isBlank()) return text

        val languageName = when (targetLanguage) {
            "en" -> "English"
            "ja" -> "Japanese"
            "zh" -> "Simplified Chinese"
            else -> targetLanguage
        }
        val prompt = "Translate the following Korean text to $languageName. Return ONLY the translated text with no extra commentary:\n\n$text"

        return try {
            val response = generativeModel.generateContent(prompt)
            response.text?.trim() ?: text
        } catch (e: Exception) {
            e.printStackTrace()
            text
        }
    }

    suspend fun translateMultipleTexts(texts: List<String>, targetLanguage: String): List<String> {
        if (targetLanguage == "ko" || texts.isEmpty()) return texts

        val languageName = when (targetLanguage) {
            "en" -> "English"
            "ja" -> "Japanese"
            "zh" -> "Simplified Chinese"
            else -> targetLanguage
        }
        
        val jsonArrayStr = org.json.JSONArray(texts).toString()
        val prompt = "Translate each string in the following JSON array from Korean to $languageName. Return ONLY a valid JSON array of strings containing the translations in the exact same order. Do not include markdown tags like ```json.\n\n$jsonArrayStr"

        return try {
            val response = generativeModel.generateContent(prompt)
            var text = response.text?.trim() ?: return texts
            
            // Extract the JSON array from the response to avoid conversational fillers or markdown
            val startIndex = text.indexOf('[')
            val endIndex = text.lastIndexOf(']')
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                text = text.substring(startIndex, endIndex + 1)
            } else {
                return texts
            }

            val jsonArray = org.json.JSONArray(text)
            val translatedList = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                translatedList.add(jsonArray.getString(i))
            }
            
            if (translatedList.size == texts.size) translatedList else texts
        } catch (e: Exception) {
            e.printStackTrace()
            texts
        }
    }

    // ─────────────────────────────────────────────
    // RAG Q&A
    // ─────────────────────────────────────────────

    fun askQuestion(question: String, heritageContext: String, targetLanguage: String): Flow<String> {
        val langInstruction = when (targetLanguage) {
            "en" -> "Respond in English."
            "ja" -> "日本語で回答してください。"
            "zh" -> "用中文回答。"
            else -> "한국어로 답변해주세요."
        }
        val insufficientMsg = when (targetLanguage) {
            "en" -> "I don't have enough information in my knowledge base to answer that question."
            "ja" -> "その質問に答える十分な情報がありません。"
            "zh" -> "我没有足够的信息来回答这个问题。"
            else -> "해당 질문에 답하기 위한 충분한 정보가 없습니다."
        }
        val contextNote = if (targetLanguage != "ko")
            "Note: The context below is written in Korean. Read it and answer the question in the requested language."
        else ""

        val prompt = """
            You are a knowledgeable guide for Korean cultural heritage sites.
            $contextNote

            --- CONTEXT ---
            $heritageContext
            --- END CONTEXT ---

            QUESTION: $question

            RULES:
            1. $langInstruction
            2. ONLY use information provided in the CONTEXT above. Do NOT use outside knowledge.
            3. If the CONTEXT does not contain enough information, reply EXACTLY with: "$insufficientMsg"
            4. Answer in 2-4 sentences, concisely and accurately.
            5. Do not say "According to the context" or similar meta-phrases.
        """.trimIndent()

        return generativeModel.generateContentStream(prompt)
            .map { it.text ?: "" }
            .catch { e -> 
                e.printStackTrace()
                emit("Error: ${e.message}") 
            }
    }
}
