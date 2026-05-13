package com.example.vauvau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Definiranje palete boja za aplikaciju
val BackgroundWhite = Color(0xFFFAFAFA)
val PastelRed = Color(0xFFFF9AA2)
val TextBlack = Color(0xFF2D2D2D)
val TextGray = Color(0xFF888888)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // navController upravlja svim promjenama ekrana u aplikaciji
                val navController = rememberNavController()

                // NavHost definira rute (ekrane) i početnu točku aplikacije
                NavHost(navController = navController, startDestination = "login") {

                    composable("login") {
                        LoginScreen(onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        })
                    }

                    composable(route = "home") {
                        HomeScreen(
                            onNavigateToFavorites = { navController.navigate("favorites") },
                            onNavigateToProfile = { navController.navigate("profile") }, // Popravljeno ime ovdje
                            onNavigateToSettings = { navController.navigate("settings") }
                        )
                    }

                    composable(route = "favorites") {
                        FavoritesScreen(
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToProfile = { navController.navigate("profile") }, // Popravljeno ime ovdje
                            onNavigateToSettings = { navController.navigate("settings") }
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToFavorites = { navController.navigate("favorites") },
                            onNavigateToSettings = { navController.navigate("settings") }
                        )
                    }

                    composable("settings") {
                        SettingsScreen(onNavigateBack = {
                            navController.popBackStack()
                        })
                    }

                }
            }
        }
    }
}