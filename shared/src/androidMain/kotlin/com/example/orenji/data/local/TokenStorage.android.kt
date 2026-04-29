package com.example.orenji.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.orenji.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class AndroidSessionStorage(private val context: Context) : SessionStorage {

    private val tokenKey = stringPreferencesKey("jwt_token")
    private val userIdKey = intPreferencesKey("user_id")
    private val userNameKey = stringPreferencesKey("user_full_name")
    private val userEmailKey = stringPreferencesKey("user_email")
    private val userAvatarKey = stringPreferencesKey("user_avatar")
    private val userBioKey = stringPreferencesKey("user_bio")

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { it[tokenKey] = token }
    }

    override suspend fun getToken(): String? {
        return context.dataStore.data.map { it[tokenKey] }.first()
    }

    override suspend fun clearToken() {
        context.dataStore.edit { it.remove(tokenKey) }
    }

    override suspend fun saveUser(user: User) {
        context.dataStore.edit {
            it[userIdKey] = user.id
            it[userNameKey] = user.fullName
            it[userEmailKey] = user.email
            it[userAvatarKey] = user.avatarUrl ?: ""
            it[userBioKey] = user.bio ?: ""
        }
    }

    override suspend fun getUser(): User? {
        return context.dataStore.data.map { prefs ->
            val id = prefs[userIdKey] ?: return@map null
            User(
                id = id,
                fullName = prefs[userNameKey] ?: "",
                email = prefs[userEmailKey] ?: "",
                avatarUrl = prefs[userAvatarKey]?.ifEmpty { null },
                bio = prefs[userBioKey]?.ifEmpty { null },
            )
        }.first()
    }

    override suspend fun clearUser() {
        context.dataStore.edit {
            it.remove(userIdKey)
            it.remove(userNameKey)
            it.remove(userEmailKey)
            it.remove(userAvatarKey)
            it.remove(userBioKey)
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }


}