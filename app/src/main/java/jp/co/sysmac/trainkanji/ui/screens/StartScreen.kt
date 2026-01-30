package jp.co.sysmac.trainkanji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import jp.co.sysmac.trainkanji.data.QuizMode
import jp.co.sysmac.trainkanji.ui.components.GradientBackground
import jp.co.sysmac.trainkanji.ui.theme.*
import jp.co.sysmac.trainkanji.viewmodel.StartViewModel
import jp.co.sysmac.trainkanji.viewmodel.ViewModelFactory
import androidx.compose.ui.platform.LocalContext

@Composable
fun StartScreen(
    onStartQuiz: (QuizMode) -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: StartViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    val totalStats by viewModel.totalStats.collectAsState()
    val weakKanjiCount by viewModel.weakKanjiCount.collectAsState()

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            // 設定ボタン（右上）
            IconButton(
                onClick = onOpenSettings,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(AppSpacing.md)
                    .size(56.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = AppColors.white,
                    shadowElevation = 4.dp,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "せってい",
                        modifier = Modifier
                            .padding(AppSpacing.sm)
                            .size(32.dp),
                        tint = AppColors.textSecondary
                    )
                }
            }

            // メインコンテンツ
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
                    style = AppTypography.kanjiDisplay.copy(fontSize = 80.sp),
                    modifier = Modifier.padding(bottom = AppSpacing.lg)
                )

                // タイトル
                Text(
                    text = "でんしゃ",
                    style = AppTypography.titleLarge,
                    color = AppColors.white,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "かん字クイズ",
                    style = AppTypography.titleLarge,
                    color = AppColors.white,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = AppSpacing.sm)
                )

                // サブタイトル
                Text(
                    text = "小学1年生のかん字 80字",
                    style = AppTypography.bodyMedium,
                    color = AppColors.white,
                    modifier = Modifier.padding(bottom = AppSpacing.xl)
                )

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                // 通常モードボタン
                ModeButton(
                    emoji = "📝",
                    title = "つうじょうモード",
                    description = "すべてのかん字から\nしゅつだい",
                    enabled = true,
                    onClick = { onStartQuiz(QuizMode.NORMAL) }
                )

                Spacer(modifier = Modifier.height(AppSpacing.md))

                // 苦手かん字モードボタン
                ModeButton(
                    emoji = "💪",
                    title = "にがてかん字モード",
                    description = if (weakKanjiCount > 0) {
                        "にがてなかん字が\n${weakKanjiCount}こ あります"
                    } else {
                        "にがてなかん字は\nありません"
                    },
                    enabled = weakKanjiCount > 0,
                    onClick = { onStartQuiz(QuizMode.WEAK) }
                )

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                // 学習履歴
                if (totalStats.totalQuestions > 0) {
                    Surface(
                        shape = RoundedCornerShape(AppShapes.buttonRadius),
                        color = AppColors.white.copy(alpha = 0.9f),
                        modifier = Modifier.padding(horizontal = AppSpacing.md)
                    ) {
                        Column(
                            modifier = Modifier.padding(AppSpacing.md),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "これまでの正かいりつ",
                                style = AppTypography.bodyMedium,
                                color = AppColors.textSecondary
                            )
                            Text(
                                text = "${totalStats.accuracyPercent}%",
                                style = AppTypography.titleMedium,
                                color = AppColors.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "(${totalStats.totalCorrect} / ${totalStats.totalQuestions}もん)",
                                style = AppTypography.caption,
                                color = AppColors.textSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModeButton(
    emoji: String,
    title: String,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppShapes.cardRadius),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) AppColors.white else AppColors.disabled,
            disabledContainerColor = AppColors.disabled
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                style = AppTypography.titleLarge.copy(fontSize = 40.sp),
                modifier = Modifier.padding(end = AppSpacing.md)
            )
            Column {
                Text(
                    text = title,
                    style = AppTypography.titleSmall,
                    color = if (enabled) AppColors.textPrimary else AppColors.textMuted,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = AppTypography.bodyMedium,
                    color = if (enabled) AppColors.textSecondary else AppColors.textMuted
                )
            }
        }
    }
}
