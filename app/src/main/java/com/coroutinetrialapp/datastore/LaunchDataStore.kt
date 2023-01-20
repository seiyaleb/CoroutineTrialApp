package com.coroutinetrialapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//DataStoreの設定
class LaunchDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveLaunch() {
        dataStore.edit {
            it[PreferenceKeys.LAUNCH] = true
        }
    }
    val preferenceFlow: Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                it[PreferenceKeys.LAUNCH] ?: false
            }
}

private object PreferenceKeys {
    val LAUNCH = booleanPreferencesKey("isLaunch")
}

