package jp.co.sysmac.trainkanji.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.sysmac.trainkanji.data.QuestionCount
import jp.co.sysmac.trainkanji.ui.components.GradientBackground
import jp.co.sysmac.trainkanji.ui.theme.*
import jp.co.sysmac.trainkanji.viewmodel.SettingsViewModel
import jp.co.sysmac.trainkanji.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenReport: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    val context = LocalContext.current
    val questionCount by viewModel.questionCount.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    text = "せってい",
                    style = AppTypography.titleMedium,
                    color = AppColors.white,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = AppSpacing.sm)
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.lg))

            // 問題数設定
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
                        text = "もんだいすう",
                        style = AppTypography.titleSmall,
                        color = AppColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = AppSpacing.md)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)
                    ) {
                        QuestionCount.entries.forEach { count ->
                            Button(
                                onClick = { viewModel.setQuestionCount(count) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (count == questionCount) {
                                        AppColors.primary
                                    } else {
                                        AppColors.disabled
                                    }
                                ),
                                shape = RoundedCornerShape(AppShapes.buttonRadius)
                            ) {
                                Text(
                                    text = count.displayName,
                                    style = AppTypography.bodyMedium,
                                    color = if (count == questionCount) {
                                        AppColors.white
                                    } else {
                                        AppColors.textMuted
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.md))

            // 学習レポートボタン
            Card(
                onClick = onOpenReport,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppShapes.cardRadius),
                colors = CardDefaults.cardColors(containerColor = AppColors.white),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "📊",
                            style = AppTypography.titleLarge,
                            modifier = Modifier.padding(end = AppSpacing.md)
                        )
                        Text(
                            text = "がくしゅうレポート",
                            style = AppTypography.bodyLarge,
                            color = AppColors.textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        tint = AppColors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.md))

            // フィードバックボタン
            Card(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/BnKKULJuDwMpdhWJA"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppShapes.cardRadius),
                colors = CardDefaults.cardColors(containerColor = AppColors.white),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "💬",
                            style = AppTypography.titleLarge,
                            modifier = Modifier.padding(end = AppSpacing.md)
                        )
                        Text(
                            text = "フィードバックをおくる",
                            style = AppTypography.bodyLarge,
                            color = AppColors.textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        tint = AppColors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.md))

            // リセットボタン
            Card(
                onClick = { showResetDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppShapes.cardRadius),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.wrongBg
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.lg)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🗑️",
                            style = AppTypography.titleLarge,
                            modifier = Modifier.padding(end = AppSpacing.md)
                        )
                        Text(
                            text = "きろくをリセット",
                            style = AppTypography.bodyLarge,
                            color = AppColors.wrong,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "すべてのきろくをけします",
                        style = AppTypography.bodyMedium,
                        color = AppColors.wrong,
                        modifier = Modifier.padding(top = AppSpacing.xs)
                    )
                }
            }
        }
    }

    // リセット確認ダイアログ
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    text = "きろくをリセット",
                    style = AppTypography.titleSmall
                )
            },
            text = {
                Text(
                    text = "ほんとうにすべてのきろくをけしますか？\nこのそうさはもとにもどせません。",
                    style = AppTypography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetAllData()
                        showResetDialog = false
                    }
                ) {
                    Text(
                        text = "けす",
                        color = AppColors.wrong,
                        style = AppTypography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(
                        text = "キャンセル",
                        color = AppColors.textSecondary,
                        style = AppTypography.bodyMedium
                    )
                }
            }
        )
    }
}
