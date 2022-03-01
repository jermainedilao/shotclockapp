package jermaine.shotclockapp.features.main

sealed class MainEvent {

    object NavigateToSettings : MainEvent()

    sealed class TimerEvent : MainEvent() {
        object Reset : TimerEvent()

        object IncreaseTime : TimerEvent()

        object DecreaseTime : TimerEvent()
    }
}