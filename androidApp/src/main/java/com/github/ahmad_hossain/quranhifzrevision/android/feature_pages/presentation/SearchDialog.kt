package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.DialogProperties
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource
import dev.icerock.moko.resources.StringResource

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    searchQuery: String,
    searchQueryError: StringResource?,
    onSearchQueryChanged: (String) -> Unit,
    onSearchDialogConfirmed: () -> Unit,
    onSearchDialogDismissed: () -> Unit,
) {
    if (!isVisible) return
    AlertDialog(
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onSearchDialogDismissed,
        confirmButton = {
            TextButton(
                onClick = onSearchDialogConfirmed,
                enabled = searchQueryError == null
            ) {
                Text(text = stringResource(SharedRes.strings.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onSearchDialogDismissed) {
                Text(text = stringResource(SharedRes.strings.cancel))
            }
        },
        icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
        title = { Text(text = stringResource(SharedRes.strings.jump_to_page)) },
        text = {
            val focusRequester = remember { FocusRequester() }
            val keyboardController = LocalSoftwareKeyboardController.current
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                label = { Text(text = stringResource(SharedRes.strings.page_number)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (searchQueryError == null)
                            onSearchDialogConfirmed()
                    }
                ),
                singleLine = true,
                isError = searchQueryError != null,
                supportingText = if (searchQueryError != null) {{ Text(stringResource(searchQueryError)) }} else null,
                trailingIcon = if (searchQueryError != null) {{ Icon(imageVector = Icons.Outlined.Error, contentDescription = null) }} else null,
            )
        }
    )
}