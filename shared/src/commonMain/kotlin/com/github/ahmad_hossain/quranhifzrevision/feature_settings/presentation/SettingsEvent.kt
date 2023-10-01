package com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation

import kotlinx.datetime.LocalTime

sealed class SettingsEvent {
    object NotificationTimeSettingClicked : SettingsEvent()
    object PageNumberSettingClicked : SettingsEvent()
    object TimePickerDismissed : SettingsEvent()
    data class TimePickerTimeChanged(val time: LocalTime) : SettingsEvent()
    data class EditPageRangeDialogStartPageChanged(val startPage: String) : SettingsEvent()
    data class EditPageRangeDialogEndPageChanged(val endPage: String) : SettingsEvent()
    object EditPageRangeDialogConfirmed : SettingsEvent()
    object EditPageRangeDialogDismissed : SettingsEvent()
    object ExportDataClicked : SettingsEvent()
    object ImportDataClicked : SettingsEvent()
//    data class OnCreateDocumentActivityResult(val result: ActivityResult) : SettingsEvent()
//    data class OnOpenDocumentActivityResult(val result: ActivityResult) : SettingsEvent()
}