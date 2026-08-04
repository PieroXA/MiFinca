package com.example.mifinca.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.mifinca.presentation.screen.auth.RegisterScreen
import com.example.mifinca.presentation.screen.dashboard.DashboardScreen
import com.example.mifinca.presentation.theme.BackgroundDark
import com.example.mifinca.presentation.theme.RosePrimary
import org.koin.compose.koinInject

@Composable
fun AppNavigation() {
    val initialDataRepository = koinInject<InitialDataRepository>()
    var isAppReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            initialDataRepository.initializeDefaultData()
        } catch (_: Exception) {
            // No bloqueamos si el seed falla
        } finally {
            isAppReady = true
        }
    }

    if (!isAppReady) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = RosePrimary)
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
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { tenantId ->
                    navController.navigate("dashboard/$tenantId") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("dashboard/{tenantId}") { backStackEntry ->
            val tenantId = backStackEntry.arguments?.getString("tenantId") ?: ""
            DashboardScreen(
                tenantId          = tenantId,
                onNavigateToAnimals = { navController.navigate("animal_list/$tenantId") },
                onNavigateToSync    = { /* TODO */ }
            )
        }

        composable("animal_list/{tenantId}") { backStackEntry ->
            val tenantId = backStackEntry.arguments?.getString("tenantId") ?: ""
            AnimalListScreen(
                tenantId         = tenantId,
                onNavigateBack   = { navController.popBackStack() },
                onNavigateToDetail = { id -> navController.navigate("animal_detail/$id") }
            )
        }

        composable("animal_detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            AnimalDetailScreen(
                animalId       = id,
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
