package com.example.vauvau

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    // Pravilo koje pokreće cijelu MainActivity klasu (i cijeli navigacijski sustav)
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun e2e_TestiranjeNavigacijeOdPrijaveDoProfila() {
        // 1. Aplikacija se pali na Login ekranu. Provjeravamo je li vidljiv naslov.
        composeTestRule.onNodeWithText("Dobrodošli").assertIsDisplayed()

        // 2. Klikamo na gumb za prijavu kako bi nas navigacija prebacila
        composeTestRule.onNodeWithText("PRIJAVI SE").performClick()

        // 3. Provjeravamo jesmo li došli na HomeScreen (početnu stranicu)
        composeTestRule.onNodeWithText("Dobrodošli u VauVau!").assertIsDisplayed()

        // 4. Klikamo na ikonu "Profil" u donjoj navigacijskoj traci
        composeTestRule.onNodeWithText("Profil").performClick()

        // 5. Provjeravamo je li se otvorio ProfileScreen
        composeTestRule.onNodeWithText("OSOBNI PODACI").assertIsDisplayed()
    }
}