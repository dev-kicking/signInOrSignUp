package dev.kick.signinorsignup.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DeepFineColorScheme = lightColorScheme(
    primary = DeepFineBlue,
    onPrimary = DeepFineWhite,
    background = DeepFineWhite,
    onBackground = DeepFineBlack,
    surface = DeepFineWhite,
    onSurface = DeepFineBlack,
    onSurfaceVariant = DeepFineGray,
    surfaceVariant = DeepFineLightGray,
    error = DeepFineError,
    onError = DeepFineWhite,
    outline = DeepFineLightGray,
    outlineVariant = DeepFineDisabled,
)

@Composable
fun DeepFineTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DeepFineColorScheme,
        typography = DeepFineTypography,
        content = content,
    )
}
