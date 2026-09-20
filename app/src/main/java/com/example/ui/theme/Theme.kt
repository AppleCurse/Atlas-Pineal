package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AtlasDarkColorScheme = darkColorScheme(
    primary = BrassGold,
    onPrimary = ObsidianBg,
    primaryContainer = BronzePlate,
    onPrimaryContainer = AntiqueGold,
    secondary = PhosphorGreen,
    onSecondary = ObsidianBg,
    secondaryContainer = DarkPhosphor,
    onSecondaryContainer = PhosphorGlow,
    tertiary = NixieAmber,
    onTertiary = ObsidianBg,
    background = ObsidianBg,
    onBackground = TextPrimary,
    surface = ConsoleSurface,
    onSurface = TextPrimary,
    surfaceVariant = ConsoleSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = ConsoleBorderBrass,
    outlineVariant = ConsoleBorder,
    error = AshRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AtlasDarkColorScheme,
        typography = Typography,
        content = content
    )
}
