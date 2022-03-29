package jermaine.shotclockapp.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import jermaine.shotclockapp.features.preferences.ShotClockPreferences
import jermaine.shotclockapp.utils.ThemeType

@Composable
fun ShotClockTheme(content: @Composable () -> Unit) {
    val preferences = ShotClockPreferences(LocalContext.current)
    val theme = preferences.theme.collectAsState(initial = null)
    val systemUiController = rememberSystemUiController()

    if (theme.value != null) {
        MaterialTheme(
            colors = when (theme.value) {
                ThemeType.Dark -> {
                    systemUiController.setStatusBarColor(BakaraGrey)
                    DarkColors
                }
                else -> {
                    systemUiController.setStatusBarColor(Color.White)
                    LightColors
                }
            },
            content = content
        )
    }
}