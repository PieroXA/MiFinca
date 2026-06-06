package com.example.mifinca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mifinca.presentation.navigation.AppNavigation
import com.example.mifinca.presentation.theme.MiFincaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiFincaTheme {
                AppNavigation()
            }
        }
    }
}