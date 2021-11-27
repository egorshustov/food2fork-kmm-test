package com.egorshustov.food2forkkmm.android.presentation.components

import androidx.compose.runtime.Composable
import com.egorshustov.food2forkkmm.domain.util.Queue
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo

@Composable
fun ProcessDialogQueue(dialogQueue: Queue<GenericMessageInfo>?) {
    dialogQueue?.peek()?.let { genericMessageInfo ->
        GenericDialog(title = genericMessageInfo.title, description = genericMessageInfo.description)
    }
}