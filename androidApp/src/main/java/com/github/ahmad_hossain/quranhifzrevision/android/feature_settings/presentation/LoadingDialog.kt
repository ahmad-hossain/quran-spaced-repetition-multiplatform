package com.github.ahmad_hossain.quranhifzrevision.android.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    text: String,
) {
    if (!isVisible) return
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { /*TODO*/ },
        content = {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.width(32.dp))
                Text(text)
            }
        }
    )
}