package jermaine.shotclockapp.features.theme

import androidx.compose.ui.test.*
import jermaine.shotclockapp.BaseAndroidTest
import jermaine.shotclockapp.features.main.ShotClockApp
import jermaine.shotclockapp.theme.ShotClockTheme
import jermaine.shotclockapp.utils.*

fun BaseAndroidTest.launchApp(func: ThemeTestRobot.() -> Unit) =
    ThemeTestRobot(this).apply {
        func()
    }

class ThemeTestRobot(baseAndroidTest: BaseAndroidTest) {

    private val composeTestRule = baseAndroidTest.composeTestRule

    init {
        composeTestRule.setContent {
            ShotClockTheme {
                ShotClockApp()
            }
        }
    }

    fun clickSettings() {
        composeTestRule.onNodeWithTag(TEST_TAG_SETTINGS).performClick()
    }

    fun selectDarkTheme() {
        composeTestRule.onNodeWithText(TEST_TAG_THEME_DARK).performClick()
    }

    fun selectLightTheme() {
        composeTestRule.onNodeWithText(TEST_TAG_THEME_LIGHT).performClick()
    }

    fun navigateBackFromSettings() {
        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON_BACK).performClick()
    }

    fun isThemeDark() {
        
    }

    fun isThemeLight() {

    }

    fun isSettingsVisible() {
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON_THEME).assertIsDisplayed()
    }
}