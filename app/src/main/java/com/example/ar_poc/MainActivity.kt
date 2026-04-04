package com.example.ar_poc

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ar_poc.data.LanguageManager
import com.example.ar_poc.ui.theme.Ar_pocTheme
import com.example.ar_poc.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * 저장된 언어를 기반으로 Activity 컨텍스트의 로케일을 설정합니다.
     * Google Maps SDK는 Activity의 baseContext 로케일을 사용하므로,
     * 이 시점에 Locale을 적용해야 지도 레이블 언어가 올바르게 표시됩니다.
     */
    override fun attachBaseContext(newBase: Context) {
        val langCode = LanguageManager(newBase).getSelectedLanguage() ?: "ko"
        super.attachBaseContext(LocaleHelper.wrap(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ar_pocTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
