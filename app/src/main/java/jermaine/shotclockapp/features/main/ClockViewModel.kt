package jermaine.shotclockapp.features.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jermaine.shotclockapp.utils.*
import jermaine.shotclockapp.utils.exceptions.TimerCompleted
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ClockViewModel(
    private val dispatchers: BaseDispatcherProvider = DispatcherProvider
) : ViewModel() {
    var clockState by mutableStateOf(ClockState.defaultValue())
        private set

    private lateinit var timer: Job

    fun decreaseTime() {
        clockState = if (clockState.is24Timer) {
            if (clockState.twentyFourClockTime == 0) return
            clockState.copy(
                twentyFourClockTime = clockState.twentyFourClockTime - 1
            )
        } else {
            if (clockState.fourteenClockTime == 0) return
            clockState.copy(
                fourteenClockTime = clockState.fourteenClockTime - 1
            )
        }
    }

    fun increaseTime() {
        clockState = if (clockState.is24Timer) {
            if (clockState.twentyFourClockTime == INITIAL_TIME_24) return
            clockState.copy(
                twentyFourClockTime = clockState.twentyFourClockTime + 1
            )
        } else {
            if (clockState.fourteenClockTime == INITIAL_TIME_14) return
            clockState.copy(
                fourteenClockTime = clockState.fourteenClockTime + 1
            )
        }
    }

    fun resetTimer() {
        clockState = clockState.copy(
            twentyFourClockTime = INITIAL_TIME_24,
            fourteenClockTime = INITIAL_TIME_14
        )
    }

    fun togglePlay() {
        clockState = clockState.copy(
            play = !clockState.play
        )

        if (!clockState.play) {
            timer.cancel()
            return
        }

        timer = viewModelScope.launch {
            tickerFlow(1.seconds, 1.seconds)
                .flatMapConcat {
                    if (clockState.is24Timer) {
                        flowOf(clockState.twentyFourClockTime - 1)
                    } else {
                        flowOf(clockState.fourteenClockTime - 1)
                    }
                }
                .flowOn(dispatchers.default())
                .onEach { if (it <= 0) timer.cancel(TimerCompleted) }
                .onCompletion {
                    if (it is TimerCompleted) {
                        clockState = clockState.copy(
                            play = false,
                            twentyFourClockTime = INITIAL_TIME_24,
                            fourteenClockTime = INITIAL_TIME_14
                        )
                    }
                }
                .collect {
                    clockState = if (clockState.is24Timer) {
                        clockState.copy(
                            twentyFourClockTime = it
                        )
                    } else {
                        clockState.copy(
                            fourteenClockTime = it
                        )
                    }
                }
        }
    }

    fun onPageChange(page: Int) {
        clockState = clockState.copy(
            is24Timer = page == PAGE_POSITION_TIMER_24,
            twentyFourClockTime = INITIAL_TIME_24,
            fourteenClockTime = INITIAL_TIME_14
        )
    }
}