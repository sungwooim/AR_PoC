package com.example.ar_poc.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.Locale

/**
 * Helper to wrap a context with a specific locale.
 * This is crucial for Google Maps to display labels in the selected language.
 */
object LocaleHelper {
    fun wrap(context: Context, languageCode: String): ContextWrapper {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        
        configuration.setLocale(locale)
        
        val localizedContext = context.createConfigurationContext(configuration)
        return ContextWrapper(localizedContext)
    }
}
