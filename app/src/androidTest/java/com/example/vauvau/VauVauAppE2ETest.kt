package com.example.vauvau

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class VauVauAppE2ETest {

    // createAndroidComposeRule podiže cijelu aplikaciju iz početne aktivnosti (MainActivity)
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun completeUserFlow_loginToHomeToFilterAndProfile() {
        // 1. KORAK: Prijava
        // Aplikacija se pali na Login ekranu. Pronalazimo gumb za prijavu i klikamo ga.
        composeTestRule.onNodeWithText("PRIJAVI SE").performClick()

        // 2. KORAK: Početni ekran (Home)
        // Provjeravamo jesmo li uspješno prebačeni na HomeScreen
        composeTestRule.onNodeWithText("Dobrodošli u VauVau!").assertIsDisplayed()

        // 3. KORAK: Otvaranje filtera
        // Pronalazimo gumb "Filteri" i simuliramo klik na njega
        composeTestRule.onNodeWithText("Filteri").performClick()

        // Provjeravamo je li se donji izbornik s filterima uspješno otvorio
        composeTestRule.onNodeWithText("Mogućnosti filtriranja").assertIsDisplayed()

        // 4. KORAK: Navigacija preko donje trake
        // Simuliramo klik na ikonu "Profil" u donjem navigacijskom meniju
        composeTestRule.onNodeWithText("Profil").performClick()

        // Provjeravamo jesmo li uspješno stigli na ekran profila
        composeTestRule.onNodeWithText("OSOBNI PODACI").assertIsDisplayed()
    }
}
