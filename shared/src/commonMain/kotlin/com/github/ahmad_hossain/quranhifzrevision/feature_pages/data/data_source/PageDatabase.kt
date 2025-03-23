package com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.domain.model.Page

@Database(
    entities = [Page::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(PageDatabaseConstructor::class)
abstract class PageDatabase : RoomDatabase() {

    abstract val pageDao: PageDao

    companion object {
        const val DATABASE_NAME = "page_db"
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PageDatabaseConstructor : RoomDatabaseConstructor<PageDatabase> {
    override fun initialize(): PageDatabase
}
