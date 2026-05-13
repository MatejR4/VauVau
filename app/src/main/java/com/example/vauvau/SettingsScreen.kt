package com.example.vauvau

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Postavke") },
                navigationIcon = {
                    // IconButton koji koristi proslijeđenu funkciju za povratak na prethodni ekran
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Nazad"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Popis postavki organiziran u kolonu
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            SettingItem(icon = Icons.Default.Person, title = "Moj profil")
            SettingItem(icon = Icons.Default.Notifications, title = "Obavijesti")
            SettingItem(icon = Icons.Default.Settings, title = "Privatnost")
        }
    }
}

// Pomoćna funkcija za prikaz pojedinačne stavke postavki (Row struktura)
@Composable
fun SettingItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PastelRed,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        // Koristimo weight(1f) kako bi tekst zauzeo sav preostali prostor između ikona
        Text(text = title, fontSize = 18.sp, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextGray
        )
    }
    // Horizontalna linija za razdvajanje stavki
    HorizontalDivider(thickness = 0.5.dp)
}