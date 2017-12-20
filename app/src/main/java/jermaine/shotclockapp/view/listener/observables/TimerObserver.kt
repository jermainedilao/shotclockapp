package jermaine.shotclockapp.view.listener.observables


interface TimerObserver {
    fun onPlus1()

    fun onMinus1()

    fun onReset()

    fun onTimePlay()

    fun onTimePause()
}