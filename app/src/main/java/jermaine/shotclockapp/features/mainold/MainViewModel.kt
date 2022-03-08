package jermaine.shotclockapp.features.mainold

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {
    private val _state by lazy {
        MutableStateFlow(MainState.initialState())
    }

    val state: StateFlow<MainState> = _state.asStateFlow()

    fun stop() {
        _state.value = _state.value.copy(
            play = false
        )
    }

    fun onPlayClick() {
        _state.value = _state.value.copy(
            play = !_state.value.play
        )
    }

    fun reset() {
        _state.value = _state.value.copy(
            events = _state.value.events + MainEvents.TimerEvent.Reset
        )
    }

    fun increaseTime() {
        _state.value = _state.value.copy(
            events = _state.value.events + MainEvents.TimerEvent.IncreaseTime
        )
    }

    fun decreaseTime() {
        _state.value = _state.value.copy(
            events = _state.value.events + MainEvents.TimerEvent.DecreaseTime
        )
    }

    fun eventConsumed(eventId: Long) {
        _state.value = _state.value.copy(
            events = _state.value.events.filterNot { it.eventId == eventId }
        )
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}