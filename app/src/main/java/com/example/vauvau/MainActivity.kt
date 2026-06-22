package com.example.vauvau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

val BackgroundWhite = Color(0xFFFAFAFA)
val PastelRed = Color(0xFFFF9AA2)
val TextBlack = Color(0xFF2D2D2D)
val TextGray = Color(0xFF888888)

// Prilagođeni model podataka za Firebase
data class AnimalAd(
    var id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val type: String = "",
    val locationName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val description: String = "",
    val healthIssues: String = "",
    val personality: String = ""
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val favoritesList = remember { mutableStateListOf<AnimalAd>() }

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateToForgotPassword = { navController.navigate("forgot_password") }
                        )
                    }

                    composable("forgot_password") {
                        ForgotPasswordScreen(onBack = { navController.popBackStack() })
                    }

                    composable("home") {
                        HomeScreen(
                            favoritesList = favoritesList,
                            onNavigateToFavorites = { navController.navigate("favorites") },
                            onNavigateToAddAd = { navController.navigate("add_ad") }, // POPRAVLJENO OVDJE
                            onNavigateToProfile = { navController.navigate("profile") },
                            onAdClick = { adId ->
                                navController.navigate("listing/$adId")
                            }
                        )
                    }

                    composable("favorites") {
                        FavoritesScreen(
                            favoritesList = favoritesList,
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToAddAd = { navController.navigate("add_ad") }, // POPRAVLJENO OVDJE
                            onNavigateToProfile = { navController.navigate("profile") },
                            onAdClick = { adId ->
                                navController.navigate("listing/$adId")
                            }
                        )
                    }

                    composable("add_ad") {
                        AddAdScreen(
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToFavorites = { navController.navigate("favorites") },
                            onNavigateToProfile = { navController.navigate("profile") },
                            onSaveSuccess = {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = false }
                                }
                            }
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToAddAd = { navController.navigate("add_ad") },
                            onNavigateToFavorites = { navController.navigate("favorites") }
                        )
                    }

                    composable(
                        route = "listing/{adId}",
                        arguments = listOf(navArgument("adId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val adId = backStackEntry.arguments?.getString("adId") ?: ""
                        ListingScreen(
                            adId = adId,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}