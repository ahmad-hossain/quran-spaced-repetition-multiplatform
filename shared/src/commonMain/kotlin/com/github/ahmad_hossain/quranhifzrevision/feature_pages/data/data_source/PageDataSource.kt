package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import com.github.ahmad_hossain.quranhifzrevision.PageDatabase
import com.github.ahmadhossain.quranhifzrevision.Page
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class PageDataSource(
    db: PageDatabase,
    private val driver: SqlDriver
) {
    private val queries = db.pageDatabaseQueries

    fun getPages(): Flow<List<Page>> =
        queries.getPages().asFlow().mapToList(Dispatchers.IO)

    fun getPagesDueToday(): Flow<List<Page>> = getPagesDueOn(Clock.System.todayIn(TimeZone.currentSystemDefault()))

    fun getPagesDueOn(date: LocalDate): Flow<List<Page>> =
        queries.getPagesDueOn(date.toEpochDays().toLong()).asFlow().mapToList(Dispatchers.IO)

    suspend fun updatePage(page: Page) {
        withContext(Dispatchers.IO) {
            insertPage(page)
        }
    }

    suspend fun insertPage(page: Page) {
        withContext(Dispatchers.IO) {
            queries.insertPage(
                page.pageNumber,
                page.interval,
                page.repetitions,
                page.eFactor,
                page.dueDate
            )
        }
    }

    suspend fun deletePage(page: Page) {
        withContext(Dispatchers.IO) {
            queries.deletePageByNumber(page.pageNumber)
        }
    }

    suspend fun checkpoint() {
        driver.executeQuery(
            identifier = null,
            sql = "PRAGMA wal_checkpoint(full)",
            mapper = SqlCursor::next,
            parameters = 0,
        )
    }
}
