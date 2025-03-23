package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import androidx.room.Update
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model.Page
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.now
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface PageDao {

    @Query("SELECT * FROM Page")
    fun getPages(): Flow<List<Page>>

    @Query("SELECT * FROM Page WHERE dueDate<=:currEpochDay")
    fun getPagesDueToday(currEpochDay: Int = LocalDate.now().toEpochDays()): Flow<List<Page>>

    @Insert
    suspend fun insertPage(page: Page)

    @Update
    suspend fun updatePage(page: Page)

    @Delete
    suspend fun deletePage(page: Page)

    @RawQuery
    suspend fun checkpoint(supportSQLiteQuery: RoomRawQuery): Int
}
