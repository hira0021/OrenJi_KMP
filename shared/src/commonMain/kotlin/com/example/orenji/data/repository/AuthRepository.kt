package com.example.orenji.data.repository

import com.example.orenji.data.local.SessionStorage
import com.example.orenji.data.remote.api.AuthApi
import com.example.orenji.data.remote.NetworkResult
import com.example.orenji.domain.model.User
import com.example.orenji.domain.model.toDomain

class AuthRepository(
    private val api: AuthApi,
    private val sessionStorage: SessionStorage,
) {
    suspend fun login(email: String, password: String): Result<User> {
        return when (val result = api.login(email, password)) {
            is NetworkResult.Success -> {
                val user = result.data.user.toDomain()
                sessionStorage.saveToken(result.data.token)
                sessionStorage.saveUser(user)
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

    suspend fun getToken(): String? = sessionStorage.getToken()

    suspend fun getLoggedInUser(): User? = sessionStorage.getUser()

    suspend fun isLoggedIn(): Boolean = sessionStorage.getToken() != null

    suspend fun logout() {
        sessionStorage.clearToken()
        sessionStorage.clearUser()
    }
}