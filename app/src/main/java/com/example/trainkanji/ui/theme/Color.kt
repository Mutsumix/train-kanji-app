package com.example.trainkanji.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    // 背景グラデーション
    val backgroundGradientStart = Color(0xFFBAE6FD)  // sky-200
    val backgroundGradientEnd = Color(0xFF38BDF8)    // sky-400

    // メインカラー
    val primary = Color(0xFF3B82F6)        // blue-500
    val primaryDark = Color(0xFF2563EB)    // blue-600

    // アクセントカラー
    val accent = Color(0xFFFACC15)         // yellow-400
    val accentDark = Color(0xFFEAB308)     // yellow-500

    // ステータスカラー
    val correct = Color(0xFF22C55E)        // green-500
    val correctBg = Color(0xFFDCFCE7)      // green-100
    val wrong = Color(0xFFEF4444)          // red-500
    val wrongBg = Color(0xFFFEE2E2)        // red-100

    // ヒント
    val hintBg = Color(0xFFFFF7ED)         // orange-50
    val hintBorder = Color(0xFFFED7AA)     // orange-200
    val hintText = Color(0xFFEA580C)       // orange-600

    // その他
    val white = Color(0xFFFFFFFF)
    val cardBg = Color(0xFFFFFFFF)
    val textPrimary = Color(0xFF1F2937)    // gray-800
    val textSecondary = Color(0xFF6B7280)  // gray-500
    val textMuted = Color(0xFF9CA3AF)      // gray-400
    val disabled = Color(0xFFE5E7EB)       // gray-200

    // 漢字表示
    val kanjiBoxBg = Color(0xFFFEF9C3)     // yellow-100
    val kanjiBoxBorder = Color(0xFFFACC15) // yellow-400
    val kanjiHighlight = Color(0xFFFEF08A) // yellow-200
}
