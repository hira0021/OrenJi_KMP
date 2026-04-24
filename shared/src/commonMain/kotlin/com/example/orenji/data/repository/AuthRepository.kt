package com.example.orenji.data.repository

import com.example.orenji.data.local.TokenStorage
import com.example.orenji.data.remote.api.AuthApi
import com.example.orenji.data.remote.NetworkResult
import com.example.orenji.domain.model.User
import com.example.orenji.domain.model.toDomain

class AuthRepository(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage,
) {
    suspend fun login(email: String, password: String): Result<User> {
        return when (val result = api.login(email, password)) {
            is NetworkResult.Success -> {
                val user = result.data.user.toDomain()
                tokenStorage.saveToken(result.data.token)
                Result.success(user)
            }
            is NetworkResult.Error -> {
                Result.failure(Exception(result.message))
            }
            is NetworkResult.Exception -> {
                Result.failure(result.throwable)
            }
        }
    }

    suspend fun getToken(): String? = tokenStorage.getToken()

    suspend fun isLoggedIn(): Boolean = tokenStorage.getToken() != null

    suspend fun logout() = tokenStorage.clearToken()
}