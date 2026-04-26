package com.example.vauvau

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToSettings: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VauVau", fontWeight = FontWeight.Bold, color = PastelRed) }
            )
        },
        bottomBar = {
            // Surface djeluje kao kontejner koji drži boju pozadine
            Surface(
                color = PastelRed, // Ovdje postavi točnu boju koju želiš
                tonalElevation = 8.dp,      // Ovo dodaje suptilnu sjenu za "odvajanje" od ekrana
                shadowElevation = 8.dp
            ) {
                NavigationBar(
                    containerColor = Color.Transparent, // Ovdje stavljamo Transparent
                    contentColor = Color.Black          // Boja ikona
                ) {
                    val itemColors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f), // Malo prozirnije za neaktivne
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent // Uklanja onaj "oblačić" iza ikone
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { /* TODO */ },
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoriti") },
                        label = { Text("Favoriti") },
                        colors = itemColors
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { /* TODO */ },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                        label = { Text("Profil") },
                        colors = itemColors
                    )
                    NavigationBarItem(
                        selected = true,
                        onClick = { onNavigateToSettings() },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Postavke") },
                        label = { Text("Postavke") },
                        colors = itemColors
                    )
                }
            }
        }
    ) { paddingValues ->
        // Sadržaj početnog ekrana
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundWhite)
                .padding(16.dp)
        ) {
            Text("Dobrodošli u VauVau!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Ovdje će uskoro biti izlog životinja.", color = TextGray)
        }
    }
}