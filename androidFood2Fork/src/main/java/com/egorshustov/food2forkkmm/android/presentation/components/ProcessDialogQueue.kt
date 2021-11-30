package com.egorshustov.food2forkkmm.android.presentation.components

import androidx.compose.runtime.Composable
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.Queue

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericMessageInfo>?,
    onRemoveHeadMessageFromQueue: () -> Unit
) {
    dialogQueue?.peek()?.let { genericMessageInfo ->
        GenericDialog(
            title = genericMessageInfo.title,
            description = genericMessageInfo.description,
            onRemoveHeadMessageFromQueue = onRemoveHeadMessageFromQueue
        )
    }
}