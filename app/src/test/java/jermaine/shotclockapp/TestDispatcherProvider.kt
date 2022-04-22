package jermaine.shotclockapp

import jermaine.shotclockapp.utils.BaseDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    BaseDispatcherProvider {
    override fun default() = testCoroutineDispatcher

    override fun io() = testCoroutineDispatcher

    override fun unconfined() = testCoroutineDispatcher

    override fun main() = testCoroutineDispatcher
}