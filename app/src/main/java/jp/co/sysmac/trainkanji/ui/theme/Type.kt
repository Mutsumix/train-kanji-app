package jp.co.sysmac.trainkanji.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {
    // アプリタイトル
    val titleLarge = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )

    // 画面タイトル
    val titleMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )

    // セクションタイトル
    val titleSmall = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    // 漢字表示（超大）
    val kanjiDisplay = TextStyle(
        fontSize = 96.sp,
        fontWeight = FontWeight.Bold
    )

    // 選択肢
    val choiceText = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )

    // 本文
    val bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    )

    // 本文（小）
    val bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )

    // キャプション
    val caption = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
}
