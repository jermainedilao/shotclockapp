package jermaine.shotclockapp.features.theme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import jermaine.shotclockapp.BaseAndroidTest
import jermaine.shotclockapp.features.settings.Settings
import jermaine.shotclockapp.utils.TEST_TAG_BUTTON_THEME
import jermaine.shotclockapp.utils.TEST_TAG_POPUP_THEME

fun BaseAndroidTest.launchSettings(func: SettingsRobot.() -> Unit) =
    SettingsRobot(this).apply { func() }


class SettingsRobot(baseAndroidTest: BaseAndroidTest) {

    private val composeTestRule = baseAndroidTest.composeTestRule

    init {
        composeTestRule.setContent {
            Settings(navController = rememberNavController())
        }
    }

    fun clickThemeButton() {
        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON_THEME).performClick()
    }

    fun isThemePopupVisible() {
        composeTestRule.onNodeWithTag(TEST_TAG_POPUP_THEME).assertIsDisplayed()
    }

    fun isThemePopupNotVisible() {
        composeTestRule.onNodeWithTag(TEST_TAG_POPUP_THEME).assertDoesNotExist()
    }
}