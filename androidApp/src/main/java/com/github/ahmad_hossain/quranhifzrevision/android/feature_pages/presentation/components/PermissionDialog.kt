package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.R
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource

@Composable
fun PermissionDialog(
    icon: Painter,
    permission: String,
    text: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirmClicked: () -> Unit,
    onDismissed: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissed,
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = getFormattedTitle(permission),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                textAlign = TextAlign.Center
            )
        },
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = onConfirmClicked) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissed) {
                Text(dismissButtonText)
            }
        },
    )
}

@Composable
private fun getFormattedTitle(permission: String): AnnotatedString {
    val appName = stringResource(SharedRes.strings.app_name)
    val title = stringResource(SharedRes.strings.allow_entity_permission, appName, permission)

    val start = title.indexOf(appName)
    val spanStyles = listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.Bold),
            start = start,
            end = start + appName.length
        )
    )

    return AnnotatedString(text = title, spanStyles = spanStyles)
}

@Preview
@Composable
fun PreviewPermissionDialog() {
    PermissionDialog(
        icon = painterResource(id = R.drawable.ic_alarm_filled),
        permission = "Schedule Alarms",
        text = "Quran Hifz Revision needs the Alarm permission to schedule notifications.",
        confirmButtonText = "Go to Settings",
        dismissButtonText = "Cancel",
        onConfirmClicked = {},
        onDismissed = {}
    )
}