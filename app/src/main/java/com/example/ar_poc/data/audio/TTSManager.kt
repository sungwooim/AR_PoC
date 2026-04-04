package com.example.ar_poc.data.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

/**
 * Android 내장 TextToSpeech를 감싼 싱글톤 래퍼.
 * 외부 API 없이 작동하며, onDestroy 시 반드시 shutdown() 호출 필요.
 */
class TTSManager(context: Context) {

    private val _isEnabled = MutableStateFlow(true)
    val isEnabled: StateFlow<Boolean> = _isEnabled.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private var initialized = false
    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            initialized = status == TextToSpeech.SUCCESS
        }
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) { _isSpeaking.value = true }
            override fun onDone(utteranceId: String?) { _isSpeaking.value = false }
            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) { _isSpeaking.value = false }
        })
    }

    fun toggleEnabled() {
        _isEnabled.value = !_isEnabled.value
        if (!_isEnabled.value) stop()
    }

    /**
     * 텍스트를 해당 언어 로케일로 낭독.
     * @param language "ko" | "en" | "ja" | "zh"
     */
    fun speak(text: String, language: String) {
        if (!initialized || !_isEnabled.value || text.isBlank()) return
        val locale = when (language) {
            "en" -> Locale.ENGLISH
            "ja" -> Locale.JAPANESE
            "zh" -> Locale.CHINESE
            else -> Locale.KOREAN
        }
        val result = tts?.setLanguage(locale) ?: return
        // 언어 지원 여부 확인 (지원 안 하면 기본값으로 fallback)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.language = Locale.ENGLISH
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ar_poc_tts")
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
