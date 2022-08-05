package jermaine.shotclockapp.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Grey = Color(0x11000000)
val DarkWhite = Color(0xFFD8D8D8)
val Pink = Color(0xFFFF4081)
val PinkVibrant = Color(0xFFF9008B)
val BakaraGrey = Color(0xFF191414)
val Gainsboro = Color(0x1AD8D8D8)
val VeryLightGrey = Color(0xFFCCCCCC)

@SuppressLint("ConflictingOnColor")
val LightColors = lightColors(
    primary = Color.White,
    primaryVariant = DarkWhite,
    secondary = Pink,
    onPrimary = Color.Black,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@SuppressLint("ConflictingOnColor")
val DarkColors = darkColors(
    primary = BakaraGrey,
    primaryVariant = Gainsboro,
    secondary = Pink,
    onPrimary = Pink,
    background = BakaraGrey,
    surface = BakaraGrey,
    onBackground = Pink,
    onSurface = Pink
)