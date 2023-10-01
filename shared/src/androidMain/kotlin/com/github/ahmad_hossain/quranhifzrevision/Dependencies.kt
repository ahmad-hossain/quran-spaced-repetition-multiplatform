package com.github.ahmad_hossain.quranhifzrevision

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.data.DatabaseDriverFactory
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.data.dataStore

class AndroidDependencies(
    context: Context,
) : Dependencies() {
    override val driver = DatabaseDriverFactory(context).create()
    override val dataStore: DataStore<Preferences> by lazy { dataStore(context) }
}