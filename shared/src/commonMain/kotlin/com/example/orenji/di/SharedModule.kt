package com.example.orenji.di

import com.example.orenji.data.remote.api.AuthApi
import com.example.orenji.data.repository.AuthRepository
import com.example.orenji.presentation.auth.AuthViewModel
import com.example.orenji.presentation.map.FamilyMapViewModel
import com.example.orenji.presentation.profile.ProfileViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
    single { AuthApi(get()) }
    single { AuthRepository(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { FamilyMapViewModel() }
}