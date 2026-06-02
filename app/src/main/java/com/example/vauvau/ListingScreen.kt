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
import androidx.compose.runtime.Composable
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
    adId: Int,
    onBack: () -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack
    val sectionBackground = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF5F5F5)

    // Pronalazak oglasa iz globalne liste
    val ad = globalAdsList.find { it.id == adId }

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
            val distance = calculateDistanceToRijeka(ad.latitude, ad.longitude)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(backgroundColor)
                    .verticalScroll(rememberScrollState())
            ) {
                // Velika istaknuta slika na vrhu
                AsyncImage(
                    model = ad.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(24.dp)) {
                    // Naslov i ime životinje
                    Text(text = ad.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = "Ime: ${ad.name} | Vrsta: ${ad.type}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = PastelRed, modifier = Modifier.padding(vertical = 4.dp))
                    Text(text = "Lokacija: ${ad.locationName} (~${distance.toInt()} km od vas u Rijeci)", fontSize = 14.sp, color = TextGray)

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Kratki opis
                    Text(text = "Opis", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = ad.description, fontSize = 15.sp, color = textColor, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Osobnost
                    Text(text = "Osobnost", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = ad.personality, fontSize = 15.sp, color = textColor, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Posebno istaknuta sekcija: Problemi i bolesti
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = sectionBackground),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ZDRAVSTVENO STANJE I PROBLEMI", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PastelRed)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = ad.healthIssues, fontSize = 14.sp, color = textColor)
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Oglas nije pronađen.", color = textColor)
            }
        }
    }
}