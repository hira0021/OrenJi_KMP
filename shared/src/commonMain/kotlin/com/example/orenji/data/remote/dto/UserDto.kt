package com.example.orenji.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatarUrl: String,
    val bio: String,
    val createdAt: String,
    val updatedAt: String,
)