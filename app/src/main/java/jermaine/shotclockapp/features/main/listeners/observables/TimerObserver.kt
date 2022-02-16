package jermaine.shotclockapp.features.main.listeners.observables


interface TimerObserver {
    fun onPlus1()

    fun onMinus1()

    fun onReset()

    fun onTimePlay()

    fun onTimePause()
}