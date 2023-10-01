package com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation

import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.model.UserPreferences
import dev.icerock.moko.resources.StringResource

data class SettingsState(
    val userPreferences: UserPreferences = UserPreferences(),
    val isTimePickerVisible: Boolean = false,
    val isEditPageRangeDialogVisible: Boolean = false,
    val dialogStartPage: String = "",
    val dialogEndPage: String = "",
    val dialogStartPageError: StringResource? = null,
    val dialogEndPageError: StringResource? = null,
    val isLoadingDialogVisible: Boolean = false,
)