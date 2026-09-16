package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Prompt2Video Theme Palette
val Surface = Color(0xFF0F131C)
val SurfaceDim = Color(0xFF0F131C)
val SurfaceBright = Color(0xFF353942)
val SurfaceContainerLowest = Color(0xFF0A0E16)
val SurfaceContainerLow = Color(0xFF181C24)
val SurfaceContainer = Color(0xFF1C2028)
val SurfaceContainerHigh = Color(0xFF262A33)
val SurfaceContainerHighest = Color(0xFF31353E)

val OnSurface = Color(0xFFDFE2EE)
val OnSurfaceVariant = Color(0xFFC7C4D7)
val Outline = Color(0xFF908FA0)
val OutlineVariant = Color(0xFF464554)

val Primary = Color(0xFFC0C1FF)
val OnPrimary = Color(0xFF1000A9)
val PrimaryContainer = Color(0xFF8083FF)
val OnPrimaryContainer = Color(0xFF0D0096)
val InversePrimary = Color(0xFF494BD6)

val Secondary = Color(0xFFD0BCFF)
val OnSecondary = Color(0xFF3C0091)
val SecondaryContainer = Color(0xFF571BC1)
val OnSecondaryContainer = Color(0xFFC4ABFF)
val SecondaryFixedDim = Color(0xFFD0BCFF)

val Tertiary = Color(0xFF4CD7F6)
val OnTertiary = Color(0xFF003640)
val TertiaryContainer = Color(0xFF009EB9)
val OnTertiaryContainer = Color(0xFF002F38)

val Error = Color(0xFFFFB4AB)
val OnError = Color(0xFF690005)
val ErrorContainer = Color(0xFF93000A)
val OnErrorContainer = Color(0xFFFFDAD6)

// Gradients
val PrimaryGradient = Brush.horizontalGradient(
    listOf(PrimaryContainer, SecondaryContainer)
)

val HeroCtaGradient = Brush.horizontalGradient(
    listOf(Color(0xFF494BD6), PrimaryContainer, Secondary)
)

val AccentCyanPurpleGradient = Brush.horizontalGradient(
    listOf(Tertiary, PrimaryContainer, Secondary)
)
