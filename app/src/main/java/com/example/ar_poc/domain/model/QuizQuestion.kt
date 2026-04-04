package com.example.ar_poc.domain.model

/** 4지선다 퀴즈 문항 */
data class QuizQuestion(
    val heritageId: String,
    val questionKo: String,
    val questionEn: String,
    val questionJa: String,
    val questionZh: String,
    val choices: List<QuizChoice>,
    val correctIndex: Int   // 0-based index into choices
) {
    fun getQuestion(lang: String) = when (lang) {
        "en" -> questionEn
        "ja" -> questionJa
        "zh" -> questionZh
        else -> questionKo
    }
}

data class QuizChoice(
    val textKo: String,
    val textEn: String,
    val textJa: String,
    val textZh: String
) {
    fun getText(lang: String) = when (lang) {
        "en" -> textEn
        "ja" -> textJa
        "zh" -> textZh
        else -> textKo
    }
}
