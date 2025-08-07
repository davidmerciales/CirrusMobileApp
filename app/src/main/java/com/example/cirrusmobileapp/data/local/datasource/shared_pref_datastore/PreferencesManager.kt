package com.example.cirrusmobileapp.data.local.datasource.shared_pref_datastore

interface PreferencesManager {
    suspend fun saveString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
    suspend fun clearAll()
}
