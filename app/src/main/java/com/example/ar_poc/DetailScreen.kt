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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.Strings
import com.example.ar_poc.domain.repository.HeritageRepository

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    heritageId: String,
    chunkId: String? = null,
    targetLanguage: String = "ko",
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
            }
        }
    }
}
