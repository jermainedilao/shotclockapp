package jermaine.shotclockapp.features.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {
    private val _events by lazy {
        MutableSharedFlow<MainEvent>(replay = 1)
    }

    val events: SharedFlow<MainEvent> = _events.asSharedFlow()

    private val _playState by lazy {
        MutableStateFlow(false)
    }

    val playState: StateFlow<Boolean> = _playState.asStateFlow()

    fun stop() {
        _playState.value = false
    }

    fun onPlayClick() {
        _playState.value = !_playState.value
    }

    fun reset() {
        _events.tryEmit(MainEvent.TimerEvent.Reset)
    }

    fun increaseTime() {
        _events.tryEmit(MainEvent.TimerEvent.IncreaseTime)
    }

    fun decreaseTime() {
        _events.tryEmit(MainEvent.TimerEvent.DecreaseTime)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}