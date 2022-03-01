package jermaine.shotclockapp.features.main


data class TimerState(
    val currentTime: Int
) {
    companion object {
        fun initialState(): TimerState {
            return TimerState(
                currentTime = 0
            )
        }
    }
}
