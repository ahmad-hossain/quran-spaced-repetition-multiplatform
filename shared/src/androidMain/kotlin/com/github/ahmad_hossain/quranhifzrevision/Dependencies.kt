package com.github.ahmad_hossain.quranhifzrevision

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDatabase
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.data.dataStore

class AndroidDependencies(
    context: Context,
) : Dependencies() {
    override val databaseBuilder: RoomDatabase.Builder<PageDatabase> =
        Room.databaseBuilder<PageDatabase>(
            context = context,
            name = context.getDatabasePath(PageDatabase.DATABASE_NAME).absolutePath
        )
    override val dataStore: Lazy<DataStore<Preferences>> = lazy { dataStore(context) }
}