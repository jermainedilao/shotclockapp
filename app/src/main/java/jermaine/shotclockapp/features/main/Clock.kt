package jermaine.shotclockapp.features.main

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import jermaine.shotclockapp.theme.DsDigi
import jermaine.shotclockapp.theme.Grey
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun ClockComponent(modifier: Modifier) {
    val pagerState = rememberPagerState(1)
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        HorizontalPager(count = 2, state = pagerState) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("HOME", "Home: Column")
                Clock(
                    if (page == 1) {
                        "24"
                    } else {
                        "14"
                    }
                )
            }
        }
        if (!pagerState.isScrollInProgress) {
            ShowClockPages(pagerState.currentPage) {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun PreviewClockComponent() {
    ClockComponent(modifier = Modifier)
}

@ExperimentalPagerApi
@Composable
private fun BoxScope.ShowClockPages(
    currentPage: Int,
    onClick: (index: Int) -> Unit
) {
    if (currentPage == 1) {
        ClockPage(
            text = "14",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp),
        ) {
            onClick(0)
        }
    } else {
        ClockPage(
            text = "24",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        ) {
            onClick(1)
        }
    }
}

@Composable
private fun Clock(text: String) {
    Log.d("HOME", "Home: Clock")
    Text(
        text = text,
        fontFamily = DsDigi,
        fontSize = 200.sp,
        color = Color.Black
    )
}

@Composable
private fun ClockPage(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontFamily = DsDigi,
        fontSize = 44.sp,
        color = Grey,
        modifier = modifier
            .clickable(onClick = onClick)
    )
}