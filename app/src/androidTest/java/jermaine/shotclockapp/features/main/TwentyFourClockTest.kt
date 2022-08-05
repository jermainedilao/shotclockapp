package jermaine.shotclockapp.features.main

import jermaine.shotclockapp.BaseAndroidTest
import org.junit.Test

class TwentyFourClockTest : BaseAndroidTest() {

    @Test
    fun testInitialState() {
        launchClockComponent {} isTimerEqualTo 24
    }

    @Test
    fun testPlus1Button() {
        launchClockComponent {
            clickMinus1Button() // 23
            clickMinus1Button() // 22
            clickMinus1Button() // 21
            clickPlus1Button()
        } isTimerEqualTo 22
    }

    @Test
    fun plus1Button_ShouldNotIncreaseTimer_WhenTimeIs24() {
        launchClockComponent {
            isTimerEqualTo(24)
            clickPlus1Button()
        } isTimerEqualTo 24
    }

    @Test
    fun testMinus1Button() {
        launchClockComponent {
            clickMinus1Button() // 23
            clickMinus1Button() // 22
            clickMinus1Button() // 21
        } isTimerEqualTo 21
    }

    @Test
    fun minus1Button_ShouldNotDecreaseTimer_WhenTimeIs0() {
        launchClockComponent {
            for (i in 1..25) {
                clickMinus1Button()
            }
        } isTimerEqualTo 0
    }

    @Test
    fun testResetButton() {
        launchClockComponent {
            clickMinus1Button() // 23
            clickMinus1Button() // 22
            clickMinus1Button() // 21
            clickMinus1Button() // 20
            clickResetButton()
        } isTimerEqualTo 24
    }

    @Test
    fun testClockPage() {
        launchClockComponent {
            clickClockPage14()
        } isTimerEqualTo 14
    }

    @Test
    fun swipeRight_ShouldShow14Timer() {
        launchClockComponent {
            swipeRight()
        } isTimerEqualTo 14
    }
}
