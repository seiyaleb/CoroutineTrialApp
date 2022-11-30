package com.coroutinetrialapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "preferences"
)

//DataStoreの設定
class LaunchDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun saveLaunch() {
        this.dataStore.edit {
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

