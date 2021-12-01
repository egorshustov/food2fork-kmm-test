package com.egorshustov.food2forkkmm.android.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egorshustov.food2forkkmm.presentation.model.NegativeAction
import com.egorshustov.food2forkkmm.presentation.model.PositiveAction

@Composable
fun GenericDialog(
    title: String,
    onRemoveHeadMessageFromQueue: (() -> Unit),
    modifier: Modifier = Modifier,
    description: String? = null,
    positiveAction: PositiveAction? = null,
    negativeAction: NegativeAction? = null,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismiss?.invoke()
            onRemoveHeadMessageFromQueue()
        },
        text = {
            description?.let {
                Text(text = it, style = MaterialTheme.typography.body1)
            }
        },
        title = {
            Text(text = title, style = MaterialTheme.typography.h3)
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (negativeAction != null) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                        onClick = {
                            negativeAction.onNegativeAction()
                            onRemoveHeadMessageFromQueue()
                        }
                    ) {
                        Text(
                            text = negativeAction.negativeBtnTxt,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
                if (positiveAction != null) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            positiveAction.onPositiveAction()
                            onRemoveHeadMessageFromQueue()
                        }
                    ) {
                        Text(
                            text = positiveAction.positiveBtnTxt,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    )
}