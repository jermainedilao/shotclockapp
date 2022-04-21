package jermaine.shotclockapp

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

abstract class BaseAndroidTest {
    @get:Rule
    val composeTestRule = createComposeRule()
//
//    @get:Rule
//    val coroutineTestRule = CoroutineTestRule()
}