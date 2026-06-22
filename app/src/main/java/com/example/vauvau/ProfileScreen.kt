package com.example.vauvau

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToAddAd: () -> Unit,
    loggedInUsername: String = "",
    loggedInEmail: String = ""
) {
    val isDarkMode = isSystemInDarkTheme()
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else BackgroundWhite
    val textColor = if (isDarkMode) Color.White else TextBlack
    val cardBackground = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF0F0F0)

    var username by remember {
        mutableStateOf(if (loggedInUsername.isNotBlank()) loggedInUsername else "Korisnik123")
    }
    var emailState by remember {
        mutableStateOf(if (loggedInEmail.isNotBlank()) loggedInEmail else "korisnik@vauvau.com")
    }

    var showNameDialog by remember { mutableStateOf(false) }
    var tempUsername by remember { mutableStateOf("") }

    var showEmailDialog by remember { mutableStateOf(false) }
    var tempEmail by remember { mutableStateOf("") }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Promijeni korisničko ime", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempUsername,
                    onValueChange = { tempUsername = it },
                    label = { Text("Novo ime") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tempUsername.isNotBlank()) {
                            username = tempUsername
                        }
                        showNameDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PastelRed)
                ) {
                    Text("Spremi", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }) {
                    Text("Odustani", color = TextGray)
                }
            }
        )
    }

    if (showEmailDialog) {
        AlertDialog(
            onDismissRequest = { showEmailDialog = false },
            title = { Text("Promijeni e-mail adresu", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempEmail,
                    onValueChange = { tempEmail = it },
                    label = { Text("Novi e-mail") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tempEmail.isNotBlank()) {
                            emailState = tempEmail
                        }
                        showEmailDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PastelRed)
                ) {
                    Text("Spremi", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEmailDialog = false }) {
                    Text("Odustani", color = TextGray)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Moj Profil", fontWeight = FontWeight.Bold, color = PastelRed) }
            )
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
                        selected = false,
                        onClick = onNavigateToAddAd,
                        icon = { Icon(Icons.Default.Add, "Dodaj") },
                        label = { Text("Dodaj") },
                        colors = itemColors
                    )
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
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
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "OSOBNI PODACI",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PastelRed,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = PastelRed,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = username,
                            fontSize = 20.sp,
                            color = textColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                tempUsername = username
                                showNameDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Uredi ime",
                                tint = TextGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = PastelRed,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = emailState,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.LightGray else TextGray,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                tempEmail = emailState
                                showEmailDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Uredi e-mail",
                                tint = TextGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}