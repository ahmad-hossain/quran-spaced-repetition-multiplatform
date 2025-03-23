package com.github.ahmad_hossain.quranhifzrevision

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDataSource
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.PagesViewModel
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.repository.SettingsRepository
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.use_case.ChangePageRange
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation.SettingsViewModel
import com.github.ahmadhossain.quranhifzrevision.Page

abstract class Dependencies {
    abstract val driver: SqlDriver
    private val database: PageDatabase by lazy {
        PageDatabase(
            driver,
            Page.Adapter(
                pageNumberAdapter = IntColumnAdapter,
                intervalAdapter = IntColumnAdapter,
                repetitionsAdapter = IntColumnAdapter
            )
        )
    }
    private val pageDataSource by lazy { PageDataSource(database, driver) }
    abstract val dataStore: Lazy<DataStore<Preferences>>
    private val settingsRepository by lazy { SettingsRepository(dataStore.value) }
    val pagesViewModel: PagesViewModel by lazy { PagesViewModel(pageDataSource) }
    val settingsViewModel: SettingsViewModel by lazy {
        SettingsViewModel(
            settingsRepository,
            ChangePageRange(settingsRepository, pageDataSource)
        )
    }
}