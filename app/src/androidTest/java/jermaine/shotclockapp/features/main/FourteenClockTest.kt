package jermaine.shotclockapp.features.main

import jermaine.shotclockapp.BaseAndroidTest
import org.junit.Test

class FourteenClockTest : BaseAndroidTest() {

    @Test
    fun testInitialState() {
        launchFourteenClockComponent { }
    }

    @Test
    fun testPlus1Button() {
        launchFourteenClockComponent {
            clickMinus1Button() // 13
            clickMinus1Button() // 12
            clickMinus1Button() // 11
            clickPlus1Button()
        } isTimerEqualTo 12
    }

    @Test
    fun plus1Button_ShouldNotIncrementTimer_WhenTimeIs14() {
        launchFourteenClockComponent {
            clickPlus1Button()
            isTimerEqualTo(14)
        }
    }

    @Test
    fun testMinus1Button() {
        launchFourteenClockComponent {
            clickMinus1Button() // 13
            clickMinus1Button() // 12
            clickMinus1Button() // 11
        } isTimerEqualTo 11
    }

    @Test
    fun minus1Button_ShouldNotDecreaseTimer_WhenTimeIs0() {
        launchFourteenClockComponent {
            for (i in 1..15) {
                clickMinus1Button()
            }
        } isTimerEqualTo 0
    }

    @Test
    fun testResetButton() {
        launchFourteenClockComponent {
            clickMinus1Button() // 13
            clickMinus1Button() // 12
            clickMinus1Button() // 11
            clickMinus1Button() // 10
            clickResetButton()
        } isTimerEqualTo 14
    }

    @Test
    fun testClockPage() {
        launchFourteenClockComponent {
            clickClockPage24()
        } isTimerEqualTo 24
    }

    @Test
    fun swipeLeft_ShouldShow24Timer() {
        launchFourteenClockComponent {
            swipeLeft()
        } isTimerEqualTo 24
    }

    private fun launchFourteenClockComponent(
        func: ClockComponentRobot.() -> Unit
    ): ClockComponentRobot = launchClockComponent {
        swipeRight()
        isTimerEqualTo(14)
        func()
    }
}