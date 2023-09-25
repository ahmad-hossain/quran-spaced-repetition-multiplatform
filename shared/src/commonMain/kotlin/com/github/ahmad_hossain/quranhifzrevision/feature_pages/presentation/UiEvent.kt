package com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation

sealed class UiEvent {
    data class ScrollToIndex(val index: Int) : UiEvent()
    object ExpandTopAndBottomBars : UiEvent()
}