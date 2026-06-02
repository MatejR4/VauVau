package com.example.vauvau

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    adId: Int,
    adTitle: String,
    onBack: () -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalji oglasa", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Nazad",
                            tint = PastelRed
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = adTitle,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = PastelRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ID oglasa: $adId",
                fontSize = 16.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Ovdje ćemo dodati sve ostale strukturirane podatke za životinju
            Text(
                text = "Ovdje će se nalaziti detaljni podaci o životinji koje naknadno definiraš.",
                fontSize = 16.sp,
                color = textColor,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}