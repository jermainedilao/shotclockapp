package jermaine.shotclockapp.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import jermaine.shotclockapp.R
import jermaine.shotclockapp.theme.LightColors
import jermaine.shotclockapp.theme.ShotClockTheme
import jermaine.shotclockapp.utils.NAVIGATION_SETTINGS
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(NAVIGATION_SETTINGS)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings_default_24dp),
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) {
        ClockComponent(Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MaterialTheme(colors = LightColors) {
        Home(navController = rememberNavController())
    }
}