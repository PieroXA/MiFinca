package com.example.mifinca.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mifinca.presentation.screen.animal.AnimalDetailScreen
import com.example.mifinca.presentation.screen.animal.AnimalFormScreen
import com.example.mifinca.presentation.screen.animal.AnimalListScreen
import com.example.mifinca.presentation.screen.auth.LoginScreen
import com.example.mifinca.presentation.screen.dashboard.DashboardScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    // Navigate to Register
                }
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                onNavigateToAnimals = {
                    navController.navigate("animal_list")
                },
                onNavigateToSync = {
                    // Navigate to Sync conflict resolution
                }
            )
        }
        
        composable("animal_list") {
            AnimalListScreen(
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
