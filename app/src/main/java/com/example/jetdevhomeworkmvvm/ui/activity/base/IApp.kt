package com.example.jetdevhomeworkmvvm.ui.activity.base

import android.app.Application
import com.example.jetdevhomeworkmvvm.domain.netModules
import com.example.jetdevhomeworkmvvm.domain.repoModule
import com.example.jetdevhomeworkmvvm.domain.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


 class IApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@IApp)
            modules(
                 netModules, repoModule, viewModelModule
            )
        }
    }
}