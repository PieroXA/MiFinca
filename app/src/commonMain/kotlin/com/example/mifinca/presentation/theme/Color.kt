package com.example.mifinca.presentation.theme

import androidx.compose.ui.graphics.Color

// ── Paleta principal ──────────────────────────────────────────────────────────
val RosePrimary       = Color(0xFFD15774) // color1 — rosa vibrante
val OrangeSecondary   = Color(0xFFD18E5A) // color2 — naranja cálido
val GoldAccent        = Color(0xFFD4C263) // color3 — dorado
val CreamAccent       = Color(0xFFDFDF9C) // color4 — crema
val MintSurface       = Color(0xFFE2EDDF) // color5 — verde muy claro (light surfaces)

// ── Variantes de primario ─────────────────────────────────────────────────────
val RosePrimaryDark   = Color(0xFFAA3A55) // más oscuro para pressed / dark theme
val RosePrimaryLight  = Color(0xFFE8889A) // más claro para containers

// ── Fondos y superficies ──────────────────────────────────────────────────────
val BackgroundDark    = Color(0xFF1A1014) // fondo oscuro cálido (tinte rosado)
val SurfaceDark       = Color(0xFF2A1C20) // surface dark — card oscura cálida
val SurfaceVariantDark= Color(0xFF3A272C) // surface variant para inputs

val BackgroundLight   = Color(0xFFFDF6F7) // fondo light — blanco con tinte rosa
val SurfaceLight      = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFFAEEF0)

// ── Texto ─────────────────────────────────────────────────────────────────────
val TextPrimaryDark   = Color(0xFFF5EAE0) // blanco cálido
val TextSecondaryDark = Color(0xFFB8A0A8) // gris rosado
val TextPrimaryLight  = Color(0xFF1A1014)
val TextSecondaryLight= Color(0xFF6B4F56)

// ── Semánticos ────────────────────────────────────────────────────────────────
val ErrorRed          = Color(0xFFEF4444)
val SuccessGreen      = Color(0xFF6BBF5E)
val WarningYellow     = GoldAccent

// ── Degradados (para uso directo en Brush) ────────────────────────────────────
val GradientStart     = Color(0xFFD15774)
val GradientMid       = Color(0xFFD18E5A)
val GradientEnd       = Color(0xFFD4C263)
