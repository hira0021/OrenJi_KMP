package com.example.orenji.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Brand colors ──
val OrangeP      = Color(0xFFFF6B2B)  // primary
val OrangePLight = Color(0xFFFF8C5A)  // primary container
val OrangePDark  = Color(0xFFCC5422)  // on buttons pressed
val OrangeSurface= Color(0xFFFFF0E8)  // surface tint

// ── Neutral ──
val TextPrimary   = Color(0xFF1A1A1A)
val TextSecondary = Color(0xFF757575)
val Background    = Color(0xFFF5F5F5)
val SurfaceWhite  = Color(0xFFFFFFFF)

// ── Semantic ──
val SuccessGreen  = Color(0xFF2E7D32)
val ErrorRed      = Color(0xFFC62828)
val WarningAmber  = Color(0xFFF57F17)

private val OrenJiColorScheme = lightColorScheme(
    primary             = OrangeP,
    onPrimary           = Color.White,
    primaryContainer    = OrangePLight,
    onPrimaryContainer  = OrangePDark,
    secondary           = OrangePDark,
    onSecondary         = Color.White,
    background          = Background,
    onBackground        = TextPrimary,
    surface             = SurfaceWhite,
    onSurface           = TextPrimary,
    onSurfaceVariant    = TextSecondary,
    error               = ErrorRed,
    onError             = Color.White,
)

@Composable
fun OrenJiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OrenJiColorScheme,
        typography = OrenJiTypography,
        content = content,
    )
}