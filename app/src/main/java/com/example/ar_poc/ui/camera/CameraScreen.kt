package com.example.ar_poc.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ar_poc.Config
import com.example.ar_poc.core.events.DocentEvent
import com.example.ar_poc.core.events.EventBus
import com.example.ar_poc.core.gateway.DocentGateway
import com.example.ar_poc.core.gateway.QuestionRequest
import com.example.ar_poc.domain.model.*
import com.example.ar_poc.domain.repository.HeritageRepository
import com.example.ar_poc.ui.ar.AROverlayScreen
import com.example.ar_poc.ui.camera.components.ARMarker
import com.example.ar_poc.ui.camera.components.CameraPreviewLayer
import com.example.ar_poc.ui.camera.components.DebugOverlay
import com.example.ar_poc.ui.viewmodel.ARViewModel
import com.example.ar_poc.ui.viewmodel.ARUiState
import com.example.ar_poc.ui.viewmodel.CameraMode
import kotlinx.coroutines.launch
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.ar_poc.ui.camera.components.UnrealXRLayer

@Composable
fun CameraScreen(
    viewModel: ARViewModel,
    onNavigateToDetail: (String, String?) -> Unit,
    targetLanguage: String = "ko"
) {
    val gateway = viewModel.gateway
    val repository = remember { HeritageRepository() }
    val context = LocalContext.current
    val labels = viewModel.labels
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraMode by viewModel.cameraMode.collectAsStateWithLifecycle()
    val gazeProgress by viewModel.gazeProgress.collectAsStateWithLifecycle()
    val azimuth by viewModel.currentAzimuth.collectAsStateWithLifecycle()
    val isInsidePalace by viewModel.isInsidePalace.collectAsStateWithLifecycle()
    val isDevMode by viewModel.isDevMode.collectAsStateWithLifecycle()
    val isTargetValid by viewModel.isObjectCandidateDetected.collectAsStateWithLifecycle()
    val suggestedQuestions by viewModel.suggestedQuestions.collectAsStateWithLifecycle()
    val debugInfo by viewModel.debugInfo.collectAsStateWithLifecycle()
    val recognizedSubElement by viewModel.recognizedSubElement.collectAsStateWithLifecycle()
    val failedMessage by viewModel.recognitionFailedMessage.collectAsStateWithLifecycle()
    // AR 오버레이 전용
    val showArOverlay by viewModel.showArOverlay.collectAsStateWithLifecycle()
    val arPitch by viewModel.arPitch.collectAsStateWithLifecycle()
    val isPhoneFlat by viewModel.isPhoneFlat.collectAsStateWithLifecycle()
    // 위치 / 지도
    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()
    // TTS 및 퀴즈
    val isTtsEnabled by viewModel.isTtsEnabled.collectAsStateWithLifecycle()
    val pendingQuizQuestions by viewModel.pendingQuizQuestions.collectAsStateWithLifecycle()
    
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var prefilledQuestion by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showStampScreen by remember { mutableStateOf(false) }

    val discoveredHeritages by viewModel.discoveredHeritages.collectAsStateWithLifecycle()
    val discoveredSubElements by viewModel.discoveredSubElements.collectAsStateWithLifecycle()

    // 퀴즈 상태
    var showQuizSheet by remember { mutableStateOf(false) }
    var showQuizResultDialog by remember { mutableStateOf(false) }
    var quizResultScore by remember { mutableIntStateOf(0) }
    var quizResultTotal by remember { mutableIntStateOf(0) }
    var quizCompletedHeritageId by remember { mutableStateOf("") }

    // AR 오버레이 중 폰을 눕히면 자동으로 MapScreen으로 전환
    val showFlatMap = showArOverlay && isPhoneFlat

    // Permission Tracking
    var cameraPermissionGranted by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }
    var locationPermissionGranted by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        cameraPermissionGranted = permissions[Manifest.permission.CAMERA] ?: cameraPermissionGranted
        locationPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: locationPermissionGranted
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    val isRecognized = uiState is ARUiState.Detected
    val showARCore = isRecognized && isDevMode

    // 💡 카메라 제어권 해제 훅: Dev Mode 활성화 + 인식 완료 시에만 ARCore 양보 (unbind)
    // isDevMode가 false일 땐 CameraX가 카메라를 독점 유지함
    LaunchedEffect(showARCore) {
        if (showARCore) {
            try {
                val cameraProvider = ProcessCameraProvider.getInstance(context).get()
                cameraProvider.unbindAll()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraMode == CameraMode.LIVE) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            viewModel.forceScan()
                        }
                    }
            ) {
                androidx.compose.animation.Crossfade(targetState = showARCore, label = "CameraSwitch") { show3D ->
                    if (show3D) {
                        UnrealXRLayer(
                            modifier = Modifier.fillMaxSize(),
                            isVisible = true
                        )
                    } else {
                        CameraPreviewLayer(
                            hasPermission = cameraPermissionGranted,
                            onObjectsDetected = { objects, w, h, bitmap ->
                                viewModel.onObjectsDetected(objects, w, h, bitmap)
                            }
                        )
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
        }

        // 1. 우상단: DevMode 토글 (작은 무기) + DebugOverlay
        Column(
            modifier = Modifier
                .padding(top = 48.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End
        ) {
            DevModeToggle(viewModel = viewModel)
            if (isDevMode) {
                Spacer(modifier = Modifier.height(8.dp))
                DebugOverlay(
                    info = debugInfo,
                    currentAzimuth = azimuth
                )
            }
        }

        // 2. Overlay Layer for AR elements
        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is ARUiState.Detected -> {
                    val content = state.content
                    val centerX = state.center.x * screenWidth.value
                    val centerY = state.center.y * screenHeight.value

                    ARMarker(
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.TopStart)
                            .offset(x = (centerX - 75).dp, y = (centerY - 75).dp)
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 140.dp),  // Bottom Nav Bar(~120dp) 위로
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        recognizedSubElement?.let { sub ->
                            FloatingSubElementCard(
                                subElement = sub,
                                targetLanguage = targetLanguage,
                                onDetailClick = { onNavigateToDetail(content.id, sub.linkedChunkId) },
                                onRescan = { viewModel.clearSubElement() }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        FloatingHeritageCard(
                            content = content,
                            onDetailClick = { onNavigateToDetail(content.id, null) },
                            onChatClick = {
                                prefilledQuestion = ""
                                showBottomSheet = true
                            },
                            onQuizClick = if (viewModel.hasQuiz(content.id)) { {
                                viewModel.requestQuiz(content.id)
                                showQuizSheet = true
                            } } else null,
                            onClose = { viewModel.resetDetection() },
                            detailLabel = labels.detail,
                            askLabel = labels.ask
                        )
                    }
                }
                ARUiState.Detecting -> {
                    ScanningReticle(modifier = Modifier.align(Alignment.Center))
                }
                ARUiState.GazeLoading -> {
                    GazeReticle(
                        progress = gazeProgress,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                ARUiState.Idle -> {
                    IdleReticle(modifier = Modifier.align(Alignment.Center))
                }
                else -> {}
            }

            // 상태 가이드 텍스트 (화면 상단 반투명 바)
            val currentFailedMsg = failedMessage  // 로컬 스냅샷 — smart cast 안전
            if (uiState is ARUiState.Idle || uiState is ARUiState.GazeLoading) {
                val guideText = when {
                    currentFailedMsg != null        -> currentFailedMsg
                    uiState is ARUiState.GazeLoading -> labels.recognizing
                    isTargetValid                   -> labels.hold
                    else                            -> labels.point
                }
                val guideBg = if (currentFailedMsg != null) Color(0xBBCC0000) else Color(0x99000000)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 56.dp)
                        .background(guideBg, RoundedCornerShape(20.dp))
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = guideText,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 하단 Bottom Nav Bar — AR토글/TTS/도장 통합
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp)
                    .background(Color(0xDD0D0D17), RoundedCornerShape(32.dp))
                    .padding(vertical = 6.dp, horizontal = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavBarItem(
                        icon = if (showArOverlay) "✕" else "🗺️",
                        label = if (showArOverlay) {
                            when (targetLanguage) { "en" -> "Close"; "ja" -> "閉じる"; "zh" -> "关闭"; else -> "닫기" }
                        } else { "AR Map" },
                        active = showArOverlay,
                        onClick = { viewModel.toggleArOverlay() }
                    )
                    Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color.White.copy(alpha = 0.12f)))
                    NavBarItem(
                        icon = if (isTtsEnabled) "🔊" else "🔇",
                        label = when (targetLanguage) { "en" -> "Sound"; "ja" -> "音声"; "zh" -> "音频"; else -> "음성" },
                        active = isTtsEnabled,
                        onClick = { viewModel.toggleTts() }
                    )
                    Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color.White.copy(alpha = 0.12f)))
                    Box {
                        NavBarItem(
                            icon = "🎯",
                            label = when (targetLanguage) { "en" -> "Stamps"; "ja" -> "スタンプ"; "zh" -> "集章"; else -> "도장" },
                            active = false,
                            onClick = { showStampScreen = true }
                        )
                        if (discoveredHeritages.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 2.dp, y = (-2).dp)
                                    .size(16.dp)
                                    .background(Color(0xFFFFD280), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(discoveredHeritages.size.toString(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A0A00))
                            }
                        }
                    }
                }
            }
        }


        // AR 오버레이 (카메라 프리뷰 위에 표시)
        if (showArOverlay && !showFlatMap) {
            AROverlayScreen(
                poiList = viewModel.poiList,
                currentLat = currentLocation?.latitude ?: Config.MOCK_LATITUDE,
                currentLng = currentLocation?.longitude ?: Config.MOCK_LONGITUDE,
                azimuth = azimuth,
                pitch = arPitch,
                targetLanguage = targetLanguage,
                discoveredHeritages = discoveredHeritages,
                onHeritageClick = { heritageId -> onNavigateToDetail(heritageId, null) },
                onClose = { viewModel.toggleArOverlay() },
                isDevMode = isDevMode
            )
        }

        // 폰 눕힘 감지 → 실제 위치가 표시되는 지도
        if (showFlatMap) {
            val selectedCourse by viewModel.selectedTourCourse.collectAsStateWithLifecycle()
            val visitedOrders by viewModel.visitedWaypointOrders.collectAsStateWithLifecycle()
            val nextWaypoint by viewModel.nextCourseWaypoint.collectAsStateWithLifecycle()
            com.example.ar_poc.ui.map.MapScreen(
                currentLocation = currentLocation,
                discoveredHeritages = discoveredHeritages,
                poiList = viewModel.poiList,
                azimuth = azimuth,
                targetLanguage = targetLanguage,
                tourCourses = viewModel.tourCourses,
                selectedCourse = selectedCourse,
                visitedOrders = visitedOrders,
                nextWaypointOrder = nextWaypoint?.order,
                onSelectCourse = { viewModel.selectTourCourse(it) },
                onClearCourse = { viewModel.clearTourCourse() },
                onToggleWaypointVisited = { viewModel.toggleWaypointVisited(it) },
                onPoiClick = { id -> onNavigateToDetail(id, null) },
                onNavigateToDetail = { hid, cid -> onNavigateToDetail(hid, cid) },
                onClose = { viewModel.toggleArOverlay() }
            )
        }


        // Display Stamp Collection Overlay
        if (showStampScreen) {
            com.example.ar_poc.ui.stamp.StampCollectionScreen(
                discoveredHeritages = discoveredHeritages,
                discoveredSubElements = discoveredSubElements,
                heritageList = viewModel.heritageList,
                targetLanguage = targetLanguage,
                getDiscoveryTime = { id -> viewModel.getDiscoveryTime(id) },
                onNavigateToDetail = { heritageId, chunkId ->
                    showStampScreen = false
                    onNavigateToDetail(heritageId, chunkId)
                },
                onClose = { showStampScreen = false }
            )
        }

        // 퀴즈 팝업 (사용자가 퀴즈 버튼 클릭 시 표시)
        if (showQuizSheet && pendingQuizQuestions.isNotEmpty()) {
            com.example.ar_poc.ui.quiz.QuizBottomSheet(
                questions = pendingQuizQuestions,
                targetLanguage = targetLanguage,
                onFinish = { score, total ->
                    quizResultScore = score
                    quizResultTotal = total
                    quizCompletedHeritageId = pendingQuizQuestions.firstOrNull()?.heritageId ?: ""
                    viewModel.clearPendingQuiz()
                    if (quizCompletedHeritageId.isNotEmpty()) viewModel.markQuizCompleted(quizCompletedHeritageId)
                    showQuizSheet = false
                    showQuizResultDialog = true
                },
                onDismiss = {
                    quizCompletedHeritageId = pendingQuizQuestions.firstOrNull()?.heritageId ?: ""
                    viewModel.clearPendingQuiz()
                    if (quizCompletedHeritageId.isNotEmpty()) viewModel.markQuizCompleted(quizCompletedHeritageId)
                    showQuizSheet = false
                }
            )
        }

        // 퀴즈 결과 다이얼로그
        if (showQuizResultDialog) {
            com.example.ar_poc.ui.quiz.QuizResultDialog(
                score = quizResultScore,
                total = quizResultTotal,
                targetLanguage = targetLanguage,
                onDismiss = { showQuizResultDialog = false }
            )
        }

        if (showBottomSheet) {
            val detectedId = (uiState as? ARUiState.Detected)?.content?.id.orEmpty()
            if (detectedId.isEmpty()) { showBottomSheet = false; return@Box }
            QuestionBottomSheet(
                landmarkId = detectedId,
                repository = repository,
                initialQuestion = prefilledQuestion,
                suggestedQuestions = suggestedQuestions,
                titleLabel = labels.questionTitle,
                placeholderLabel = labels.questionPlaceholder,
                waitingLabel = labels.waitingAnswer,
                gateway = gateway,
                eventBus = viewModel.eventBus,
                viewModel = viewModel,
                targetLanguage = targetLanguage,
                onClose = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun FloatingSubElementCard(
    subElement: com.example.ar_poc.domain.model.SubElement,
    targetLanguage: String,
    onDetailClick: () -> Unit,
    onRescan: () -> Unit = {}
) {
    Card(
        modifier = Modifier.width(280.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onDetailClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✨", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = com.example.ar_poc.Strings.getSubElementLabel(targetLanguage),
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subElement.displayName[targetLanguage] ?: subElement.displayName["en"] ?: "",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
            // 다른 세부유물 스캔 버튼
            Text(
                text = "🔄",
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable { onRescan() }
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun DevModeToggle(viewModel: ARViewModel) {
    val isDevMode by viewModel.isDevMode.collectAsStateWithLifecycle()
    
    Surface(
        onClick = { viewModel.toggleDevMode() },
        color = if (isDevMode) Color.Green.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.6f),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, if (isDevMode) Color.Green else Color.Gray)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(8.dp).background(if (isDevMode) Color.White else Color.Gray, CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "DEV", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DevDebugOverlay(
    isInsidePalace: Boolean,
    azimuth: Float,
    progress: Float,
    isTargetValid: Boolean,
    isDevMode: Boolean,
    locationGranted: Boolean
) {
    val direction = when (azimuth) {
        in 315f..360f, in 0f..45f -> "북"
        in 45f..135f -> "동"
        in 135f..225f -> "남"
        else -> "서"
    }

    Column(
        modifier = Modifier
            .width(160.dp)
            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        DebugRow("개발 모드", if (isDevMode) "ON" else "OFF", if (isDevMode) Color.Green else Color.White)
        DebugRow("위치 권한", if (locationGranted) "허용" else "거부", if (locationGranted) Color.White else Color.Red)
        DebugRow("경복궁 내부", if (isInsidePalace) "YES" else "NO", if (isInsidePalace) Color.Cyan else Color.Red)
        DebugRow("방위각", "${azimuth.toInt()}° ($direction)")
        DebugRow("타겟 감지", if (isTargetValid) "OK" else "대기", if (isTargetValid) Color.Green else Color.Gray)
        DebugRow("응시 진행", "${(progress * 100).toInt()}%")
    }
}

@Composable
private fun DebugRow(label: String, value: String, valueColor: Color = Color.White) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.LightGray, fontSize = 10.sp)
        Text(text = value, color = valueColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun IdleReticle(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(80.dp)) {
        drawCircle(
            color = Color.White.copy(alpha = 0.5f),
            radius = size.minDimension / 4,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Composable
fun GazeReticle(progress: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(100.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = Color.Cyan,
            strokeWidth = 4.dp,
            trackColor = Color.White.copy(alpha = 0.2f)
        )
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(Color.Cyan, CircleShape)
        )
    }
}

@Composable
fun FloatingHeritageCard(
    content: com.example.ar_poc.domain.model.HeritageContent,
    onDetailClick: () -> Unit,
    onChatClick: () -> Unit,
    onQuizClick: (() -> Unit)? = null,
    onClose: () -> Unit,
    detailLabel: String,
    askLabel: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatOffset"
    )

    Card(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .offset(y = floatOffset.dp)
            .widthIn(max = 360.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = content.palace,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = content.title,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                IconButton(onClick = onClose, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = content.shortDescription,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 26.sp
            )
            
            Spacer(modifier = Modifier.height(28.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDetailClick,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        text = detailLabel,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                OutlinedButton(
                    onClick = onChatClick,
                    modifier = Modifier.weight(1f).height(52.dp),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        text = askLabel,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // 퀴즈 버튼 — 풀 수 있는 퀴즈가 있을 때만 표시
                if (onQuizClick != null) {
                    OutlinedButton(
                        onClick = onQuizClick,
                        modifier = Modifier.height(52.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFFFB300)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text("🧩", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestedQuestionChips(
    questions: List<String>,
    onQuestionClick: (String) -> Unit
) {
    androidx.compose.foundation.lazy.LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(questions.size) { index ->
            Surface(
                onClick = { onQuestionClick(questions[index]) },
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Text(
                    text = questions[index],
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}

// ChatMessage는 ARViewModel 안에 정의됨 — import 경유로 접근
typealias ChatMessage = com.example.ar_poc.ui.viewmodel.ARViewModel.ChatMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionBottomSheet(
    landmarkId: String,
    repository: HeritageRepository = remember { HeritageRepository() },
    initialQuestion: String = "",
    suggestedQuestions: List<String> = emptyList(),
    titleLabel: String,
    placeholderLabel: String,
    waitingLabel: String,
    gateway: DocentGateway,
    eventBus: EventBus,
    viewModel: com.example.ar_poc.ui.viewmodel.ARViewModel,
    targetLanguage: String = "ko",
    onClose: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var questionText by remember { mutableStateOf("") }

    // 음성 인식 상태 (내장 SpeechRecognizer)
    val ctx = LocalContext.current
    val sttHelper = remember { com.example.ar_poc.util.SpeechRecognizerHelper(ctx) }
    var isListening by remember { mutableStateOf(false) }
    var micError by remember { mutableStateOf<String?>(null) }
    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            micError = com.example.ar_poc.Strings.getMicPermissionLabel(targetLanguage)
        }
    }
    DisposableEffect(Unit) {
        onDispose { sttHelper.release() }
    }

    // ViewModel에서 채팅 내역 구독 — BottomSheet 닫아도 유지됨
    val messages by viewModel.chatMessages.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(false) }

    val listState = androidx.compose.foundation.lazy.rememberLazyListState()

    // Helper: build RAG context then ask
    suspend fun ask(question: String) {
        if (question.isBlank()) return

        eventBus.publish(
            DocentEvent.QuestionAsked(
                heritageId = landmarkId,
                question = question,
                language = targetLanguage
            )
        )

        viewModel.addChatMessage(ChatMessage(question, isUser = true))
        questionText = ""
        isLoading = true

        try {
            val ragContext = repository.buildRagContext(landmarkId, question)
            viewModel.addChatMessage(ChatMessage("", isUser = false))
            var currentAnswer = ""

            gateway.askQuestion(
                QuestionRequest(
                    question = question,
                    heritageContext = ragContext,
                    targetLanguage = targetLanguage,
                    heritageId = landmarkId,
                )
            ).collect { chunk ->
                for (char in chunk) {
                    currentAnswer += char
                    viewModel.updateLastChatMessage(ChatMessage(currentAnswer, isUser = false))
                    kotlinx.coroutines.delay(15L)
                }
            }
        } catch (e: Exception) {
            val errorPrefix = com.example.ar_poc.Strings.getErrorPrefix(targetLanguage)
            viewModel.addChatMessage(ChatMessage("$errorPrefix${e.message}", isUser = false, isError = true))
        } finally {
            isLoading = false
        }
    }

    // Auto-submit if a prefilled question was provided (only if chat is empty)
    LaunchedEffect(initialQuestion) {
        if (initialQuestion.isNotBlank() && messages.isEmpty()) {
            ask(initialQuestion)
        }
    }

    // Auto-scroll to bottom
    val lastTextLength = messages.lastOrNull()?.text?.length ?: 0
    LaunchedEffect(messages.size, isLoading, lastTextLength) {
        if (messages.isNotEmpty()) {
            val targetIndex = if (isLoading) messages.size else messages.lastIndex
            if (targetIndex >= 0) {
                listState.scrollToItem(targetIndex)
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxHeight(0.85f) // Take up more screen space
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = titleLabel,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Chat Messages Area
            androidx.compose.foundation.lazy.LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(messages.size) { index ->
                    val msg = messages[index]
                    ChatBubble(message = msg)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if (isLoading) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp),
                                shadowElevation = 1.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(14.dp),
                                        strokeWidth = 2.dp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = waitingLabel, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            // Suggested Questions inside Chat (only show if no messages or right before input)
            if (suggestedQuestions.isNotEmpty() && !isLoading) {
                SuggestedQuestionChips(
                    questions = suggestedQuestions,
                    onQuestionClick = { q -> coroutineScope.launch { ask(q) } }
                )
            }

            // Input Area
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(26.dp))
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(26.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                // 음성 입력 버튼 (Android 내장 SpeechRecognizer)
                val voiceLabel = com.example.ar_poc.Strings.getVoiceInputLabel(targetLanguage)
                val listeningLabel = com.example.ar_poc.Strings.getVoiceListeningLabel(targetLanguage)
                IconButton(
                    onClick = {
                        if (isListening) {
                            sttHelper.stop()
                            isListening = false
                            return@IconButton
                        }
                        if (!sttHelper.hasPermission()) {
                            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            return@IconButton
                        }
                        micError = null
                        isListening = true
                        sttHelper.start(
                            languageTag = com.example.ar_poc.util.SpeechRecognizerHelper.languageTag(targetLanguage),
                            onResult = { text ->
                                questionText = text
                                isListening = false
                            },
                            onError = { msg ->
                                micError = msg
                                isListening = false
                            },
                            onPartialResult = { partial ->
                                questionText = partial
                            }
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier.size(44.dp).semantics {
                        contentDescription = if (isListening) listeningLabel else voiceLabel
                    }
                ) {
                    Text(
                        text = if (isListening) "🎙️" else "🎤",
                        fontSize = 18.sp
                    )
                }

                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(
                            if (isListening) listeningLabel else placeholderLabel,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    maxLines = 3,
                    enabled = !isLoading
                )

                IconButton(
                    onClick = {
                        if (questionText.isNotBlank() && !isLoading) {
                            coroutineScope.launch { ask(questionText) }
                        }
                    },
                    enabled = questionText.isNotBlank() && !isLoading,
                    modifier = Modifier
                        .size(44.dp)
                        .background(if (questionText.isNotBlank() && !isLoading) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                ) {
                    val sendDesc = com.example.ar_poc.Strings.getSendLabel(targetLanguage)
                    Icon(
                        Icons.Default.Send, 
                        contentDescription = sendDesc, 
                        tint = if (questionText.isNotBlank() && !isLoading) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), 
                        modifier = Modifier.size(20.dp).offset(x = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isUser) Arrangement.End else Arrangement.Start
    val bgColor = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val textColor = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val shape = if (message.isUser) RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp) 
                else RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp)
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = alignment
    ) {
        Surface(
            color = bgColor,
            shape = shape,
            shadowElevation = if (message.isUser) 0.dp else 1.dp,
            modifier = Modifier.widthIn(max = 290.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = if (message.isError) MaterialTheme.colorScheme.error else textColor
            )
        }
    }
}

@Composable
fun ScanningReticle(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = modifier
            .size(100.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.5f),
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bottom Nav Bar 아이템
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun NavBarItem(
    icon: String,
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (active) Color(0xFF64B5F6) else Color.White.copy(alpha = 0.85f)
    Column(
        modifier = Modifier
            .semantics { contentDescription = label }
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, fontSize = 22.sp)
        Spacer(Modifier.height(2.dp))
        Text(label, fontSize = 10.sp, color = textColor, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal)
    }
}
