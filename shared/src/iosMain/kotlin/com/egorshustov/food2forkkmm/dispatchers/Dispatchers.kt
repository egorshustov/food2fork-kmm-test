package com.egorshustov.food2forkkmm.dispatchers

import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue

actual val ioDispatcher: CoroutineContext
    get() = MainDispatcher

actual val uiDispatcher: CoroutineContext
    get() = MainDispatcher

@ThreadLocal
private object MainDispatcher : CoroutineDispatcher() {

    @OptIn(ExperimentalStdlibApi::class)
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val isExperimentalMM = isExperimentalMM()
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run().freeze()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}


@ThreadLocal
private object IODispatcher : CoroutineDispatcher() {

    @OptIn(ExperimentalStdlibApi::class)
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val isExperimentalMM = isExperimentalMM()
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0.toULong())) {
            try {
                block.run().freeze()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}