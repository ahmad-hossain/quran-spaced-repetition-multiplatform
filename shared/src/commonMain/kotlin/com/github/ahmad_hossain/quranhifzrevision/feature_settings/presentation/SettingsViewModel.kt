package com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation

import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.repository.SettingsRepository
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.use_case.ChangePageRange
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val settingsRepo: SettingsRepository,
    // TODO
//    private val scheduleNotificationAlarmUseCase: ScheduleNotificationAlarm,
//    private val app: Application,
//    private val validSqlLiteDbUseCase: ValidSqlLiteDb,
//    private val db: PageDatabase,
//    private val pageRepository: PageDataSource,
    private val changePageRangeUseCase: ChangePageRange,
) : ViewModel() {
    private val _viewModelScope = viewModelScope.coroutineScope

    private val _state = MutableStateFlow(viewModelScope, SettingsState())
    val state = _state.asStateFlow()
    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: SettingsEvent) {
        // TODO
//        Timber.d("%s : %s", event::class.simpleName, event.toString())
        println("MYTAG ${event::class.simpleName} : $event")

        when (event) {
            is SettingsEvent.NotificationTimeSettingClicked -> _state.value =
                state.value.copy(isTimePickerVisible = true)
            is SettingsEvent.PageNumberSettingClicked -> resetEditPageRangeDialogStates(isVisible = true)
            is SettingsEvent.TimePickerTimeChanged -> {
                _viewModelScope.launch {
                    settingsRepo.updateDatastore { it.copy(notificationTime = event.time) }
                    // TODO
//                    scheduleNotificationAlarmUseCase()
                }
            }
            is SettingsEvent.TimePickerDismissed -> _state.value =
                state.value.copy(isTimePickerVisible = false)
            is SettingsEvent.TimePickerConfirmed -> {
                _state.value = state.value.copy(isTimePickerVisible = false)
                _viewModelScope.launch {
                    settingsRepo.updateDatastore { it.copy(notificationTime = event.time) }
                    // TODO
//                    scheduleNotificationAlarmUseCase()
                }
            }
            is SettingsEvent.EditPageRangeDialogConfirmed -> {
                _state.value = state.value.copy(isLoadingDialogVisible = true)
                val newPageRange =
                    state.value.dialogStartPage.toInt()..state.value.dialogEndPage.toInt()
                resetEditPageRangeDialogStates()
                _viewModelScope.launch(Dispatchers.IO) {
                    changePageRangeUseCase(newPageRange).join()
                    settingsRepo.updateDatastore {
                        it.copy(startPage = newPageRange.first, endPage = newPageRange.last)
                    }
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(isLoadingDialogVisible = false)
                    }
                }
            }
            is SettingsEvent.EditPageRangeDialogDismissed -> resetEditPageRangeDialogStates()
            is SettingsEvent.EditPageRangeDialogStartPageChanged -> {
                val start = event.startPage
                val end = state.value.dialogEndPage
                val startInt = start.toIntOrNull()
                val startPageError = when {
                    start.isEmpty() -> SharedRes.strings.empty_field_error
                    startInt == null -> {
                        _state.value = state.value.copy(
                            dialogStartPage = state.value.dialogStartPage,
                            dialogStartPageError = SharedRes.strings.invalid_number
                        )
                        return
                    }
                    startInt >= end.toInt() -> SharedRes.strings.start_greater_than_end_error
                    else -> null
                }
                _state.value = state.value.copy(
                    dialogStartPage = event.startPage,
                    dialogStartPageError = startPageError
                )
            }
            is SettingsEvent.EditPageRangeDialogEndPageChanged -> {
                val start = state.value.dialogStartPage
                val end = event.endPage
                val endInt = end.toIntOrNull()
                val endPageError = when {
                    end.isEmpty() -> SharedRes.strings.empty_field_error
                    endInt == null -> {
                        _state.value = state.value.copy(
                            dialogEndPage = state.value.dialogEndPage,
                            dialogEndPageError = SharedRes.strings.invalid_number
                        )
                        return
                    }
                    end.toInt() <= start.toInt() -> SharedRes.strings.end_less_than_start_error
                    else -> null
                }
                _state.value = state.value.copy(
                    dialogEndPage = event.endPage,
                    dialogEndPageError = endPageError
                )
            }
            is SettingsEvent.ExportDataClicked -> {
                // TODO
//                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//                    type = "*/*"
//                    putExtra(Intent.EXTRA_TITLE, "quran_hifz_revision_backup.db")
//                }
//                _viewModelScope.launch {
//                    _uiEvent.emit(SettingsUiEvent.LaunchCreateDocumentIntent(intent))
//                }
            }
            is SettingsEvent.ImportDataClicked -> {
                // TODO
//                val dbMimeTypes = arrayOf("application/vnd.sqlite3", "application/octet-stream")
//                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//                    addCategory(Intent.CATEGORY_OPENABLE)
//                    type = "*/*"
//                    putExtra(Intent.EXTRA_MIME_TYPES, dbMimeTypes)
//                }
//                _viewModelScope.launch {
//                    _uiEvent.emit(SettingsUiEvent.LaunchOpenDocumentIntent(intent))
//                }
            }

            // TODO
//            is SettingsEvent.OnCreateDocumentActivityResult -> {
//                if (event.result.resultCode != Activity.RESULT_OK) return
//                val userChosenUri = event.result.data?.data
//                if (userChosenUri == null) {
//                    _viewModelScope.launch {
//                        _uiEvent.emit(
//                            SettingsUiEvent.Toast(
//
//                                SharedRes.strings.error_null_uri
//                            )
//                        )
//                        )
//                    }
//                    return
//                }
//
//                _viewModelScope.launch(Dispatchers.IO) {
//                    pageRepository.checkpoint()
//
//                    val inputStream = app.getDatabasePath(PageDatabase.DATABASE_NAME).inputStream()
//                    val outputStream =
//                        app.contentResolver.openOutputStream(userChosenUri) ?: return@launch
//
//                    inputStream.copyTo(outputStream)
//
//                    inputStream.close()
//                    outputStream.close()
//                }
//            }
            // TODO
//            is SettingsEvent.OnOpenDocumentActivityResult -> {
//                if (event.result.resultCode != Activity.RESULT_OK) return
//                val dbUri = event.result.data?.data ?: return
//                if (!validSqlLiteDbUseCase.isValid(dbUri)) {
//                    Timber.d("OnOpenDocumentActivityResult: Invalid sqlite db")
//                    _viewModelScope.launch {
//                        _uiEvent.emit(
//                            SettingsUiEvent.Toast(
//
//                                SharedRes.strings.error_invalid_file
//                            )
//                        )
//                        )
//                    }
//                    return
//                }

//                _viewModelScope.launch(Dispatchers.IO) {
//                    db.close()
//
//                    val inputStream = app.contentResolver.openInputStream(dbUri) ?: return@launch
//                    val outputStream =
//                        app.getDatabasePath(PageDatabase.DATABASE_NAME).outputStream()
//
//                    inputStream.copyTo(outputStream)
//                    inputStream.close()
//                    outputStream.close()
//
//                    val intent = app.packageManager.getLaunchIntentForPackage(app.packageName)
//                    intent?.apply {
//                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        action = INTENT_ACTION_RESTART
//                    }
//                    app.startActivity(intent)
//                    exitProcess(0)
//                }
//            }
        }
    }

    private fun resetEditPageRangeDialogStates(isVisible: Boolean = false) {
        _viewModelScope.launch {
            val prefs = settingsRepo.getUserPreferences()
            _state.value = state.value.copy(
                isEditPageRangeDialogVisible = isVisible,
                dialogStartPage = prefs.startPage.toString(),
                dialogEndPage = prefs.endPage.toString(),
            )
        }
    }

    init {
        settingsRepo.userPreferencesFlow.onEach {
            _state.value = state.value.copy(userPreferences = it)
        }.launchIn(_viewModelScope)
    }

    // TODO
//    companion object {
//        const val INTENT_ACTION_RESTART = BuildConfig.APPLICATION_ID + ".RESTART"
//    }
}