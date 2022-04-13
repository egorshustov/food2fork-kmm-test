package com.egorshustov.food2forkkmm.dispatchers

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

actual val ioDispatcher: CoroutineContext
    get() = Dispatchers.IO

actual val uiDispatcher: CoroutineContext
    get() = Dispatchers.Main