package com.egorshustov.food2forkkmm.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.egorshustov.food2forkkmm.android.presentation.components.CircularIndeterminateProgressBar
import com.egorshustov.food2forkkmm.android.presentation.components.ProcessDialogQueue
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.Queue

private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2
)

@Composable
fun AppTheme(
    displayProgressBar: Boolean,
    messageQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    onRemoveHeadMessageFromQueue: () -> Unit,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey1)
        ) {
            ProcessDialogQueue(
                dialogQueue = messageQueue,
                onRemoveHeadMessageFromQueue = onRemoveHeadMessageFromQueue
            )
            content()
            CircularIndeterminateProgressBar(
                isDisplayed = displayProgressBar,
                verticalBias = 0.3f
            )
        }
    }
}