package com.example.orenji.data.remote

import io.ktor.client.call.*
import io.ktor.client.statement.*

suspend inline fun <reified T> safeApiCall(
    call: () -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = call()
        val apiResponse = response.body<ApiResponse<T>>()

        if (apiResponse.isSuccess && apiResponse.data != null) {
            NetworkResult.Success(apiResponse.data)
        } else {
            NetworkResult.Error(
                code = apiResponse.statusCode,
                message = apiResponse.message,
            )
        }
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}