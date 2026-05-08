package com.example.orenji

import android.app.Application
import com.example.orenji.di.androidModule
import com.example.orenji.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OrenjiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OrenjiApp)
            modules(
                androidModule,
                sharedModule
            )
        }
    }
}