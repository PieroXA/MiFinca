package com.example.mifinca

import android.app.Application
import com.example.mifinca.data.local.DatabaseDriverFactory
import com.example.mifinca.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Clase Application para inicializar Koin en Android.
 *
 * IMPORTANTE: la carga de datos demo (InitialDataRepository.initializeDefaultData)
 * ya NO se ejecuta aquí con runBlocking. Ese patrón bloqueaba el hilo principal
 * durante el arranque de la app (creación de tablas SQLite + inserts), lo que
 * podía provocar que la app pareciera congelada ("se queda cargando") o incluso
 * disparar un ANR en dispositivos lentos. Ahora el seed se hace de forma
 * asíncrona desde AppNavigation (Compose) mostrando un loader mientras tanto.
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
    }
}

/**
 * Módulo específico de Android
 */
val androidModule = module {
    single { DatabaseDriverFactory(get()) }
}
