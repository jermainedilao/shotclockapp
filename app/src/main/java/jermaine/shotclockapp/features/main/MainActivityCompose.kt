package jermaine.shotclockapp.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import jermaine.shotclockapp.theme.DarkColors
import jermaine.shotclockapp.theme.Gainsboro
import jermaine.shotclockapp.theme.LightColors
import jermaine.shotclockapp.utils.NAVIGATION_HOME

@ExperimentalPagerApi
class MainActivityCompose : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) {
                    DarkColors
                } else {
                    LightColors
                }
            ) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    if (isSystemInDarkTheme()) Gainsboro else Color.White
                )
                ShotClockApp()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun ShotClockApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAVIGATION_HOME) {
        composable(NAVIGATION_HOME) {
            Home()
        }
    }
}