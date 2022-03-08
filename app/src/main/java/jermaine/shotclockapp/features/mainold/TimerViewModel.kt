package jermaine.shotclockapp.features.mainold

import androidx.lifecycle.ViewModel
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
        currentTime = initialTime
        maxTime = initialTime
        _state.value = _state.value.copy(
            currentTime = initialTime
        )
    }

    fun resetTimer() {
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
        currentTime = time
        _state.value = _state.value.copy(
            currentTime = currentTime
        )
    }

    companion object {
        private const val TAG = "TimerViewModel"
    }
}