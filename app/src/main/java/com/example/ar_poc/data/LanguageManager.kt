package com.example.ar_poc.data

import android.content.Context
import android.content.SharedPreferences

class LanguageManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getSelectedLanguage(): String? = prefs.getString("selected_lang", null)

    fun saveLanguage(langCode: String) {
        prefs.edit().putString("selected_lang", langCode).apply()
    }

    fun isLanguageSelected(): Boolean = getSelectedLanguage() != null

    // Activity recreate() 후 다시 진입했는지 여부를 표시
    fun setLanguageChangedFlag(changed: Boolean) {
        prefs.edit().putBoolean("language_changed", changed).apply()
    }

    fun consumeLanguageChangedFlag(): Boolean {
        val changed = prefs.getBoolean("language_changed", false)
        if (changed) setLanguageChangedFlag(false)
        return changed
    }
}
