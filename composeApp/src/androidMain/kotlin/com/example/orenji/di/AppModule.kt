package com.example.orenji.di

import android.content.Context
import com.example.orenji.data.local.AndroidSessionStorage
import com.example.orenji.data.local.SessionStorage
import org.koin.dsl.module

val androidModule = module {
    single<SessionStorage> { AndroidSessionStorage(get<Context>()) }
}