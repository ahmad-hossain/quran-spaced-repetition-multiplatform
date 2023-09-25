package com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation

import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDataSource
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.domain.SuperMemo
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.PagesEvent.*
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.now
import com.github.ahmadhossain.quranhifzrevision.Page
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

open class PagesViewModel(
    private val dataSource: PageDataSource,
    // TODO
//    private val updateReminderNotificationUseCase: UpdateReminderNotification,
) : KMMViewModel() {

    private val _viewModelScope = viewModelScope.coroutineScope

    private val _state = MutableStateFlow(viewModelScope, PagesState())
    val state = _state.asStateFlow()
    private lateinit var lastClickedPage: Page

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: PagesEvent) {
        // TODO
//        Timber.d("%s : %s", event::class.simpleName, event.toString())

        when (event) {
            is PageClicked -> {
                lastClickedPage = event.page
                _state.value = state.value.copy(
                    isGradeDialogVisible = true,
                    lastClickedPageNumber = lastClickedPage.pageNumber
                )
            }
            is TabClicked -> {
                _viewModelScope.launch { _uiEvent.emit(UiEvent.ExpandTopAndBottomBars) }
                when (event.tab) {
                    UiTabs.TODAY -> _state.value = state.value.copy(
                        selectedTab = event.tab,
                        displayedPages = state.value.pagesDueToday
                    )
                    UiTabs.ALL -> _state.value = state.value.copy(
                        selectedTab = event.tab,
                        displayedPages = state.value.allPages
                    )
                }
            }
            is GradeDialogDismissed -> _state.value = state.value.copy(isGradeDialogVisible = false, selectedGrade = 5)
            is GradeDialogConfirmed -> {
                val grade = state.value.selectedGrade
                _state.value = state.value.copy(isGradeDialogVisible = false, selectedGrade = 5)

                val updatedPage = SuperMemo(page = lastClickedPage, grade = grade)
                _viewModelScope.launch(Dispatchers.IO) {
                    dataSource.updatePage(
                        updatedPage.copy(
                            dueDate = LocalDate.now().plus(DatePeriod(days = updatedPage.interval)).toEpochDays().toLong()
                        )
                    )
                    // TODO
//                    if (state.value.pagesDueToday.contains(lastClickedPage))
//                        updateReminderNotificationUseCase()
                }
            }
            is NumberPickerValueChanged -> _state.value = state.value.copy(selectedGrade = event.newValue)
            is GradeSelected -> _state.value = state.value.copy(selectedGrade = event.grade)
            is SearchFabClicked -> _state.value = state.value.copy(isSearchDialogVisible = true)
            is SearchDialogConfirmed -> {
                val queriedPageNum = state.value.searchQuery.toInt()
                resetSearchDialog()
                _viewModelScope.launch {
                    val queriedPageIndex = state.value.displayedPages
                        .indexOfFirst { it.pageNumber == queriedPageNum }
                        .takeUnless { it == -1 }
                        ?: queriedPageNum.coerceIn(0, state.value.displayedPages.lastIndex)
                    _uiEvent.emit(UiEvent.ScrollToIndex(queriedPageIndex))
                }
            }
            is SearchDialogDismissed -> resetSearchDialog()
            is SearchQueryChanged -> {
                val searchQueryError = when {
                    event.query.isEmpty() -> SharedRes.strings.empty_field_error
                    event.query.toIntOrNull() == null -> {
                        _state.value = state.value.copy(
                            searchQuery = state.value.searchQuery,
                            searchQueryError = SharedRes.strings.invalid_number
                        )
                        return
                    }
                    else -> null
                }
                _state.value = state.value.copy(searchQuery = event.query, searchQueryError = searchQueryError)
            }
        }
    }

    private fun resetSearchDialog() {
        _state.value = state.value.copy(
            isSearchDialogVisible = false,
            searchQuery = "",
            searchQueryError = null
        )
    }

    init {
        dataSource.getPagesDueToday().onEach {
            _state.value = state.value.copy(
                displayedPages = if (state.value.selectedTab == UiTabs.TODAY) it else state.value.displayedPages,
                pagesDueToday = it
            )
        }.launchIn(_viewModelScope)

        dataSource.getPages().onEach {
            _state.value = state.value.copy(
                displayedPages = if (state.value.selectedTab == UiTabs.ALL) it else state.value.displayedPages,
                allPages = it
            )
        }.launchIn(_viewModelScope)
    }
}