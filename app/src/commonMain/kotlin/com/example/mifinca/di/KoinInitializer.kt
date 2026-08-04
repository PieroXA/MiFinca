package com.example.mifinca.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Inicializa Koin desde código común.
 * - Android: llamado desde MiFincaApplication con androidModule
 * - iOS: llamado desde SwiftUI/AppDelegate con iosModule
 *
 * platformModuleDeclaration permite que cada plataforma inyecte
 * su propio módulo (SqlDriver, etc.) antes de appModule.
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(appModule)
    }
}
