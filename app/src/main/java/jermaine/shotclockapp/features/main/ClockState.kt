package jermaine.shotclockapp.features.main

import jermaine.shotclockapp.utils.INITIAL_TIME_14
import jermaine.shotclockapp.utils.INITIAL_TIME_24


data class ClockState(
    val is24Timer: Boolean,
    val play: Boolean,
    val twentyFourClockTime: Int,
    val fourteenClockTime: Int,
) {
    companion object {
        fun defaultValue() = ClockState(
            is24Timer = true,
            play = false,
            twentyFourClockTime = INITIAL_TIME_24,
            fourteenClockTime = INITIAL_TIME_14
        )
    }
}
