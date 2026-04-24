package com.example.orenji.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class AndroidTokenStorage(private val context: Context) : TokenStorage {

    private val tokenKey = stringPreferencesKey("jwt_token")

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { it[tokenKey] = token }
    }

    override suspend fun getToken(): String? {
        return context.dataStore.data.map { it[tokenKey] }.first()
    }

    override suspend fun clearToken() {
        context.dataStore.edit { it.remove(tokenKey) }
    }

}