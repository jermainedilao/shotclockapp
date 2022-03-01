package jermaine.shotclockapp.features.main

import java.util.*

data class MainState(
    val play: Boolean,
    val events: List<MainEvents>
) {
    companion object {
        fun initialState() = MainState(
            play = false,
            events = emptyList()
        )
    }
}

sealed class MainEvents(val eventId: Long = UUID.randomUUID().mostSignificantBits) {
    sealed class TimerEvent : MainEvents() {
        object Reset : TimerEvent()

        object IncreaseTime : TimerEvent()

        object DecreaseTime : TimerEvent()
    }
}