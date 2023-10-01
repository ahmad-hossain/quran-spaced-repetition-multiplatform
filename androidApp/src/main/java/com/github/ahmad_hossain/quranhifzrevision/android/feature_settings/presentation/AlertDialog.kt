package com.github.ahmad_hossain.quranhifzrevision.android.feature_settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

private val DialogMinWidth = 280.dp
private val DialogMaxWidth = 560.dp

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Box(
            modifier = modifier
                .sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth)
                .then(Modifier.semantics { paneTitle = "Dialog" }),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}