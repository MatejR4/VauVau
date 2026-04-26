package com.example.vauvau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        })
                    }
                    // U MainActivity.kt, unutar NavHost-a:

                    composable("home") {
                        HomeScreen(onNavigateToSettings = {
                            navController.navigate("settings") // Ovo vodi na SettingsScreen
                        })
                    }

                    composable("settings") {
                        SettingsScreen(onNavigateBack = {
                            navController.popBackStack() // Ovo automatski vraća korisnika na prethodni ekran
                        })
                    }
                }
            }
        }
    }
}