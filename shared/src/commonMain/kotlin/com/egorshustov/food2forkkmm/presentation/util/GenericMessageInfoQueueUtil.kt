package com.egorshustov.food2forkkmm.presentation.util

import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.Queue

/**
 * Normally I would just make an extension function but KMP cannot use extension functions yet
 */
object GenericMessageInfoQueueUtil {

    fun doesMessageAlreadyExistInQueue(
        queue: Queue<GenericMessageInfo>,
        messageInfo: GenericMessageInfo
    ): Boolean = messageInfo.id in queue.items.map { it.id }
}