package com.github.ahmad_hossain.quranhifzrevision

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.DatabaseDriverFactory
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.data.dataStore

class IosDependencies : Dependencies() {
    override val driver: SqlDriver = DatabaseDriverFactory().create()
    override val dataStore: DataStore<Preferences> by lazy { dataStore() }
}