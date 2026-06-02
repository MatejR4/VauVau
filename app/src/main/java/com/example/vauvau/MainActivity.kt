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

// Globalno definirane boje i klasa vidljive svuda
val BackgroundWhite = Color(0xFFFAFAFA)
val PastelRed = Color(0xFFFF9AA2)
val TextBlack = Color(0xFF2D2D2D)
val TextGray = Color(0xFF888888)

data class AnimalAd(val id: Int, val title: String, val imageUrl: String)

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
                            onNavigateToProfile = { navController.navigate("profile") },
                            onAdClick = { adId, adTitle ->
                                navController.navigate("listing/$adId/$adTitle")
                            }
                        )
                    }

                    composable("favorites") {
                        FavoritesScreen(
                            favoritesList = favoritesList,
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToProfile = { navController.navigate("profile") },
                            onAdClick = { adId, adTitle ->
                                navController.navigate("listing/$adId/$adTitle")
                            }
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            onNavigateToHome = { navController.navigate("home") },
                            onNavigateToFavorites = { navController.navigate("favorites") }
                        )
                    }

                    composable(
                        route = "listing/{adId}/{adTitle}",
                        arguments = listOf(
                            navArgument("adId") { type = NavType.IntType },
                            navArgument("adTitle") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val adId = backStackEntry.arguments?.getInt("adId") ?: 0
                        val adTitle = backStackEntry.arguments?.getString("adTitle") ?: ""
                        ListingScreen(
                            adId = adId,
                            adTitle = adTitle,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}