package com.example.ar_poc.data.discovery

import android.content.Context
import android.content.SharedPreferences

/**
 * 유산 발견 타임스탬프를 SharedPreferences에 저장하는 로컬 추적기.
 * 최초 발견 시각만 기록하며 덮어쓰지 않음.
 */
class DiscoveryTracker(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** 최초 발견 시 타임스탬프 기록. 이미 기록된 경우 무시. */
    fun recordHeritage(id: String) {
        if (!prefs.contains(keyHeritage(id))) {
            prefs.edit().putLong(keyHeritage(id), System.currentTimeMillis()).apply()
        }
    }

    fun recordSubElement(id: String) {
        if (!prefs.contains(keySubElement(id))) {
            prefs.edit().putLong(keySubElement(id), System.currentTimeMillis()).apply()
        }
    }

    /** 발견 시각 반환 (ms), 미발견이면 null */
    fun getHeritageDiscoveryTime(id: String): Long? =
        if (prefs.contains(keyHeritage(id))) prefs.getLong(keyHeritage(id), 0L) else null

    fun getSubElementDiscoveryTime(id: String): Long? =
        if (prefs.contains(keySubElement(id))) prefs.getLong(keySubElement(id), 0L) else null

    /** 발견한 유산 ID → 타임스탬프 전체 맵 */
    fun getAllHeritageDiscoveries(): Map<String, Long> =
        prefs.all.filterKeys { it.startsWith(PREFIX_HERITAGE) }
            .mapKeys { (k, _) -> k.removePrefix(PREFIX_HERITAGE) }
            .mapValues { (_, v) -> v as Long }

    /** 퀴즈 완료 여부 저장/확인 */
    fun markQuizCompleted(heritageId: String) {
        prefs.edit().putBoolean(keyQuiz(heritageId), true).apply()
    }

    fun isQuizCompleted(heritageId: String): Boolean =
        prefs.getBoolean(keyQuiz(heritageId), false)

    private fun keyHeritage(id: String) = "$PREFIX_HERITAGE$id"
    private fun keySubElement(id: String) = "$PREFIX_SUB$id"
    private fun keyQuiz(id: String) = "$PREFIX_QUIZ$id"

    companion object {
        private const val PREFS_NAME = "ar_poc_discoveries"
        private const val PREFIX_HERITAGE = "heritage_"
        private const val PREFIX_SUB = "sub_"
        private const val PREFIX_QUIZ = "quiz_"
    }
}
