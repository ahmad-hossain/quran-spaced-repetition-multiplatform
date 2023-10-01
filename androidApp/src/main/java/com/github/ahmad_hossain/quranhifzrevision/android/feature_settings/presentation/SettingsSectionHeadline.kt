package com.github.ahmad_hossain.quranhifzrevision.android.feature_settings.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsSectionHeadline(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
        text = text,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        color = MaterialTheme.colorScheme.primary,
    )
}