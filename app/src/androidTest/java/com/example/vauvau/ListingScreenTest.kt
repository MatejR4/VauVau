package com.example.vauvau

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class ListingScreenTest {

    // Pravilo koje nam omogućava podizanje Compose ekrana u izolaciji
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listingScreen_displaysCorrectAnimalDataFromGlobalList() {
        // Pokrećemo SAMO ListingScreen, ne cijelu aplikaciju (to je poanta Medium testa)
        composeTestRule.setContent {
            // Prosljeđujemo ID 1 (znamo da je to Pas Rex iz globalAdsList)
            ListingScreen(adId = 1, onBack = {})
        }

        // Testiramo jesu li se podaci iz baze (liste) ispravno proslijedili u UI
        // Provjeravamo renderira li se naslov oglasa
        composeTestRule.onNodeWithText("Veseli Rex traži dom").assertIsDisplayed()

        // Provjeravamo prikazuje li se naslov sekcije za zdravlje
        composeTestRule.onNodeWithText("ZDRAVSTVENO STANJE I PROBLEMI").assertIsDisplayed()

        // Provjeravamo je li UI učitao ispravan tekst o bolestima
        composeTestRule.onNodeWithText("Nema poznatih bolesti, cijepljen.").assertIsDisplayed()
    }
}
