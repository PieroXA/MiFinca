package com.example.mifinca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.mifinca.presentation.navigation.AppNavigation
import com.example.mifinca.presentation.theme.MiFincaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Edge-to-edge para que el contenido ocupe toda la pantalla
        enableEdgeToEdge()
        // Forzar fondo oscuro en la status bar / nav bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            // darkTheme = true forzado para evitar que Android 12+ override con Dynamic Color
            MiFincaTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}