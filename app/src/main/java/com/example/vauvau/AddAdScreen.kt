package com.example.vauvau

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAdScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack

    // Stanja za tekstualna polja
    var title by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var personality by remember { mutableStateOf("") }
    var healthIssues by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    // Novo stanje za URL slike s interneta
    var imageUrl by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dodaj Oglas", fontWeight = FontWeight.Bold, color = PastelRed) }
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

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToHome,
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
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Default.Add, "Dodaj") },
                        label = { Text("Dodaj") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Unesite podatke o životinji", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor)

            Text("Pregled slike", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = textColor)

            // Okvir koji automatski prikazuje sliku čim korisnik unese ispravan URL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF0F0F0))
                    .border(1.dp, if (isDarkMode) Color.DarkGray else Color.LightGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Pregled unesene slike",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Add, contentDescription = "Nema slike", tint = PastelRed, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Ovdje će se prikazati slika nakon unosa URL-a", color = if (isDarkMode) Color.Gray else TextGray, fontSize = 14.sp)
                    }
                }
            }

            // Polje za unos URL-a slike s interneta
            OutlinedTextField(
                enabled = !isSaving,
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL slike s interneta") },
                modifier = Modifier.fillMaxWidth()
            )
            // Tražena mala napomena ispod kućice
            Text(
                text = "* Molimo pronađite sliku životinje na internetu koja najbliže reprezentira životinju za koju radite oglas te ovdje zalijepite njezin link.",
                color = if (isDarkMode) Color.LightGray.copy(alpha = 0.6f) else Color.Gray,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            // Ostala tekstualna polja
            OutlinedTextField(enabled = !isSaving, value = title, onValueChange = { title = it }, label = { Text("Naslov oglasa") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(enabled = !isSaving, value = name, onValueChange = { name = it }, label = { Text("Ime životinje") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(enabled = !isSaving, value = type, onValueChange = { type = it }, label = { Text("Vrsta (npr. Pas, Mačka)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(enabled = !isSaving, value = locationName, onValueChange = { locationName = it }, label = { Text("Lokacija (Grad)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(enabled = !isSaving, value = description, onValueChange = { description = it }, label = { Text("Opis") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            OutlinedTextField(enabled = !isSaving, value = personality, onValueChange = { personality = it }, label = { Text("Osobnost") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(enabled = !isSaving, value = healthIssues, onValueChange = { healthIssues = it }, label = { Text("Zdravstveno stanje i problemi") }, modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(enabled = !isSaving, value = latitude, onValueChange = { latitude = it }, label = { Text("Latitude") }, modifier = Modifier.weight(1f))
                OutlinedTextField(enabled = !isSaving, value = longitude, onValueChange = { longitude = it }, label = { Text("Longitude") }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Validacija: provjeravamo je li upisan URL i ostala osnovna polja
                    if (imageUrl.isBlank()) {
                        Toast.makeText(context, "Molimo unesite URL slike s interneta!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (title.isEmpty() || name.isEmpty() || type.isEmpty() || locationName.isEmpty()) {
                        Toast.makeText(context, "Popunite osnovna polja (Naslov, Ime, Vrsta, Lokacija)!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isSaving = true

                    // Povezivanje s Firestore bazom podataka
                    val db = FirebaseFirestore.getInstance()
                    val documentRef = db.collection("oglasi").document()

                    val noviOglas = AnimalAd(
                        id = documentRef.id,
                        title = title,
                        name = name,
                        type = type,
                        locationName = locationName,
                        latitude = latitude.toDoubleOrNull() ?: 0.0,
                        longitude = longitude.toDoubleOrNull() ?: 0.0,
                        description = description,
                        personality = personality,
                        healthIssues = healthIssues,
                        imageUrl = imageUrl.trim() // Sprema se čisti tekstualni URL link u Firestore!
                    )

                    // Izravan upis objekta u Firestore
                    documentRef.set(noviOglas)
                        .addOnSuccessListener {
                            isSaving = false
                            Toast.makeText(context, "Oglas uspješno objavljen u Firestore!", Toast.LENGTH_SHORT).show()
                            onSaveSuccess()
                        }
                        .addOnFailureListener { e ->
                            isSaving = false
                            Toast.makeText(context, "Greška pri spremanju: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = PastelRed)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Objavi oglas", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}