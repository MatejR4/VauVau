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
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlin.math.*

fun calculateDistanceToRijeka(lat: Double, lon: Double): Double {
    val rijekaLat = 45.327
    val rijekaLon = 14.442
    val r = 6371.0

    val dLat = Math.toRadians(lat - rijekaLat)
    val dLon = Math.toRadians(lon - rijekaLon)

    val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(rijekaLat)) * cos(Math.toRadians(lat)) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return r * c
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    favoritesList: MutableList<AnimalAd>,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onAdClick: (String) -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack

    // --- FIREBASE INTEGRACIJA ---
    val repo = remember { FirestoreRepository() }
    var adsList by remember { mutableStateOf<List<AnimalAd>>(emptyList()) }

    LaunchedEffect(Unit) {
        repo.dohvatiOglase { dohvaceniOglasi ->
            adsList = dohvaceniOglasi
        }
    }

    var showFilterMenu by remember { mutableStateOf(false) }

    val petTypes = listOf("Sve", "Pas", "Mačka", "Ptica", "Kornjača", "Riba", "Hrčak")
    var selectedTypeFilter by remember { mutableStateOf("Sve") }
    var expandedTypeDropdown by remember { mutableStateOf(false) }

    val distanceStepsValues = listOf(25, 50, 75, 100, 150, 200, 2000)
    val distanceStepsLabels = listOf("25 km", "50 km", "75 km", "100 km", "150 km", "200 km", "Sve")
    var sliderPosition by remember { mutableStateOf(6f) }

    val filteredAds = adsList.filter { ad ->
        val distance = calculateDistanceToRijeka(ad.latitude, ad.longitude)
        val matchesType = selectedTypeFilter == "Sve" || ad.type == selectedTypeFilter
        val currentMaxDistance = distanceStepsValues[sliderPosition.toInt()]
        val matchesDistance = currentMaxDistance == 2000 || distance <= currentMaxDistance

        matchesType && matchesDistance
    }

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
                    NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Default.Home, "Početna") }, label = { Text("Početna") }, colors = itemColors)
                    NavigationBarItem(selected = false, onClick = onNavigateToFavorites, icon = { Icon(Icons.Default.Favorite, "Favoriti") }, label = { Text("Favoriti") }, colors = itemColors)
                    NavigationBarItem(selected = false, onClick = onNavigateToProfile, icon = { Icon(Icons.Default.Person, "Profil") }, label = { Text("Profil") }, colors = itemColors)
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Dobrodošli u VauVau!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)

                        Button(
                            onClick = { showFilterMenu = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PastelRed)
                        ) {
                            Icon(Icons.Default.List, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Filteri", color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (filteredAds.isEmpty()) {
                    item {
                        Text(
                            text = "Nema učitanih životinja.",
                            color = TextGray,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }
                } else {
                    items(filteredAds) { ad ->
                        val isFavorited = favoritesList.any { it.id == ad.id }
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
                                    modifier = Modifier.weight(1f).height(115.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = ad.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = textColor)
                                        Text(text = "${ad.type} • ${ad.locationName} (~${distance.toInt()} km)", fontSize = 13.sp, color = TextGray)
                                    }

                                    IconButton(
                                        onClick = {
                                            if (isFavorited) favoritesList.removeAll { it.id == ad.id }
                                            else favoritesList.add(ad)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = null,
                                            tint = PastelRed
                                        )
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

            if (showFilterMenu) {
                ModalBottomSheet(
                    onDismissRequest = { showFilterMenu = false },
                    containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Text("Mogućnosti filtriranja", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor)
                        Spacer(modifier = Modifier.height(24.dp))

                        Text("Vrsta kućnog ljubimca:", fontWeight = FontWeight.Bold, color = textColor)
                        Spacer(modifier = Modifier.height(8.dp))

                        ExposedDropdownMenuBox(
                            expanded = expandedTypeDropdown,
                            onExpandedChange = { expandedTypeDropdown = !expandedTypeDropdown }
                        ) {
                            OutlinedTextField(
                                value = selectedTypeFilter,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTypeDropdown) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PastelRed,
                                    unfocusedBorderColor = Color.LightGray,
                                    focusedTextColor = textColor,
                                    unfocusedTextColor = textColor
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedTypeDropdown,
                                onDismissRequest = { expandedTypeDropdown = false },
                                modifier = Modifier.background(if (isDarkMode) Color(0xFF2D2D2D) else Color.White)
                            ) {
                                petTypes.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type, color = textColor) },
                                        onClick = {
                                            selectedTypeFilter = type
                                            expandedTypeDropdown = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text("Maksimalna udaljenost (od vas):", fontWeight = FontWeight.Bold, color = textColor)
                        val currentDistanceLabel = distanceStepsLabels[sliderPosition.toInt()]

                        Text(currentDistanceLabel, color = PastelRed, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 4.dp))

                        Slider(
                            value = sliderPosition,
                            onValueChange = { sliderPosition = it },
                            valueRange = 0f..6f,
                            steps = 5,
                            colors = SliderDefaults.colors(
                                thumbColor = PastelRed,
                                activeTrackColor = PastelRed,
                                activeTickColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}