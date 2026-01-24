package com.example.trainkanji.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainkanji.data.QuizMode
import com.example.trainkanji.ui.components.ChoiceButton
import com.example.trainkanji.ui.components.ChoiceState
import com.example.trainkanji.ui.components.GradientBackground
import com.example.trainkanji.ui.components.KanjiBox
import com.example.trainkanji.ui.components.SentenceWithHighlight
import com.example.trainkanji.ui.theme.*
import com.example.trainkanji.viewmodel.QuizViewModel
import com.example.trainkanji.viewmodel.ViewModelFactory
import androidx.compose.ui.platform.LocalContext

@Composable
fun QuizScreen(
    mode: QuizMode,
    onFinish: () -> Unit,
    onExit: () -> Unit,
    viewModel: QuizViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(mode) {
        viewModel.startQuiz(mode)
    }

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished) {
            onFinish()
        }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(AppSpacing.md)
        ) {
            // ヘッダー
            QuizHeader(
                currentQuestion = uiState.currentIndex + 1,
                totalQuestions = uiState.questions.size,
                onExit = onExit
            )

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            // 進捗バー
            ProgressBar(progress = uiState.progress)

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            // スコア表示
            Text(
                text = "⭐ スコア: ${uiState.score}",
                style = AppTypography.bodyLarge,
                color = AppColors.white,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            // クイズカード
            uiState.currentKanji?.let { kanji ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppShapes.cardRadius),
                    colors = CardDefaults.cardColors(containerColor = AppColors.white),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppSpacing.md),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // タイトル
                        Surface(
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                            color = AppColors.primary.copy(alpha = 0.1f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(AppSpacing.md),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "🚃 このかん字はなんとよむ？",
                                    style = AppTypography.bodyLarge,
                                    color = AppColors.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(AppSpacing.sm))

                        // 漢字表示
                        KanjiBox(
                            kanji = kanji.kanji,
                            textStyle = TextStyle(
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(AppSpacing.sm))

                        // ヒントボタン（回答前のみ）
                        if (!uiState.isAnswered && !uiState.showHint) {
                            OutlinedButton(
                                onClick = { viewModel.showHint() },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = AppColors.hintText
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = androidx.compose.ui.graphics.SolidColor(AppColors.hintBorder)
                                )
                            ) {
                                Text(
                                    text = "💡 れいぶんを見る",
                                    style = AppTypography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(AppSpacing.sm))
                        }

                        // ヒント表示
                        if (uiState.showHint && !uiState.isAnswered) {
                            Surface(
                                shape = RoundedCornerShape(AppShapes.buttonRadius),
                                color = AppColors.hintBg,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    AppColors.hintBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(AppSpacing.sm)
                                ) {
                                    Text(
                                        text = "ヒント",
                                        style = AppTypography.bodyMedium,
                                        color = AppColors.hintText,
                                        fontWeight = FontWeight.Bold
                                    )
                                    SentenceWithHighlight(
                                        sentence = kanji.sentence,
                                        highlightKanji = kanji.kanji
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(AppSpacing.sm))
                        }

                        // 選択肢（2x2グリッド）
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)
                        ) {
                            uiState.choices.chunked(2).forEach { rowChoices ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)
                                ) {
                                    rowChoices.forEach { choice ->
                                        val state = when {
                                            !uiState.isAnswered -> ChoiceState.DEFAULT
                                            choice == kanji.reading -> ChoiceState.CORRECT
                                            choice == uiState.selectedReading -> ChoiceState.WRONG
                                            else -> ChoiceState.DISABLED
                                        }

                                        ChoiceButton(
                                            text = choice,
                                            state = state,
                                            onClick = { viewModel.selectAnswer(choice) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }

                        // 回答後の結果表示
                        if (uiState.isAnswered) {
                            Spacer(modifier = Modifier.height(AppSpacing.sm))

                            Surface(
                                shape = RoundedCornerShape(AppShapes.buttonRadius),
                                color = if (uiState.isCorrect == true) {
                                    AppColors.correctBg
                                } else {
                                    AppColors.wrongBg
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(AppSpacing.sm),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (uiState.isCorrect == true) {
                                            "⭕ せいかい！"
                                        } else {
                                            "❌ ざんねん"
                                        },
                                        style = AppTypography.titleSmall,
                                        color = if (uiState.isCorrect == true) {
                                            AppColors.correct
                                        } else {
                                            AppColors.wrong
                                        },
                                        fontWeight = FontWeight.Bold
                                    )

                                    if (uiState.isCorrect == false) {
                                        Text(
                                            text = "正しいこたえ: ${kanji.reading}",
                                            style = AppTypography.bodyLarge,
                                            color = AppColors.wrong,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(top = AppSpacing.xs)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(AppSpacing.sm))

                                    Divider(color = AppColors.disabled)

                                    Spacer(modifier = Modifier.height(AppSpacing.sm))

                                    Text(
                                        text = "📖 つかいかた",
                                        style = AppTypography.bodyMedium,
                                        color = AppColors.textSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    SentenceWithHighlight(
                                        sentence = kanji.sentence,
                                        highlightKanji = kanji.kanji
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(AppSpacing.sm))

                            // 次へボタン
                            Button(
                                onClick = { viewModel.nextQuestion() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppColors.primary
                                ),
                                shape = RoundedCornerShape(AppShapes.buttonRadius)
                            ) {
                                Text(
                                    text = "➡️ つぎへ",
                                    style = AppTypography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = AppSpacing.xs)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizHeader(
    currentQuestion: Int,
    totalQuestions: Int,
    onExit: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(AppShapes.buttonRadiusFull),
        color = AppColors.white,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.md, vertical = AppSpacing.sm),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🚃 でんしゃかん字",
                style = AppTypography.bodyLarge,
                color = AppColors.textPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$currentQuestion / $totalQuestions",
                style = AppTypography.bodyMedium,
                color = AppColors.textSecondary,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onExit,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "しゅうりょう",
                    tint = AppColors.textSecondary
                )
            }
        }
    }
}

@Composable
private fun ProgressBar(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "progress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(AppColors.disabled)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .background(AppColors.correct)
        )
    }
}
