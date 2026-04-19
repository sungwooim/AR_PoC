package com.example.ar_poc

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ar_poc.Strings
import com.example.ar_poc.domain.repository.HeritageRepository
import com.example.ar_poc.ui.components.AssetImage
import com.example.ar_poc.ui.components.ImageGallery
import com.example.ar_poc.ui.quiz.QuizBottomSheet
import com.example.ar_poc.ui.quiz.QuizResultDialog
import com.example.ar_poc.ui.viewmodel.ARViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    heritageId: String,
    chunkId: String? = null,
    targetLanguage: String = "ko",
    viewModel: ARViewModel? = null,
    onBack: () -> Unit
) {
    val repository = remember { HeritageRepository() }
    
    var title by remember { mutableStateOf("") }
    var shortDescription by remember { mutableStateOf("") }
    var chunks by remember { mutableStateOf(listOf<com.example.ar_poc.domain.model.HeritageChunk>()) }
    var isLoading by remember { mutableStateOf(true) }

    val originalContent = remember(heritageId) { repository.getHeritageContent(heritageId) }

    val sortedChunks = remember(heritageId, chunkId, originalContent) {
        if (originalContent == null) emptyList()
        else if (chunkId != null) repository.getRelevantChunks(heritageId, chunkId)
        else originalContent.chunks
    }

    LaunchedEffect(heritageId, chunkId, targetLanguage) {
        val content = originalContent ?: return@LaunchedEffect
        isLoading = true

        if (targetLanguage == "ko") {
            title = content.title
            shortDescription = content.shortDescription
            chunks = sortedChunks
        } else {
            // Retrieve pre-translated content from maps, fallback to original if missing
            title = content.titleMap[targetLanguage] ?: content.title
            shortDescription = content.shortDescMap[targetLanguage] ?: content.shortDescription
            
            chunks = sortedChunks.map { chunk ->
                chunk.copy(
                    section = Strings.getSection(chunk.section, targetLanguage),
                    title = chunk.titleMap[targetLanguage] ?: chunk.title,
                    content = chunk.contentMap[targetLanguage] ?: chunk.content
                )
            }
        }
        isLoading = false
    }

    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = if (isLoading) "..." else title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    val backDesc = Strings.getBackLabel(targetLanguage)
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = backDesc
                        )
                    }
                },
                actions = {
                    val shareLabel = Strings.getShareLabel(targetLanguage)
                    TextButton(
                        onClick = {
                            if (title.isNotBlank() && shortDescription.isNotBlank()) {
                                val subject = Strings.getShareSubject(targetLanguage, title)
                                val body = Strings.getShareBodyTemplate(targetLanguage, title, shortDescription)
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, subject)
                                    putExtra(Intent.EXTRA_TEXT, body)
                                }
                                context.startActivity(Intent.createChooser(intent, shareLabel))
                            }
                        },
                        enabled = !isLoading
                    ) {
                        Text(text = "📤 $shareLabel", fontSize = 14.sp)
                    }
                }
            )
        }
    ) { innerPadding ->
        if (originalContent == null) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                val noInfoText = Strings.getNoInfoLabel(targetLanguage)
                Text(text = noInfoText, modifier = Modifier.padding(16.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 전각 대표 이미지 (assets에 있으면 표시)
                originalContent.coverImageAsset?.takeIf { it.isNotBlank() }?.let { path ->
                    AssetImage(
                        assetPath = path,
                        contentDescription = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                Text(
                    text = originalContent.palace,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = shortDescription,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )

                // 갤러리 (cover 외 추가 이미지가 있을 때)
                if (originalContent.galleryImageAssets.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    ImageGallery(
                        imagePaths = originalContent.galleryImageAssets,
                        contentDescriptionPrefix = title
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(modifier = Modifier.alpha(0.5f))
                Spacer(modifier = Modifier.height(24.dp))

                chunks.forEach { chunk ->
                    Column(modifier = Modifier.padding(bottom = 32.dp)) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = chunk.section,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = chunk.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = chunk.content,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 28.sp
                        )
                    }
                }

                // ── 퀴즈 ───────────────────────────────────
                if (viewModel != null) {
                    DetailQuizSection(
                        viewModel = viewModel,
                        heritageId = heritageId,
                        targetLanguage = targetLanguage
                    )
                }
            }
        }
    }
}

/**
 * 상세 화면 하단 퀴즈 섹션.
 *
 * - viewModel.hasQuiz(heritageId)가 true면 버튼 노출
 * - 클릭 시 viewModel.requestQuiz() → pendingQuizQuestions 채워짐 → QuizBottomSheet 표시
 * - 완료 시 markQuizCompleted() + 결과 다이얼로그
 */
@Composable
private fun DetailQuizSection(
    viewModel: ARViewModel,
    heritageId: String,
    targetLanguage: String
) {
    val pendingQuestions by viewModel.pendingQuizQuestions.collectAsStateWithLifecycle()
    var showResult by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    // 이 전각의 퀴즈 이용 가능 여부 스냅샷 (완료 상태는 completion 후 재평가됨)
    var completed by remember(heritageId) { mutableStateOf(false) }
    val canTakeQuiz = remember(heritageId, completed) {
        !completed && viewModel.hasQuiz(heritageId)
    }

    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(modifier = Modifier.alpha(0.3f))
    Spacer(modifier = Modifier.height(16.dp))

    when {
        completed -> {
            Text(
                text = Strings.getQuizCompletedLabel(targetLanguage),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        canTakeQuiz -> {
            Button(
                onClick = { viewModel.requestQuiz(heritageId) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = Strings.getQuizButtonLabel(targetLanguage),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        else -> {
            Text(
                text = Strings.getQuizUnavailableLabel(targetLanguage),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // QuizBottomSheet (pendingQuestions가 있고 아직 결과 다이얼로그가 열리지 않은 상태)
    if (pendingQuestions.isNotEmpty() && showResult == null) {
        QuizBottomSheet(
            questions = pendingQuestions,
            targetLanguage = targetLanguage,
            onFinish = { score, total ->
                viewModel.markQuizCompleted(heritageId)
                viewModel.clearPendingQuiz()
                showResult = score to total
                completed = true
            },
            onDismiss = {
                viewModel.clearPendingQuiz()
            }
        )
    }

    // 결과 다이얼로그
    showResult?.let { (score, total) ->
        QuizResultDialog(
            score = score,
            total = total,
            targetLanguage = targetLanguage,
            onDismiss = { showResult = null }
        )
    }
}
