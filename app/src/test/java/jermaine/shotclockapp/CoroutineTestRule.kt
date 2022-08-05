package jermaine.shotclockapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * References:
 * 1. https://medium.com/swlh/unit-testing-with-kotlin-coroutines-the-android-way-19289838d257
 * 2. https://gist.github.com/RBusarow/70256d782e2d789cfa167a0163b2e22e
 * 3. https://craigrussell.io/2019/11/unit-testing-coroutine-suspend-functions-using-testcoroutinedispatcher/
 */
@ExperimentalCoroutinesApi
class CoroutineTestRule : TestWatcher() {

    val testDispatcherProvider = TestDispatcherProvider()
    val testCoroutineDispatcher = testDispatcherProvider.testCoroutineDispatcher

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcherProvider.main())
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }
}