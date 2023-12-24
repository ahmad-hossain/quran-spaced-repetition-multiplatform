package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.GradeOption

@Composable
fun GradePageDialog(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    selectedGrade: Int,
    onSelectGrade: (Int) -> Unit,
) {
    if (!isVisible) return

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(SharedRes.strings.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(SharedRes.strings.cancel))
            }
        },
        title = { Text(stringResource(SharedRes.strings.grade_your_review)) },
        text = {
            val dividerColor = LocalContentColor.current.copy(alpha = 0.7f)

            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pageNumber.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(16.dp))
                Divider(color = dividerColor)
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    GradeOption.gradeOptions.forEachIndexed { index, gradeOption ->
                        if (index != 0)
                            Spacer(Modifier.height(6.dp))
                        GradePageDialogOption(
                            modifier = Modifier.fillMaxWidth(),
                            selected = selectedGrade == gradeOption.grade,
                            onSelect = { onSelectGrade(gradeOption.grade) },
                            emoji = gradeOption.emoji,
                            description = stringResource(gradeOption.textRes),
                        )
                    }
                }
                Divider(modifier = Modifier.requiredHeight(1.dp), color = dividerColor)
            }
        },
        icon = { Icon(Icons.Outlined.StarRate, null) }
    )
}

@Preview
@Composable
fun PreviewGradingDialog() {
    GradePageDialog(
        isVisible = true,
        pageNumber = 27,
        onDismissRequest = {},
        onConfirm = {},
        onDismiss = {},
        selectedGrade = 0,
        onSelectGrade = {},
    )
}