package com.example.mifinca.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary              = RosePrimary,
    onPrimary            = TextPrimaryDark,
    primaryContainer     = RosePrimaryDark,
    onPrimaryContainer   = RosePrimaryLight,
    secondary            = OrangeSecondary,
    onSecondary          = TextPrimaryDark,
    secondaryContainer   = Color(0xFF5A3020),
    onSecondaryContainer = CreamAccent,
    tertiary             = GoldAccent,
    onTertiary           = BackgroundDark,
    background           = BackgroundDark,
    onBackground         = TextPrimaryDark,
    surface              = SurfaceDark,
    onSurface            = TextPrimaryDark,
    surfaceVariant       = SurfaceVariantDark,
    onSurfaceVariant     = TextSecondaryDark,
    outline              = TextSecondaryDark,
    error                = ErrorRed,
    onError              = TextPrimaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary              = RosePrimary,
    onPrimary            = SurfaceLight,
    primaryContainer     = RosePrimaryLight,
    onPrimaryContainer   = RosePrimaryDark,
    secondary            = OrangeSecondary,
    onSecondary          = SurfaceLight,
    secondaryContainer   = CreamAccent,
    onSecondaryContainer = TextPrimaryLight,
    tertiary             = GoldAccent,
    onTertiary           = TextPrimaryLight,
    background           = BackgroundLight,
    onBackground         = TextPrimaryLight,
    surface              = SurfaceLight,
    onSurface            = TextPrimaryLight,
    surfaceVariant       = SurfaceVariantLight,
    onSurfaceVariant     = TextSecondaryLight,
    outline              = TextSecondaryLight,
    error                = ErrorRed,
    onError              = SurfaceLight,
)

@Composable
fun MiFincaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
