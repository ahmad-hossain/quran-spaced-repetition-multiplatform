package com.github.ahmad_hossain.quranhifzrevision.android.feature_settings.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.App.Companion.deps
import com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.CustomBottomBar
import com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.Screen
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation.SettingsEvent
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation.SettingsUiEvent
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation.SettingsViewModel
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = deps.settingsViewModel,
    navigator: DestinationsNavigator,
) {
    // TODO
//    val createDocumentActivityResultLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            viewModel.onEvent(SettingsEvent.OnCreateDocumentActivityResult(it))
//        }
//    val openDocumentActivityResultLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            viewModel.onEvent(SettingsEvent.OnOpenDocumentActivityResult(it))
//        }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                // TODO
//                is SettingsUiEvent.LaunchCreateDocumentIntent -> createDocumentActivityResultLauncher.launch(
//                    it.intent
//                )
//
//                is SettingsUiEvent.LaunchOpenDocumentIntent -> openDocumentActivityResultLauncher.launch(
//                    it.intent
//                )
                is SettingsUiEvent.Toast -> Toast.makeText(
                    context,
                    StringDesc.Resource(it.s).toString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    val state by viewModel.state.collectAsState()

    if (state.isTimePickerVisible) {
        TimePickerDialog(
            title = { Text(stringResource(SharedRes.strings.select_time)) },
            initialTime = state.userPreferences.notificationTime.toJavaLocalTime(),
            onDismissRequest = { viewModel.onEvent(SettingsEvent.TimePickerDismissed) },
            onTimeChange = { viewModel.onEvent(SettingsEvent.TimePickerTimeChanged(it.toKotlinLocalTime())) }
        )
    }
    EditPageRangeDialog(
        isVisible = state.isEditPageRangeDialogVisible,
        startPage = state.dialogStartPage,
        endPage = state.dialogEndPage,
        startPageError = state.dialogStartPageError,
        endPageError = state.dialogEndPageError,
        onStartPageChanged = {
            viewModel.onEvent(
                SettingsEvent.EditPageRangeDialogStartPageChanged(
                    it
                )
            )
        },
        onEndPageChanged = { viewModel.onEvent(SettingsEvent.EditPageRangeDialogEndPageChanged(it)) },
        onDismissRequest = { viewModel.onEvent(SettingsEvent.EditPageRangeDialogDismissed) },
        onConfirmClicked = { viewModel.onEvent(SettingsEvent.EditPageRangeDialogConfirmed) }
    )
    LoadingDialog(
        isVisible = state.isLoadingDialogVisible,
        text = stringResource(id = SharedRes.strings.applying_setting)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(SharedRes.strings.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(SharedRes.strings.navigate_back)
                        )
                    }
                }
            )
        },
        bottomBar = {
            CustomBottomBar(
                currentScreen = Screen.Settings,
                onHomeClicked = { navigator.popBackStack() },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingsSectionHeadline(text = stringResource(SharedRes.strings.revision))
            ListItem(
                modifier = Modifier.clickable { viewModel.onEvent(SettingsEvent.PageNumberSettingClicked) },
                headlineText = { Text(stringResource(SharedRes.strings.quran_pages_setting)) },
                supportingText = { Text("${state.userPreferences.startPage} - ${state.userPreferences.endPage}") }
            )
            Divider()
            SettingsSectionHeadline(text = stringResource(SharedRes.strings.notifications))
            ListItem(
                modifier = Modifier.clickable { viewModel.onEvent(SettingsEvent.NotificationTimeSettingClicked) },
                headlineText = { Text(stringResource(SharedRes.strings.notification_time_setting)) },
                supportingText = {
                    Text(
                        state.userPreferences.notificationTime.toJavaLocalTime().format(
                            DateTimeFormatter.ofPattern("hh:mm a")
                        )
                    )
                }
            )
            Divider()
            SettingsSectionHeadline(text = "Backup")
            ListItem(
                modifier = Modifier.clickable { viewModel.onEvent(SettingsEvent.ExportDataClicked) },
                leadingContent = { Icon(Icons.Default.Upload, contentDescription = null) },
                headlineText = { Text(stringResource(SharedRes.strings.headline_export_data)) },
                supportingText = { Text(stringResource(SharedRes.strings.supporting_export_data)) },
            )
            ListItem(
                modifier = Modifier.clickable { viewModel.onEvent(SettingsEvent.ImportDataClicked) },
                leadingContent = { Icon(Icons.Default.Download, contentDescription = null) },
                headlineText = { Text(stringResource(SharedRes.strings.headline_import_data)) },
                supportingText = { Text(stringResource(SharedRes.strings.supporting_import_data)) },
            )
        }
    }
}