package jermaine.shotclockapp.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jermaine.shotclockapp.CoroutineTestRule
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ClockViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: ClockViewModel

    @Before
    fun setUp() {
        subject = ClockViewModel(coroutineTestRule.testDispatcherProvider)
    }

    @Test
    fun test24Timer() {
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            subject.togglePlay()

            advanceTimeBy(3000)

            assertEquals(21, subject.clockState.twentyFourClockTime)
        }
    }

    @Test
    fun test14Timer() {
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            subject.onPageChange(0)
            subject.togglePlay()

            advanceTimeBy(3000)

            assertEquals(11, subject.clockState.fourteenClockTime)
        }
    }

    @Test
    fun timer_ShouldResetTimeTo14_WhenTimerIsCompleted() {
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            subject.onPageChange(0)
            subject.togglePlay()

            advanceTimeBy(14000)

            assertEquals(14, subject.clockState.fourteenClockTime)
        }
    }

    @Test
    fun timer_ShouldResetTimeTo24_WhenTimerIsCompleted() {
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            subject.onPageChange(1)
            subject.togglePlay()

            advanceTimeBy(24000)

            assertEquals(24, subject.clockState.twentyFourClockTime)
        }
    }
}