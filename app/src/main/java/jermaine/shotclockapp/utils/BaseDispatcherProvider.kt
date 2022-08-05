package jermaine.shotclockapp.utils

import kotlinx.coroutines.CoroutineDispatcher

interface BaseDispatcherProvider {
    fun default(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun unconfined(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}
