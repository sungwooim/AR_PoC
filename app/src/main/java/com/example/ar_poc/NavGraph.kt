package com.example.ar_poc

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ar_poc.data.LanguageManager
import com.example.ar_poc.ui.LanguageSelectionScreen
import com.example.ar_poc.ui.camera.CameraScreen
import com.example.ar_poc.ui.navigation.NavigationHud
import com.example.ar_poc.ui.viewmodel.ARViewModel

sealed class Screen(val route: String) {
    object LanguageSelection : Screen("language_selection")
    object Camera : Screen("camera")
    object Detail : Screen("detail/{heritageId}?chunkId={chunkId}") {
        fun createRoute(heritageId: String, chunkId: String? = null): String {
            return if (chunkId != null) "detail/$heritageId?chunkId=$chunkId"
            else "detail/$heritageId"
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // ⚠️ CompositionLocalProvider 래핑 이전에 실제 Activity 레퍼런스 확보
    // LocalContext.current는 여기서는 진짜 Activity Context임 (아직 감싸지 않음)
    val context = LocalContext.current
    val activity = context as? Activity

    val languageManager = remember { LanguageManager(context) }
    val savedLanguage = remember { languageManager.getSelectedLanguage() }
    var selectedLanguage by remember { mutableStateOf(savedLanguage ?: "ko") }

    val arViewModel: ARViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    LaunchedEffect(selectedLanguage) {
        arViewModel.setLanguage(selectedLanguage)
    }

    // CompositionLocalProvider는 제거함:
    // 실제 구글 맵 로케일은 MainActivity.attachBaseContext()가 담당하므로
    // Compose LocalContext 래핑은 불필요하며 Activity.recreate() 충돌을 유발함
    Box(modifier = Modifier.fillMaxSize()) {
    NavHost(
        navController = navController,
        startDestination = Screen.LanguageSelection.route
    ) {
        composable(Screen.LanguageSelection.route) {
            // Activity 재시작 후 재진입 시: 방금 언어를 변경해서 재시작된 거면 카메라로 즉시 스킵
            val isJustChanged = remember { languageManager.consumeLanguageChangedFlag() }
            LaunchedEffect(isJustChanged) {
                if (isJustChanged) {
                    navController.navigate(Screen.Camera.route) {
                        popUpTo(Screen.LanguageSelection.route) { inclusive = true }
                    }
                }
            }
            // 방금 바꾼 게 아니면 (즉 앱 최초 시작이면) 언어 선택 화면을 정상 노출함
            if (!isJustChanged) {
                LanguageSelectionScreen { code ->
                    languageManager.saveLanguage(code)
                    languageManager.setLanguageChangedFlag(true) // 재시작 플래그 ON
                    activity?.recreate() // Activity를 재시작하여 attachBaseContext()가 새 로케일로 구글 맵 초기화
                }
            }
        }
        composable(Screen.Camera.route) {
            CameraScreen(
                viewModel = arViewModel,
                onNavigateToDetail = { id, chunkId ->
                    navController.navigate(Screen.Detail.createRoute(id, chunkId))
                },
                targetLanguage = selectedLanguage
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                androidx.navigation.navArgument("heritageId") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("chunkId") { type = androidx.navigation.NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val heritageId = backStackEntry.arguments?.getString("heritageId") ?: ""
            val chunkId = backStackEntry.arguments?.getString("chunkId")

            DetailScreen(
                heritageId = heritageId,
                chunkId = chunkId,
                targetLanguage = selectedLanguage,
                viewModel = arViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }

        // ── 전역 내비게이션 HUD 오버레이 ───────────────────────────────
        // 코스 내비게이션 중이면 어느 화면(카메라/상세)에 있어도 상단에 HUD가 떠 있음
        val isNavigating by arViewModel.isNavigating.collectAsStateWithLifecycle()
        val navHudInfo by arViewModel.navHudInfo.collectAsStateWithLifecycle()
        if (isNavigating && navHudInfo != null) {
            val info = navHudInfo!!
            NavigationHud(
                waypointName = info.waypointName,
                distanceM = info.distanceM,
                relativeBearingDeg = info.relativeBearingDeg,
                targetLanguage = selectedLanguage,
                onOpenMap = {
                    // 카메라 화면으로 돌아가기 (AR 오버레이/맵 토글은 사용자가 직접)
                    navController.navigate(Screen.Camera.route) {
                        popUpTo(Screen.Camera.route) { inclusive = true }
                    }
                },
                onDismiss = { arViewModel.stopCourseNavigation() },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
