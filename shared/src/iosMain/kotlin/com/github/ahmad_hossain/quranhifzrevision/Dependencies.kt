package com.github.ahmad_hossain.quranhifzrevision

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.data.dataStore
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IosDependencies : Dependencies() {
    override val databaseBuilder: RoomDatabase.Builder<PageDatabase> =
        Room.databaseBuilder<PageDatabase>(
            name = documentDirectory() + PageDatabase.DATABASE_NAME
        )
    override val dataStore: Lazy<DataStore<Preferences>> = lazy { dataStore() }
}


private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}