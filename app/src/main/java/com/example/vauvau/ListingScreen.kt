package com.example.vauvau

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ListingScreen(
    adId: String,
    onBack: () -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack
    val sectionBackground = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF5F5F5)

    val repo = remember { FirestoreRepository() }
    var ad by remember { mutableStateOf<AnimalAd?>(null) }

    LaunchedEffect(adId) {
        repo.dohvatiOglase { oglasi ->
            ad = oglasi.find { it.id == adId }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalji oglasa", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Nazad", tint = PastelRed)
                    }
                }
            )
        }
    ) { paddingValues ->
        if (ad != null) {
            val trenutniOglas = ad!!
            val distance = calculateDistanceToRijeka(trenutniOglas.latitude, trenutniOglas.longitude)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(backgroundColor)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = trenutniOglas.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = trenutniOglas.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = "Ime: ${trenutniOglas.name} | Vrsta: ${trenutniOglas.type}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = PastelRed, modifier = Modifier.padding(vertical = 4.dp))
                    Text(text = "Lokacija: ${trenutniOglas.locationName} (~${distance.toInt()} km od vas u Rijeci)", fontSize = 14.sp, color = TextGray)

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Opis", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = trenutniOglas.description, fontSize = 15.sp, color = textColor, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Osobnost", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = trenutniOglas.personality, fontSize = 15.sp, color = textColor, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = sectionBackground),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ZDRAVSTVENO STANJE I PROBLEMI", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PastelRed)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = trenutniOglas.healthIssues, fontSize = 14.sp, color = textColor)
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Oglas se učitava ili nije pronađen...", color = textColor)
            }
        }
    }
}