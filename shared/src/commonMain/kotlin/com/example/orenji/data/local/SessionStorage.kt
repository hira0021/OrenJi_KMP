package com.example.orenji.data.local

import com.example.orenji.domain.model.User

interface SessionStorage {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()

    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()

    suspend fun clear()
}