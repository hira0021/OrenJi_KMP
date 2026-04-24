package com.example.orenji.data.remote.api

import com.example.orenji.data.remote.NetworkResult
import com.example.orenji.data.remote.dto.AuthDataDto
import com.example.orenji.data.remote.dto.LoginRequestDto
import com.example.orenji.data.remote.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {

    suspend fun login(
        email: String,
        password: String
    ): NetworkResult<AuthDataDto> = safeApiCall {
        client.post("$BASE_URL/api/v1/users/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(email, password))
        }
    }

    companion object {
        const val BASE_URL = "http://10.0.2.2:4000"
    }
}