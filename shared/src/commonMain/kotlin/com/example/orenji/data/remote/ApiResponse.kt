package com.example.orenji.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val statusCode: Int,
    val isSuccess: Boolean,
    val message: String,
    val data: T? = null,
)