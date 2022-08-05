package jermaine.shotclockapp.features.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import jermaine.shotclockapp.theme.DsDigi
import jermaine.shotclockapp.theme.LightColors
import jermaine.shotclockapp.utils.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Clock(
    modifier: Modifier,
    viewModel: ClockViewModel = viewModel()
) {
    val pagerState = rememberPagerState(1)
    val clockState = viewModel.clockState

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest {
                viewModel.onPageChange(it)
            }
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
        ) {
            val scope = rememberCoroutineScope()
            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier.testTag(TEST_TAG_PAGER)
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (page == 1) {
                        Clock(clockState.twentyFourClockTime)
                    } else {
                        Clock(clockState.fourteenClockTime)
                    }
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
        ClockButtons(
            modifier = Modifier.fillMaxSize(),
            play = clockState.play,
            onPlay = {
                viewModel.togglePlay()
            },
            onIncreaseTime = {
                viewModel.increaseTime()
            },
            onDecreaseTime = {
                viewModel.decreaseTime()
            },
            onResetTime = {
                viewModel.resetTimer()
            }
        )
    }
}

@Composable
private fun BoxScope.ShowClockPages(
    currentPage: Int,
    onClick: (index: Int) -> Unit
) {
    if (currentPage == PAGE_POSITION_TIMER_24) {
        ClockPage(
            text = INITIAL_TIME_14.toString(),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
                .testTag(TEST_TAG_CLOCK_PAGE_14)
        ) {
            onClick(0)
        }
    } else {
        ClockPage(
            text = INITIAL_TIME_24.toString(),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .testTag(TEST_TAG_CLOCK_PAGE_24)
        ) {
            onClick(1)
        }
    }
}

@Composable
private fun Clock(time: Int) {
    Text(
        text = time.toString(),
        fontFamily = DsDigi,
        fontSize = 200.sp
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
        color = MaterialTheme.colors.primaryVariant,
        modifier = modifier
            .clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewClockComponent() {
    MaterialTheme(colors = LightColors) {
        Clock(
            modifier = Modifier.fillMaxSize()
        )
    }
}
