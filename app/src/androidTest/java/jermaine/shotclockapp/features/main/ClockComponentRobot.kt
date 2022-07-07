package jermaine.shotclockapp.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import jermaine.shotclockapp.BaseAndroidTest
import jermaine.shotclockapp.theme.ShotClockTheme
import jermaine.shotclockapp.utils.*

fun BaseAndroidTest.launchClockComponent(func: ClockComponentRobot.() -> Unit) =
    ClockComponentRobot(this).apply {
        func()
    }

class ClockComponentRobot(baseAndroidTest: BaseAndroidTest) {

    private val composeTestRule = baseAndroidTest.composeTestRule

    init {
        composeTestRule.setContent {
            ShotClockTheme {
                Clock(modifier = Modifier.fillMaxSize())
            }
        }
    }

    fun swipeLeft() {
        composeTestRule.onNodeWithTag(TEST_TAG_PAGER).performTouchInput {
            swipeLeft()
        }
    }

    fun swipeRight() {
        composeTestRule.onNodeWithTag(TEST_TAG_PAGER).performTouchInput {
            swipeRight()
        }
    }

    fun clickPlus1Button() {
        composeTestRule.onNodeWithText("+1").performClick()
    }

    fun clickMinus1Button() {
        composeTestRule.onNodeWithText("-1").performClick()
    }

    fun clickResetButton() {
        composeTestRule.onNodeWithTag(TEST_TAG_RESET).performClick()
    }

    fun clickClockPage14() {
        composeTestRule.onNodeWithTag(TEST_TAG_CLOCK_PAGE_14).performClick()
    }

    fun clickClockPage24() {
        composeTestRule.onNodeWithTag(TEST_TAG_CLOCK_PAGE_24).performClick()
    }

    fun clickPlayPauseButton() {
        composeTestRule.onNodeWithTag(TEST_TAG_PLAY_PAUSE).performClick()
    }

    infix fun isTimerEqualTo(value: Int) {
        composeTestRule.onNodeWithText(value.toString())
            .assertIsDisplayed()
    }

    fun isPauseButtonVisible() {
        composeTestRule.onNodeWithContentDescription("Pause")
            .assertIsDisplayed()
    }

    fun isPlayButtonVisible() {
        composeTestRule.onNodeWithContentDescription("Play")
            .assertIsDisplayed()
    }
}