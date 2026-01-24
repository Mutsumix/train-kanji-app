package com.example.trainkanji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trainkanji.ui.components.GradientBackground
import com.example.trainkanji.ui.theme.*

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    onRetry: () -> Unit,
    onBackToStart: () -> Unit
) {
    val percentage = if (total > 0) {
        (score * 100 / total)
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
                        text = "$score / $total",
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
