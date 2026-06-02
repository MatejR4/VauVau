package com.example.vauvau

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    favoritesList: MutableList<AnimalAd>,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onAdClick: (Int, String) -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack

    val allAds = listOf(
        AnimalAd(1, "Oglas životinje 1", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=150"),
        AnimalAd(2, "Oglas životinje 2", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=150"),
        AnimalAd(3, "Oglas životinje 3", "https://images.unsplash.com/photo-1533738363-b7f9aef128ce?w=150")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("VauVau", fontWeight = FontWeight.Bold, color = PastelRed) })
        },
        bottomBar = {
            Surface(color = PastelRed, tonalElevation = 8.dp) {
                NavigationBar(containerColor = Color.Transparent) {
                    val itemColors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent
                    )

                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Default.Home, "Početna") },
                        label = { Text("Početna") },
                        colors = itemColors
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToFavorites,
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
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            item {
                Text("Dobrodošli u VauVau!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(allAds) { ad ->
                val isFavorited = favoritesList.any { it.id == ad.id }

                // Koristimo običan OutlinedCard s Modifier.clickable kako bi izbjegli M3 crash presretače
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onAdClick(ad.id, ad.title) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ad.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = textColor
                            )

                            IconButton(
                                onClick = {
                                    if (isFavorited) {
                                        favoritesList.removeAll { it.id == ad.id }
                                    } else {
                                        favoritesList.add(ad)
                                    }
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorit",
                                    tint = PastelRed
                                )
                            }
                        }

                        AsyncImage(
                            model = ad.imageUrl,
                            contentDescription = "Slika životinje",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}