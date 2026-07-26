package com.example.mifinca

import android.app.Application
import com.example.mifinca.data.local.DatabaseDriverFactory
import com.example.mifinca.data.repository.InitialDataRepository
import com.example.mifinca.di.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Clase Application para inicializar Koin en Android
 */
class MiFincaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@MiFincaApplication)
            modules(
                appModule,
                androidModule
            )
        }

        initializeData()
    }

    private fun initializeData() {
        val initialDataRepository = org.koin.java.KoinJavaComponent.get<InitialDataRepository>(
            InitialDataRepository::class.java
        )

        runBlocking(Dispatchers.IO) {
            initialDataRepository.initializeDefaultData()
        }
    }
}

/**
 * Módulo específico de Android
 */
val androidModule = module {
    single { DatabaseDriverFactory(get()) }
}
