package com.example.mifinca

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.example.mifinca.data.local.DatabaseDriverFactory
import com.example.mifinca.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MiFincaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MiFincaApplication)
            modules(
                androidModule, // primero: provee el SqlDriver
                appModule      // segundo: usa el SqlDriver para MiFincaDatabase
            )
        }
    }
}

/**
 * Módulo Android — provee el SqlDriver para SQLDelight.
 * appModule (commonMain) recibe el SqlDriver via get() para crear MiFincaDatabase.
 */
val androidModule = module {
    single<SqlDriver> { DatabaseDriverFactory(get()).createDriver() }
}
