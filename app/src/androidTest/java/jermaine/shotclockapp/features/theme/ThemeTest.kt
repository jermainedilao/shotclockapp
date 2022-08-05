package jermaine.shotclockapp.features.theme

import jermaine.shotclockapp.BaseAndroidTest
import org.junit.Test

class ThemeTest : BaseAndroidTest() {

    @Test
    fun testSettingsNavigation() {
        launchApp {
            clickSettings()
        }.isSettingsVisible()
    }
}