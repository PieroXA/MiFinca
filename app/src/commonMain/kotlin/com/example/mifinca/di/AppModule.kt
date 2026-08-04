package com.example.mifinca.di

import com.example.mifinca.data.local.DatabaseDriverFactory
import com.example.mifinca.data.remote.AuthApiService
import com.example.mifinca.data.remote.createHttpClient
import com.example.mifinca.data.repository.AnimalRepository
import com.example.mifinca.data.repository.FarmRepository
import com.example.mifinca.data.repository.HealthEventRepository
import com.example.mifinca.data.repository.InitialDataRepository
import com.example.mifinca.data.repository.UserRepository
import com.example.mifinca.database.MiFincaDatabase
import com.example.mifinca.presentation.screen.animal.AnimalFormViewModel
import com.example.mifinca.presentation.screen.animal.AnimalListViewModel
import com.example.mifinca.presentation.screen.auth.LoginViewModel
import com.example.mifinca.presentation.screen.auth.RegisterViewModel
import com.example.mifinca.presentation.screen.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ── Red ───────────────────────────────────────────────────────────────────
    single { createHttpClient() }
    single { AuthApiService(get()) }

    // ── Base de datos local ───────────────────────────────────────────────────
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        MiFincaDatabase(driver)
    }

    // ── Repositorios locales ──────────────────────────────────────────────────
    single { UserRepository(get()) }
    single { AnimalRepository(get()) }
    single { FarmRepository(get()) }
    single { HealthEventRepository(get()) }
    single { InitialDataRepository(get()) }

    // ── ViewModels ────────────────────────────────────────────────────────────
    viewModel { LoginViewModel(get<AuthApiService>()) }
    viewModel { RegisterViewModel(get<AuthApiService>()) }

    viewModel { (tenantId: String) ->
        DashboardViewModel(
            animalRepository = get(),
            farmRepository = get(),
            healthEventRepository = get(),
            tenantId = tenantId
        )
    }

    viewModel { (tenantId: String) ->
        AnimalListViewModel(
            animalRepository = get(),
            tenantId = tenantId
        )
    }

    viewModel { (tenantId: String, animalId: String?) ->
        AnimalFormViewModel(
            animalRepository = get(),
            farmRepository = get(),
            tenantId = tenantId,
            animalId = animalId
        )
    }
}
