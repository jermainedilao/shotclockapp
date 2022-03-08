package jermaine.shotclockapp.features.mainold


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
