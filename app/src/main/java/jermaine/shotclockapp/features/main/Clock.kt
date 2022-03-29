package jermaine.shotclockapp.features.main

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import jermaine.shotclockapp.features.mainold.cancellation.TimerCompleted
import jermaine.shotclockapp.theme.DsDigi
import jermaine.shotclockapp.theme.LightColors
import jermaine.shotclockapp.utils.INITIAL_TIME_14
import jermaine.shotclockapp.utils.INITIAL_TIME_24
import jermaine.shotclockapp.utils.tickerFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalPagerApi
class ClockState(
    val pagerState: PagerState,
    val playState: MutableState<Boolean>,
    val twentyFourTimeState: MutableState<Int>,
    val fourteenTimeState: MutableState<Int>
) {
    val currentPage get() = pagerState.currentPage
    val play get() = playState.value
    val twentyFourTime get() = twentyFourTimeState.value
    val fourteenTime get() = fourteenTimeState.value

    fun togglePlay() {
        playState.value = !playState.value
    }

    fun stop() {
        playState.value = false
    }

    fun resetTimers() {
        twentyFourTimeState.value = INITIAL_TIME_24
        fourteenTimeState.value = INITIAL_TIME_14
    }

    fun increaseTime() {
        if (currentPage == 1) {
            if (twentyFourTime < INITIAL_TIME_24) twentyFourTimeState.value += 1
        } else {
            if (fourteenTime < INITIAL_TIME_14) fourteenTimeState.value += 1
        }
    }

    fun decreaseTime() {
        if (currentPage == 1) {
            if (twentyFourTime > 0) twentyFourTimeState.value -= 1
        } else {
            if (fourteenTime > 0) fourteenTimeState.value -= 1
        }
    }
}

@ExperimentalPagerApi
@Composable
fun rememberClockState(
    pagerState: PagerState = rememberPagerState(1),
    playState: MutableState<Boolean> = remember { mutableStateOf(false) },
    twentyFourTimeState: MutableState<Int> = remember { mutableStateOf(INITIAL_TIME_24) },
    fourteenTimeState: MutableState<Int> = remember { mutableStateOf(INITIAL_TIME_14) }
) = remember {
    Log.d("HOME", "rememberClockState: ")
    ClockState(
        pagerState = pagerState,
        playState = playState,
        twentyFourTimeState = twentyFourTimeState,
        fourteenTimeState = fourteenTimeState
    )
}

@FlowPreview
@ExperimentalTime
@ExperimentalPagerApi
@Composable
fun ClockComponent(modifier: Modifier) {
    Log.d("HOME", "Home: ClockComponent")
    val clockState = rememberClockState()

    LaunchedEffect(clockState.pagerState) {
        snapshotFlow { clockState.currentPage }
            .collectLatest {
                clockState.resetTimers()
            }
    }

    if (clockState.play) CountdownTimer(clockState)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
        ) {
            Log.d("HOME", "Home: Box")
            val scope = rememberCoroutineScope()
            HorizontalPager(count = 2, state = clockState.pagerState) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("HOME", "Home: Column")
                    if (page == 1) {
                        Clock(clockState.twentyFourTime)
                    } else {
                        Clock(clockState.fourteenTime)
                    }
                }
            }
            if (!clockState.pagerState.isScrollInProgress) {
                ShowClockPages(clockState.currentPage) {
                    scope.launch {
                        clockState.pagerState.animateScrollToPage(it)
                    }
                }
            }
        }
        ClockButtons(
            modifier = Modifier.fillMaxSize(),
            play = clockState.play,
            onPlay = {
                clockState.togglePlay()
            },
            onIncreaseTime = {
                clockState.increaseTime()
            },
            onDecreaseTime = {
                clockState.decreaseTime()
            },
            onResetTime = {
                clockState.resetTimers()
            }
        )
    }
}

@FlowPreview
@ExperimentalPagerApi
@Composable
private fun CountdownTimer(clockState: ClockState) {
    var timer: Job? = null
    LaunchedEffect(clockState.play) {
        timer = launch {
            tickerFlow(1.seconds, 1.seconds)
                .flatMapConcat {
                    if (clockState.currentPage == 1) {
                        flowOf(clockState.twentyFourTime - 1)
                    } else {
                        flowOf(clockState.fourteenTime - 1)
                    }
                }
                .flowOn(Dispatchers.Default)
                .onEach {
                    if (it <= 0) {
                        timer?.cancel(TimerCompleted)
                    }
                }
                .onCompletion {
                    if (it is TimerCompleted) {
                        clockState.stop()
                        clockState.resetTimers()
                    }
                }
                .collect {
                    if (clockState.currentPage == 1) {
                        clockState.twentyFourTimeState.value = it
                    } else {
                        clockState.fourteenTimeState.value = it
                    }
                }
        }
    }
}

@FlowPreview
@ExperimentalTime
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun PreviewClockComponent() {
    MaterialTheme(colors = LightColors) {
        ClockComponent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@ExperimentalPagerApi
@Composable
private fun BoxScope.ShowClockPages(
    currentPage: Int,
    onClick: (index: Int) -> Unit
) {
    if (currentPage == 1) {
        ClockPage(
            text = INITIAL_TIME_14.toString(),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp),
        ) {
            onClick(0)
        }
    } else {
        ClockPage(
            text = INITIAL_TIME_24.toString(),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        ) {
            onClick(1)
        }
    }
}

@Composable
private fun Clock(time: Int) {
    Log.d("HOME", "Home: Clock")
    Text(
        text = time.toString(),
        fontFamily = DsDigi,
        fontSize = 200.sp,
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