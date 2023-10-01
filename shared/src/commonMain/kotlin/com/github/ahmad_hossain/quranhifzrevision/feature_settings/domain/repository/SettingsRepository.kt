package com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.ahmad_hossain.quranhifzrevision.feature_settings.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime

class SettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { it.toUserPreferences() }

    suspend fun getData() {
        dataStore.data.first().toPreferences()
    }

    suspend fun setData(userPreferences: UserPreferences) {
        dataStore.edit {
            it[START_PAGE] = userPreferences.startPage
            it[END_PAGE] = userPreferences.endPage
            it[NOTIFICATION_TIME] = userPreferences.notificationTime.toString()
        }
    }

    private fun Preferences.toUserPreferences(): UserPreferences {
        val defaultPref = UserPreferences()
        val startPage = this[START_PAGE] ?: defaultPref.startPage
        val endPage = this[END_PAGE] ?: defaultPref.endPage
        val notificationTime = LocalTime.parse(
            this[NOTIFICATION_TIME] ?: defaultPref.notificationTime.toString()
        )
        return UserPreferences(startPage, endPage, notificationTime)
    }

    private companion object {
        val START_PAGE = intPreferencesKey("start_page")
        val END_PAGE = intPreferencesKey("end_page")
        val NOTIFICATION_TIME = stringPreferencesKey("notification_time")
    }
}