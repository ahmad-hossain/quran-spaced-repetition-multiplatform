package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val IconSize = 24.dp

@Composable
fun GradePageDialogOption(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onSelect: () -> Unit,
    emoji: String,
    description: String,
) {
    val backgroundColor = if (selected)
        MaterialTheme.colorScheme.primary else
        MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current)
    CompositionLocalProvider(LocalContentColor provides contentColorFor(backgroundColor)) {
        Row(
            modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .clickable { onSelect() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected)
                Icon(Icons.Default.Check, null)
            else
                Spacer(Modifier.width(IconSize))
            Spacer(Modifier.width(8.dp))
            Text(
                text = emoji,
                fontSize = 18.sp,
            )
            Spacer(Modifier.width(8.dp))
            Text(description)
        }
    }
}