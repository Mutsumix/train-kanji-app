package com.example.trainkanji.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.trainkanji.ui.theme.AppColors
import com.example.trainkanji.ui.theme.AppTypography

@Composable
fun SentenceWithHighlight(
    sentence: String,
    highlightKanji: String,
    modifier: Modifier = Modifier
) {
    val parts = sentence.split(highlightKanji)

    Text(
        text = buildAnnotatedString {
            parts.forEachIndexed { index, part ->
                append(part)
                if (index < parts.size - 1) {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = AppColors.primary,
                            background = AppColors.kanjiHighlight
                        )
                    ) {
                        append(highlightKanji)
                    }
                }
            }
        },
        style = AppTypography.bodyLarge,
        modifier = modifier
    )
}
