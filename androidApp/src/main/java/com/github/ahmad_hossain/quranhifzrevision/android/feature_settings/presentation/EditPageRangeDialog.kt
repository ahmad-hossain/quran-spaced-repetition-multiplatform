package com.github.ahmad_hossain.quranhifzrevision.android.feature_settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource
import dev.icerock.moko.resources.StringResource

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditPageRangeDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    startPage: String,
    endPage: String,
    startPageError: StringResource?,
    endPageError: StringResource?,
    onStartPageChanged: (String) -> Unit,
    onEndPageChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    if (!isVisible) return
    val noTextErrors = startPageError == null && endPageError == null
    AlertDialog(
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmClicked,
                enabled = noTextErrors
            ) {
                Text(text = stringResource(SharedRes.strings.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(SharedRes.strings.cancel))
            }
        },
        icon = { Icon(imageVector = Icons.Outlined.Edit, contentDescription = null) },
        title = { Text(stringResource(SharedRes.strings.edit_start_and_end_page)) },
        text = {
            val focusManager = LocalFocusManager.current
            val focusRequester = remember { FocusRequester() }
            val keyboardController = LocalSoftwareKeyboardController.current
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            Column(Modifier.width(IntrinsicSize.Min)) {
                OutlinedTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = startPage,
                    onValueChange = onStartPageChanged,
                    label = { Text(stringResource(SharedRes.strings.start_page)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    isError = startPageError != null,
                    supportingText = if (startPageError != null) { { Text(stringResource(startPageError)) } } else null,
                    trailingIcon = if (startPageError != null) { { Icon(imageVector = Icons.Outlined.Error, contentDescription = null) } } else null,
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = endPage,
                    onValueChange = onEndPageChanged,
                    label = { Text(stringResource(SharedRes.strings.end_page)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            if (noTextErrors)
                                onConfirmClicked()
                        }
                    ),
                    singleLine = true,
                    isError = endPageError != null,
                    supportingText = if (endPageError != null) { { Text(stringResource(endPageError)) } } else null,
                    trailingIcon = if (endPageError != null) { { Icon(imageVector = Icons.Outlined.Error, contentDescription = null) } } else null,
                )
                Spacer(Modifier.height(16.dp))
                RevisionResetWarning()
            }
        }
    )
}

@Composable
private fun RevisionResetWarning() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.width(8.dp))
        Text(
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold,
            text = stringResource(SharedRes.strings.revision_data_reset_warning)
        )
    }
}

@Preview
@Composable
fun PreviewEditPageRangeDialog() {
    EditPageRangeDialog(
        isVisible = true,
        startPage = "1",
        endPage = "2",
        startPageError = null,
        endPageError = null,
        onStartPageChanged = { },
        onEndPageChanged = { },
        onDismissRequest = { },
        onConfirmClicked = { },
    )
}