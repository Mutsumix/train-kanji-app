package jp.co.sysmac.trainkanji.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import jp.co.sysmac.trainkanji.ui.theme.AppColors
import jp.co.sysmac.trainkanji.ui.theme.AppShapes
import jp.co.sysmac.trainkanji.ui.theme.AppSpacing
import jp.co.sysmac.trainkanji.ui.theme.AppTypography

@Composable
fun KanjiBox(
    kanji: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTypography.kanjiDisplay
) {
    Box(
        modifier = modifier
            .background(
                color = AppColors.kanjiBoxBg,
                shape = RoundedCornerShape(AppShapes.kanjiBoxRadius)
            )
            .border(
                width = 4.dp,
                color = AppColors.kanjiBoxBorder,
                shape = RoundedCornerShape(AppShapes.kanjiBoxRadius)
            )
            .padding(AppSpacing.md),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = kanji,
            style = textStyle,
            color = AppColors.textPrimary
        )
    }
}
