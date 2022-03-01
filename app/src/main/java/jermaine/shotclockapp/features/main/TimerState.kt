package jermaine.shotclockapp.features.main


data class TimerState(
    val play: Boolean,
    val currentTime: Int
) {
    companion object {
        fun initialState(): TimerState {
            return TimerState(
                play = false,
                currentTime = 0
            )
        }
    }
}
