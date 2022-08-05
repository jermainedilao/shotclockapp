package jermaine.shotclockapp.features.theme

import jermaine.shotclockapp.BaseAndroidTest
import org.junit.Test

class SettingsTest : BaseAndroidTest() {

    @Test
    fun testInitialState() {
        launchSettings { }.isThemePopupNotVisible()
    }

    @Test
    fun testThemeButton() {
        launchSettings {
            clickThemeButton()
        }.isThemePopupVisible()
    }
}