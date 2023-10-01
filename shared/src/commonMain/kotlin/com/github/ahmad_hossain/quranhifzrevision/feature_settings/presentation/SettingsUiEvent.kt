package com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation

import dev.icerock.moko.resources.StringResource

sealed class SettingsUiEvent {
    data class Toast(val s: StringResource) : SettingsUiEvent()
    // TODO
//    data class LaunchCreateDocumentIntent(val intent: Intent) : SettingsUiEvent()
//    data class LaunchOpenDocumentIntent(val intent: Intent) : SettingsUiEvent()
}
