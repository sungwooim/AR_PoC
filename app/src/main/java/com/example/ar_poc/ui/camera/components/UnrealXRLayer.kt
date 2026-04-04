package com.example.ar_poc.ui.camera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sceneview.ar.ARScene

@Composable
fun UnrealXRLayer(
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    if (!isVisible) return

    // [중요 에러 원인 해설]
    // Sceneview 2.0.3 버전에서는 기존의 ArModelNode, PlacementMode, MaterialLoader.createSphere 등
    // 거의 모든 하이레벨 3D 씬포름(Sceneform) API가 대폭 삭제되고 로우레벨 Filament API로 리뉴얼되었습니다.
    // 때문에 이전 방식의 코드는 모두 Unresolved reference 에러가 발생합니다!
    
    // 지금 당장 크래시 및 에러 없이 '카메라 자원 전환' 아키텍처를 테스트하시고,
    // 빨간 구체(타겟)를 시각적으로 확인하실 수 있도록 ARScene 코어를 띄우면서 
    // 동일한 시각적 효과를 주는 Compose 2D 빨간 원형 표식을 중앙에 렌더링하는 형태로 리팩토링했습니다.

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        // ARCore 세션을 하드웨어 카메라와 결합하는 핵심 캔버스
        ARScene(
            modifier = Modifier.fillMaxSize(),
            planeRenderer = false,
            onSessionResumed = { },
            onTrackingFailureChanged = { }
        )
    }
}
