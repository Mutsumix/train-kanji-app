package com.example.trainkanji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainkanji.ui.components.GradientBackground
import com.example.trainkanji.ui.theme.*
import com.example.trainkanji.viewmodel.QuizViewModel
import com.example.trainkanji.viewmodel.ViewModelFactory
import androidx.compose.ui.platform.LocalContext

@Composable
fun ResultScreen(
    onRetry: () -> Unit,
    onBackToStart: () -> Unit,
    viewModel: QuizViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()

    val totalQuestions = uiState.questions.size
    val score = uiState.score
    val percentage = if (totalQuestions > 0) {
        (score * 100 / totalQuestions)
    } else 0

    val (message, emoji) = when {
        percentage == 100 -> "パーフェクト！すごい！" to "🎉"
        percentage >= 80 -> "よくできました！" to "👏"
        percentage >= 60 -> "がんばったね！" to "💪"
        else -> "もういっかいチャレンジ！" to "🔄"
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppSpacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 電車絵文字
            Text(
                text = "🚃",
                style = AppTypography.kanjiDisplay.copy(fontSize = 64.sp),
                modifier = Modifier.padding(bottom = AppSpacing.md)
            )

            // タイトル
            Text(
                text = "クイズしゅうりょう！",
                style = AppTypography.titleLarge,
                color = AppColors.white,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = AppSpacing.lg)
            )

            // スコアカード
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppShapes.cardRadius),
                colors = CardDefaults.cardColors(containerColor = AppColors.white),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.xl),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // スコア
                    Text(
                        text = "$score / $totalQuestions",
                        style = AppTypography.kanjiDisplay.copy(fontSize = 56.sp),
                        color = AppColors.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.md))

                    // メッセージ
                    Text(
                        text = "$message $emoji",
                        style = AppTypography.titleMedium,
                        color = AppColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // 間違えた漢字
                    if (uiState.sessionWrongList.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(AppSpacing.lg))

                        Divider(color = AppColors.disabled)

                        Spacer(modifier = Modifier.height(AppSpacing.md))

                        Text(
                            text = "まちがえたかん字",
                            style = AppTypography.bodyMedium,
                            color = AppColors.textSecondary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = AppSpacing.sm)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)
                        ) {
                            items(uiState.sessionWrongList) { kanji ->
                                Surface(
                                    shape = RoundedCornerShape(AppShapes.buttonRadius),
                                    color = AppColors.wrongBg,
                                    modifier = Modifier.padding(vertical = AppSpacing.xs)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(AppSpacing.md),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = kanji.kanji,
                                            style = AppTypography.titleLarge,
                                            color = AppColors.wrong,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = kanji.reading,
                                            style = AppTypography.caption,
                                            color = AppColors.wrong
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.lg))

            // もういっかいボタン
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.correct
                ),
                shape = RoundedCornerShape(AppShapes.buttonRadius)
            ) {
                Text(
                    text = "🔄 もういっかい",
                    style = AppTypography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = AppSpacing.md)
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.md))

            // スタートに戻るボタン
            OutlinedButton(
                onClick = onBackToStart,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.white
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(AppColors.white)
                ),
                shape = RoundedCornerShape(AppShapes.buttonRadius)
            ) {
                Text(
                    text = "🏠 スタートにもどる",
                    style = AppTypography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = AppSpacing.md)
                )
            }
        }
    }
}
