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
fun FavoritesScreen(
    favoritesList: MutableList<AnimalAd>,
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onAdClick: (String) -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack

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
                    NavigationBarItem(selected = false, onClick = onNavigateToHome, icon = { Icon(Icons.Default.Home, "Početna") }, label = { Text("Početna") }, colors = itemColors)
                    NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Default.Favorite, "Favoriti") }, label = { Text("Favoriti") }, colors = itemColors)
                    NavigationBarItem(selected = false, onClick = onNavigateToProfile, icon = { Icon(Icons.Default.Person, "Profil") }, label = { Text("Profil") }, colors = itemColors)
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
                Text("Vaši favoriti", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (favoritesList.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 100.dp), contentAlignment = Alignment.Center) {
                        Text(text = "Trenutno nemate spremljenih oglasa.", color = if (isDarkMode) Color.Gray else TextGray, fontSize = 16.sp)
                    }
                }
            } else {
                items(favoritesList) { ad ->
                    val distance = calculateDistanceToRijeka(ad.latitude, ad.longitude)
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onAdClick(ad.id) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f).height(100.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = ad.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = textColor)
                                    Text(text = "${ad.locationName} (~${distance.toInt()} km)", fontSize = 13.sp, color = TextGray)
                                }
                                IconButton(onClick = { favoritesList.removeAll { it.id == ad.id } }, modifier = Modifier.size(32.dp)) {
                                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = PastelRed)
                                }
                            }
                            AsyncImage(
                                model = ad.imageUrl,
                                contentDescription = null,
                                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}