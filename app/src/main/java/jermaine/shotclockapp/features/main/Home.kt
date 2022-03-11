package jermaine.shotclockapp.features.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import jermaine.shotclockapp.theme.ShotClockTheme

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
            ClockButtons(
                modifier = Modifier.fillMaxSize(),
                onPlay = {
                    Log.d("Home", "Home: onPlay")
                },
                onIncreaseTime = {
                    Log.d("Home", "Home: onIncreaseTime")
                },
                onDecreaseTime = {
                    Log.d("Home", "Home: onDecreaseTime")
                },
                onResetTime = {
                    Log.d("Home", "Home: onResetTime")
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    ShotClockTheme {
        Home()
    }
}