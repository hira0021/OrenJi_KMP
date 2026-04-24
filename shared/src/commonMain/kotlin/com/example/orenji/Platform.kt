package com.example.orenji

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform