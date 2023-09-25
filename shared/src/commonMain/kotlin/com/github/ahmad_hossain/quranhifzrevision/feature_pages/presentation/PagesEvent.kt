package com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation

import com.github.ahmadhossain.quranhifzrevision.Page

sealed class PagesEvent {
    data class PageClicked(val page: Page) : PagesEvent()
    data class TabClicked(val tab: UiTabs) : PagesEvent()
    object GradeDialogConfirmed : PagesEvent()
    object GradeDialogDismissed : PagesEvent()
    object SearchFabClicked : PagesEvent()
    object SearchDialogConfirmed : PagesEvent()
    object SearchDialogDismissed : PagesEvent()
    data class SearchQueryChanged(val query: String) : PagesEvent()
    data class NumberPickerValueChanged(val newValue: Int) : PagesEvent()
    data class GradeSelected(val grade: Int) : PagesEvent()
}