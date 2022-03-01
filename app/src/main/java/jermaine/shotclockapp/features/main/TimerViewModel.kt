package jermaine.shotclockapp.features.main

import android.util.Log
import androidx.lifecycle.ViewModel
import jermaine.shotclockapp.utils.INITIAL_TIME_14
import jermaine.shotclockapp.utils.INITIAL_TIME_24
import jermaine.shotclockapp.utils.PAGE_POSITION_TIMER_14
import jermaine.shotclockapp.utils.PAGE_POSITION_TIMER_24
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel : ViewModel() {

    private val _state by lazy {
        MutableStateFlow(TimerState.initialState())
    }

    val state: StateFlow<TimerState> = _state.asStateFlow()

    private var currentTime = 0
    private var maxTime = 0

    fun init(initialTime: Int) {
        Log.d(TAG, "init: $initialTime")
        currentTime = initialTime
        maxTime = initialTime
        _state.value = _state.value.copy(
            currentTime = initialTime
        )
    }

    fun resetTimer() {
        Log.d(TAG, "resetTimer: $maxTime")
        currentTime = maxTime
        _state.value = _state.value.copy(
            currentTime = currentTime
        )
    }

    fun decreaseTime() {
        if (currentTime <= 0) return

        currentTime -= 1

        _state.value = _state.value.copy(
            currentTime = currentTime
        )
    }

    fun increaseTime() {
        if (currentTime >= maxTime) return

        currentTime += 1

        _state.value = _state.value.copy(
            currentTime = currentTime
        )
    }

    fun onTick(time: Int) {
        Log.d(TAG, "onTick: $time")
        currentTime = time
        _state.value = _state.value.copy(
            currentTime = currentTime
        )
    }

    fun startTimer() {

    }

    companion object {
        private const val TAG = "TimerViewModel"
    }
}