package com.example.orenji.di

import android.content.Context
import com.example.orenji.data.local.AndroidTokenStorage
import com.example.orenji.data.local.TokenStorage
import org.koin.dsl.module

val androidModule = module {
    single<TokenStorage> { AndroidTokenStorage(get<Context>()) }
}