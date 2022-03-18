package jermaine.shotclockapp.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ShotClockTheme(content: @Composable () -> Unit) {
    MaterialTheme(
//        colors = if (isSystemInDarkTheme()) {
//            DarkColors
//        } else {
//            LightColors
//        },
        colors = LightColors,
        content = content
    )
}