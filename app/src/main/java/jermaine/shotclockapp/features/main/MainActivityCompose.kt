package jermaine.shotclockapp.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import jermaine.shotclockapp.features.settings.Settings
import jermaine.shotclockapp.theme.ShotClockTheme
import jermaine.shotclockapp.utils.NAVIGATION_HOME
import jermaine.shotclockapp.utils.NAVIGATION_SETTINGS
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@FlowPreview
@ExperimentalTime
@ExperimentalPagerApi
class MainActivityCompose : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShotClockTheme {
                ShotClockApp()
            }
        }
    }
}

@FlowPreview
@ExperimentalTime
@ExperimentalPagerApi
@Composable
fun ShotClockApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAVIGATION_HOME) {
        composable(NAVIGATION_HOME) {
            Home(navController)
        }
        composable(NAVIGATION_SETTINGS) {
            Settings(navController)
        }
    }
}