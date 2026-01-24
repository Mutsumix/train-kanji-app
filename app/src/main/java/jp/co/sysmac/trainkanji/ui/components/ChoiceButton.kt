package jp.co.sysmac.trainkanji.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import jp.co.sysmac.trainkanji.ui.theme.AppColors
import jp.co.sysmac.trainkanji.ui.theme.AppShapes
import jp.co.sysmac.trainkanji.ui.theme.AppSpacing
import jp.co.sysmac.trainkanji.ui.theme.AppTypography

enum class ChoiceState {
    DEFAULT,
    CORRECT,
    WRONG,
    DISABLED
}

@Composable
fun ChoiceButton(
    text: String,
    state: ChoiceState = ChoiceState.DEFAULT,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (state) {
        ChoiceState.DEFAULT -> AppColors.accent
        ChoiceState.CORRECT -> AppColors.correct
        ChoiceState.WRONG -> AppColors.wrong
        ChoiceState.DISABLED -> AppColors.disabled
    }

    val textColor = when (state) {
        ChoiceState.DEFAULT -> AppColors.textPrimary
        ChoiceState.CORRECT, ChoiceState.WRONG -> AppColors.white
        ChoiceState.DISABLED -> AppColors.textMuted
    }

    val scale by animateFloatAsState(
        targetValue = if (state == ChoiceState.CORRECT) 1.05f else 1f,
        label = "choice_scale"
    )

    Button(
        onClick = onClick,
        enabled = state == ChoiceState.DEFAULT,
        modifier = modifier
            .fillMaxWidth()
            .scale(scale),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor
        ),
        shape = RoundedCornerShape(AppShapes.buttonRadius)
    ) {
        Text(
            text = text,
            style = AppTypography.choiceText,
            color = textColor,
            modifier = Modifier.padding(vertical = AppSpacing.md)
        )
    }
}
