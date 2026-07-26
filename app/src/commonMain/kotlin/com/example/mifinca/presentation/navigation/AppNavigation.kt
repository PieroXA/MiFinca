package com.example.mifinca.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mifinca.data.repository.InitialDataRepository
import com.example.mifinca.presentation.screen.animal.AnimalDetailScreen
import com.example.mifinca.presentation.screen.animal.AnimalFormScreen
import com.example.mifinca.presentation.screen.animal.AnimalListScreen
import com.example.mifinca.presentation.screen.auth.LoginScreen
import com.example.mifinca.presentation.screen.dashboard.DashboardScreen
import org.koin.compose.koinInject

@Composable
fun AppNavigation() {
    // La inicialización de datos demo se hace de forma asíncrona y NO bloqueante.
    // Antes se hacía con runBlocking en el hilo principal (Application.onCreate),
    // lo que podía congelar la UI o dejar la pantalla de login "cargando" si la
    // creación/seed de SQLite tardaba. Ahora se espera aquí, en una corrutina de
    // Compose, mostrando un loader simple mientras tanto.
    val initialDataRepository = koinInject<InitialDataRepository>()
    var isAppReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            initialDataRepository.initializeDefaultData()
        } catch (e: Exception) {
            // No bloqueamos el arranque de la app si el seed falla;
            // el usuario simplemente no tendrá los datos demo precargados.
        } finally {
            isAppReady = true
        }
    }

    if (!isAppReady) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { tenantId ->
                    navController.navigate("dashboard/$tenantId") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    // Navigate to Register
                }
            )
        }

        composable("dashboard/{tenantId}") { backStackEntry ->
            val tenantId = backStackEntry.arguments?.getString("tenantId") ?: ""
            DashboardScreen(
                tenantId = tenantId,
                onNavigateToAnimals = {
                    navController.navigate("animal_list/$tenantId")
                },
                onNavigateToSync = {
                    // Navigate to Sync conflict resolution
                }
            )
        }

        composable("animal_list/{tenantId}") { backStackEntry ->
            val tenantId = backStackEntry.arguments?.getString("tenantId") ?: ""
            AnimalListScreen(
                tenantId = tenantId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { tag ->
                    navController.navigate("animal_detail/$tag")
                }
            )
        }

        composable("animal_detail/{tag}") { backStackEntry ->
            val tag = backStackEntry.arguments?.getString("tag") ?: ""
            AnimalDetailScreen(
                animalId = tag,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("animal_form") {
            AnimalFormScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
