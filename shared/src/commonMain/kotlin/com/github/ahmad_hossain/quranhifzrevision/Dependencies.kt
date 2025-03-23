package com.github.ahmad_hossain.quranhifzrevision

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDao
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.repository.PageRepositoryImpl
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model.Page
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.repository.PageRepository
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.PagesViewModel
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.repository.SettingsRepository
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.use_case.ChangePageRange
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.presentation.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking

abstract class Dependencies {
    abstract val databaseBuilder: RoomDatabase.Builder<PageDatabase>
    private val database: PageDatabase by lazy {
        databaseBuilder
            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(connection: SQLiteConnection) {
                        super.onCreate(connection)

                        runBlocking {
                            val userPrefs = settingsRepository.getUserPreferences()
                            val defaultPage = Page(pageNumber = 0)

                            (userPrefs.startPage..userPrefs.endPage).forEach { pageNum ->
                                connection.execSQL("INSERT INTO Page VALUES ($pageNum, ${defaultPage.interval}, ${defaultPage.repetitions}, ${defaultPage.eFactor}, ${defaultPage.dueDate})")
                            }
                        }
                    }
                }
            )
            .build()
    }
    private val pageDao: PageDao by lazy { database.pageDao }
    private val pageRepository: PageRepository by lazy { PageRepositoryImpl(pageDao) }
    abstract val dataStore: Lazy<DataStore<Preferences>>
    private val settingsRepository by lazy { SettingsRepository(dataStore.value) }
    val pagesViewModel: PagesViewModel by lazy { PagesViewModel(pageRepository) }
    val settingsViewModel: SettingsViewModel by lazy {
        SettingsViewModel(
            settingsRepository,
            ChangePageRange(settingsRepository, pageRepository)
        )
    }
}