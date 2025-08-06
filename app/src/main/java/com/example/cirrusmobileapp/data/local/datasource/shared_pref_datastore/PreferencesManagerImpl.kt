package com.example.cirrusmobileapp.data.local.datasource.shared_pref_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
    private val dataStore = context.dataStore

    override suspend fun saveString(key: String, value: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return dataStore.data
            .map { prefs -> prefs[stringPreferencesKey(key)] }
            .firstOrNull()
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return dataStore.data
            .map { prefs -> prefs[booleanPreferencesKey(key)] }
            .firstOrNull()
    }

    override suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}