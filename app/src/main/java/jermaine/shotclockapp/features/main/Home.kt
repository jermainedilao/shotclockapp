package jermaine.shotclockapp.features.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import jermaine.shotclockapp.theme.DarkColors
import jermaine.shotclockapp.theme.LightColors

@ExperimentalPagerApi
@Composable
fun Home() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {}
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ClockComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.7f)
            )
            ClockButtons(modifier = Modifier.fillMaxSize())
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) {
            DarkColors
        } else {
            LightColors
        }
    ) {
        Home()
    }
}