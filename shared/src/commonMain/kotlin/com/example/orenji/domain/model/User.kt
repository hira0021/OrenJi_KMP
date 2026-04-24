package com.example.orenji.domain.model

import com.example.orenji.data.remote.dto.UserDto

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val avatarUrl: String?,
    val bio: String?,
)

fun UserDto.toDomain() = User(
    id = id,
    fullName = "$firstName $lastName",
    email = email,
    avatarUrl = avatarUrl.ifEmpty { null },
    bio = bio.ifEmpty { null },
)