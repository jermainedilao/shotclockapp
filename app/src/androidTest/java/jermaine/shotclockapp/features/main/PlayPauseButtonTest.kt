package jermaine.shotclockapp.features.main

import jermaine.shotclockapp.BaseAndroidTest
import org.junit.Test

class PlayPauseButtonTest : BaseAndroidTest() {

    @Test
    fun testPlayButtonIcon() {
        launchClockComponent {
            clickPlayPauseButton()
        }.isPauseButtonVisible()
    }

    @Test
    fun testPauseButtonIcon() {
        launchClockComponent {
            clickPlayPauseButton()
            clickPlayPauseButton()
        }.isPlayButtonVisible()
    }
}