package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class Screen {
    Home, Settings
}

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    currentScreen: Screen,
    onHomeClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
) {
    NavigationBar(modifier) {
        NavigationBarItem(
            selected = currentScreen == Screen.Home,
            onClick = onHomeClicked,
            icon = {
                Icon(
                    imageVector = if (currentScreen == Screen.Home) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == Screen.Settings,
            onClick = onSettingsClicked,
            icon = {
                Icon(
                    imageVector = if (currentScreen == Screen.Settings) Icons.Filled.Settings else Icons.Outlined.Settings,
                    contentDescription = null
                )
            }
        )
    }
}
