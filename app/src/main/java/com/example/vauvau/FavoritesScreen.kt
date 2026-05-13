package com.example.vauvau

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    // Provjera teme sustava za dosljedan dizajn
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VauVau", fontWeight = FontWeight.Bold, color = PastelRed) }
            )
        },
        bottomBar = {
            Surface(
                color = PastelRed,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                NavigationBar(containerColor = Color.Transparent) {
                    val itemColors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent
                    )

                    // Navigacijske stavke
                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToHome,
                        icon = { Icon(Icons.Default.Home, "Početna") },
                        label = { Text("Početna") },
                        colors = itemColors
                    )

                    NavigationBarItem(
                        selected = true, // Označeno jer smo na favoritima
                        onClick = { /* Trenutni ekran */ },
                        icon = { Icon(Icons.Default.Favorite, "Favoriti") },
                        label = { Text("Favoriti") },
                        colors = itemColors
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToProfile,
                        icon = { Icon(Icons.Default.Person, "Profil") },
                        label = { Text("Profil") },
                        colors = itemColors
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToSettings,
                        icon = { Icon(Icons.Default.Settings, "Postavke") },
                        label = { Text("Postavke") },
                        colors = itemColors
                    )
                }
            }
        }
    ) { paddingValues ->
        // Sadržaj ekrana s favoritima
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Text(
                text = "Ovdje se nalaze vaši favoriti",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Trenutno nemate spremljenih oglasa.",
                color = if (isDarkMode) Color.Gray else TextGray
            )
        }
    }
}