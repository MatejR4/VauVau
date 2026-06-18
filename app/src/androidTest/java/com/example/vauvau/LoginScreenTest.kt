package com.example.vauvau

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    // Pravilo koje nam omogućuje pokretanje Compose ekrana unutar testa
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginButton_PravilnoReagiraNaKlik() {
        var jePrijavljen = false // Varijabla koja prati je li gumb kliknut

        // 1. Postavljamo sadržaj testa (otvaramo samo LoginScreen)
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = { jePrijavljen = true },
                onNavigateToForgotPassword = {}
            )
        }

        // 2. Simuliramo korisnika koji upisuje podatke
        composeTestRule.onNodeWithText("E-mail").performTextInput("test@vauvau.hr")
        composeTestRule.onNodeWithText("Lozinka").performTextInput("mojalozinka123")

        // 3. Simuliramo klik na gumb
        composeTestRule.onNodeWithText("PRIJAVI SE").performClick()

        // 4. Provjeravamo rezultat (je li varijabla promijenjena u true)
        assertTrue("Klik na gumb 'PRIJAVI SE' nije okinuo onLoginSuccess akciju", jePrijavljen)
    }
}