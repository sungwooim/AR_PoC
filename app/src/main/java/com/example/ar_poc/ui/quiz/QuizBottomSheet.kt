package com.example.ar_poc.ui.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar_poc.domain.model.QuizQuestion
import kotlinx.coroutines.delay

/**
 * 건물 발견 후 팝업되는 퀴즈 바텀 시트.
 * 한 문제씩 표시하며 정답/오답 피드백 후 다음 문제로 자동 진행.
 *
 * @param questions  출제할 문항 리스트 (최대 3개)
 * @param targetLanguage 표시 언어
 * @param onFinish  퀴즈 완료 콜백 (점수 전달)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizBottomSheet(
    questions: List<QuizQuestion>,
    targetLanguage: String,
    onFinish: (score: Int, total: Int) -> Unit,
    onDismiss: () -> Unit
) {
    if (questions.isEmpty()) {
        onFinish(0, 0)
        return
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var isAnswered by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }

    val currentQ = questions[currentIndex]
    val isCorrect = selectedIndex == currentQ.correctIndex

    // 정답 선택 후 1.5초 뒤 자동 다음 문제
    LaunchedEffect(isAnswered) {
        if (isAnswered) {
            delay(1500L)
            if (currentIndex + 1 < questions.size) {
                currentIndex++
                selectedIndex = -1
                isAnswered = false
            } else {
                onFinish(score, questions.size)
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF1A0A00),
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            // 헤더
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (targetLanguage == "ko") "🎓 퀴즈" else "🎓 Quiz",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFFFD280)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 문제 번호 인디케이터
                    questions.indices.forEach { idx ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        idx < currentIndex -> Color(0xFFFFD280)
                                        idx == currentIndex -> Color.White
                                        else -> Color.White.copy(alpha = 0.3f)
                                    }
                                )
                        )
                        if (idx < questions.lastIndex) Spacer(Modifier.width(6.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // 문항 슬라이드 애니메이션
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    (slideInHorizontally { it } + fadeIn(tween(300))) togetherWith
                    (slideOutHorizontally { -it } + fadeOut(tween(200)))
                },
                label = "quiz_question"
            ) { qIdx ->
                val q = questions[qIdx]
                Column {
                    // 문제
                    Text(
                        text = "Q${qIdx + 1}. ${q.getQuestion(targetLanguage)}",
                        fontSize = 16.sp,
                        color = Color.White,
                        lineHeight = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.07f), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    // 보기 4개
                    q.choices.forEachIndexed { idx, choice ->
                        val isSelected = selectedIndex == idx
                        val isThisCorrect = idx == q.correctIndex

                        val bgColor = when {
                            !isAnswered && isSelected -> Color(0xFFFFD280).copy(alpha = 0.2f)
                            isAnswered && isThisCorrect -> Color(0xFF2E7D32).copy(alpha = 0.4f)
                            isAnswered && isSelected && !isThisCorrect -> Color(0xFFB71C1C).copy(alpha = 0.4f)
                            else -> Color.White.copy(alpha = 0.05f)
                        }
                        val borderColor = when {
                            !isAnswered && isSelected -> Color(0xFFFFD280)
                            isAnswered && isThisCorrect -> Color(0xFF66BB6A)
                            isAnswered && isSelected && !isThisCorrect -> Color(0xFFEF5350)
                            else -> Color.White.copy(alpha = 0.15f)
                        }
                        val prefix = when {
                            isAnswered && isThisCorrect -> "✅ "
                            isAnswered && isSelected && !isThisCorrect -> "❌ "
                            else -> "${('A' + idx)}. "
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(bgColor)
                                .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                                .clickable(enabled = !isAnswered) {
                                    selectedIndex = idx
                                    if (isThisCorrect) score++
                                    isAnswered = true
                                }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$prefix${choice.getText(targetLanguage)}",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // 정답 피드백 메시지
            if (isAnswered) {
                Spacer(Modifier.height(14.dp))
                val msg = if (isCorrect) {
                    when (targetLanguage) {
                        "en" -> "🎉 Correct!"
                        "ja" -> "🎉 正解！"
                        "zh" -> "🎉 正确！"
                        else -> "🎉 정답입니다!"
                    }
                } else {
                    when (targetLanguage) {
                        "en" -> "💡 Wrong. The answer is ${questions[currentIndex].choices[questions[currentIndex].correctIndex].getText(targetLanguage)}."
                        "ja" -> "💡 不正解。答えは${questions[currentIndex].choices[questions[currentIndex].correctIndex].getText(targetLanguage)}です。"
                        "zh" -> "💡 错误。正确答案是${questions[currentIndex].choices[questions[currentIndex].correctIndex].getText(targetLanguage)}。"
                        else -> "💡 오답입니다. 정답: ${questions[currentIndex].choices[questions[currentIndex].correctIndex].getText(targetLanguage)}"
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isCorrect) Color(0xFF1B5E20).copy(0.6f) else Color(0xFF7F0000).copy(0.5f),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(msg, color = Color.White, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

/** 퀴즈 완료 결과 팝업 */
@Composable
fun QuizResultDialog(
    score: Int,
    total: Int,
    targetLanguage: String,
    onDismiss: () -> Unit
) {
    val percent = if (total > 0) score * 100 / total else 0
    val (emoji, msg) = when {
        percent == 100 -> "🏆" to when (targetLanguage) {
            "en" -> "Perfect Score! You're a real expert!"
            "ja" -> "満点！本当の専門家ですね！"
            "zh" -> "满分！您是真正的专家！"
            else -> "만점! 진정한 전문가시네요!"
        }
        percent >= 67 -> "⭐" to when (targetLanguage) {
            "en" -> "Well done! Keep exploring!"
            "ja" -> "よくできました！探索を続けましょう！"
            "zh" -> "做得好！继续探索！"
            else -> "잘하셨어요! 계속 탐험하세요!"
        }
        else -> "📖" to when (targetLanguage) {
            "en" -> "Good try! Review the info and try again!"
            "ja" -> "惜しい！説明を読んでもう一度挑戦！"
            "zh" -> "不错！阅读说明后再试一次！"
            else -> "아쉬워요! 설명을 읽고 다시 도전해보세요!"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2D1500),
        title = {
            Text(
                text = "$emoji ${when (targetLanguage) { "en" -> "Quiz Complete"; "ja" -> "クイズ完了"; "zh" -> "测验完成"; else -> "퀴즈 완료" }}",
                color = Color(0xFFFFD280), fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "$score / $total",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                // 점수 진행바
                LinearProgressIndicator(
                    progress = { percent / 100f },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFFFFD280),
                    trackColor = Color.White.copy(0.2f)
                )
                Spacer(Modifier.height(12.dp))
                Text(msg, color = Color.White.copy(0.85f), fontSize = 14.sp, textAlign = TextAlign.Center)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(when (targetLanguage) { "en" -> "Close"; "ja" -> "閉じる"; "zh" -> "关闭"; else -> "닫기" }, color = Color(0xFFFFD280))
            }
        }
    )
}
