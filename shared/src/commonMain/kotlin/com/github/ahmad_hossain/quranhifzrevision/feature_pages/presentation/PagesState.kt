package com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation

import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model.Page
import dev.icerock.moko.resources.StringResource

enum class UiTabs {
    TODAY, ALL
}

data class PagesState(
    val displayedPages: List<Page> = emptyList(),
    val allPages: List<Page> = emptyList(),
    val pagesDueToday: List<Page> = emptyList(),
    val selectedTab: UiTabs = UiTabs.TODAY,
    val isGradeDialogVisible: Boolean = false,
    val lastClickedPageNumber: Int = -1,
    val selectedGrade: Int = 5,
    val isSearchDialogVisible: Boolean = false,
    val searchQuery: String = "",
    val searchQueryError: StringResource? = null,
)