package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.PageUtil.relativeDueDate
import com.github.ahmadhossain.quranhifzrevision.Page

@Composable
fun PageItem(
    modifier: Modifier = Modifier,
    page: Page
) {
    Column {
        Row(modifier) {
            TableCell(text = page.pageNumber.toString(), weight = 1f)
            TableCell(text = page.interval.toString(), weight = 1f)
            TableCell(text = page.repetitions.toString(), weight = 1f)
            TableCell(text = page.relativeDueDate ?: stringResource(id = SharedRes.strings.na), weight = 1f)
        }
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}