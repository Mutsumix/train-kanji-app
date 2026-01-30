package jp.co.sysmac.trainkanji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.sysmac.trainkanji.ui.components.GradientBackground
import jp.co.sysmac.trainkanji.ui.theme.*
import jp.co.sysmac.trainkanji.viewmodel.ReportViewModel
import jp.co.sysmac.trainkanji.viewmodel.ViewModelFactory
import androidx.compose.ui.platform.LocalContext

@Composable
fun ReportScreen(
    onBack: () -> Unit,
    viewModel: ReportViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    val totalStats by viewModel.totalStats.collectAsState()
    val weakKanjiList by viewModel.weakKanjiList.collectAsState()

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(AppSpacing.lg)
        ) {
            // ヘッダー
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "もどる",
                        tint = AppColors.white
                    )
                }
                Text(
                    text = "がくしゅうレポート",
                    style = AppTypography.titleMedium,
                    color = AppColors.white,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = AppSpacing.sm)
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.lg))

            // 全体の成績
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppShapes.cardRadius),
                colors = CardDefaults.cardColors(containerColor = AppColors.white),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.lg)
                ) {
                    Text(
                        text = "ぜんたいのせいせき",
                        style = AppTypography.titleSmall,
                        color = AppColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = AppSpacing.md)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatsColumn(
                            value = "${totalStats.totalQuestions}",
                            label = "といた\nもんだい"
                        )
                        StatsColumn(
                            value = "${totalStats.totalCorrect}",
                            label = "せいかい\nすう"
                        )
                        StatsColumn(
                            value = "${totalStats.accuracyPercent}%",
                            label = "正かい\nりつ",
                            valueColor = AppColors.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.md))

            // 苦手な漢字一覧
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                shape = RoundedCornerShape(AppShapes.cardRadius),
                colors = CardDefaults.cardColors(containerColor = AppColors.white),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppSpacing.lg)
                ) {
                    Text(
                        text = "にがてなかん字 (${weakKanjiList.size}こ)",
                        style = AppTypography.titleSmall,
                        color = AppColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = AppSpacing.md)
                    )

                    if (weakKanjiList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "にがてなかん字は\nありません 🎉",
                                style = AppTypography.bodyLarge,
                                color = AppColors.textSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)
                        ) {
                            items(weakKanjiList) { item ->
                                Surface(
                                    shape = RoundedCornerShape(AppShapes.buttonRadius),
                                    color = AppColors.wrongBg,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(AppSpacing.md),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = item.kanji.kanji,
                                                style = AppTypography.titleLarge,
                                                color = AppColors.textPrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = " (${item.kanji.reading})",
                                                style = AppTypography.bodyMedium,
                                                color = AppColors.textSecondary
                                            )
                                        }
                                        Text(
                                            text = "✗${item.stats.wrongCount} / ○${item.stats.correctCount}",
                                            style = AppTypography.bodyMedium,
                                            color = AppColors.wrong,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsColumn(
    value: String,
    label: String,
    valueColor: androidx.compose.ui.graphics.Color = AppColors.textPrimary
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = AppTypography.titleLarge,
            color = valueColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = AppTypography.caption,
            color = AppColors.textSecondary
        )
    }
}
