package jermaine.shotclockapp.features.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import jermaine.shotclockapp.theme.ShotClockTheme
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@FlowPreview
@ExperimentalTime
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
        ClockComponent(Modifier.fillMaxSize())
    }
}

@FlowPreview
@ExperimentalTime
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    ShotClockTheme {
        Home()
    }
}