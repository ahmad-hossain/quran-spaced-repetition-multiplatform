package com.github.ahmad_hossain.quranhifzrevision

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.data_source.PageDataSource
import com.github.ahmadhossain.quranhifzrevision.Page

abstract class Dependencies {
    abstract val driver: SqlDriver
    val database: PageDatabase by lazy {
        PageDatabase(
            driver,
            Page.Adapter(
                pageNumberAdapter = IntColumnAdapter,
                intervalAdapter = IntColumnAdapter,
                repetitionsAdapter = IntColumnAdapter
            )
        )
    }
    val pageDataSource by lazy { PageDataSource(database, driver) }
    abstract val dataStore: DataStore<Preferences>
}