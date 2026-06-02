package com.example.vauvau

import org.junit.Assert.assertEquals
import org.junit.Test

class DistanceCalculatorTest {

    @Test
    fun calculateDistanceToRijeka_isCorrect() {
        // Test 1: Udaljenost od Rijeke (45.327, 14.442) do same Rijeke mora biti 0 km
        val distanceSame = calculateDistanceToRijeka(45.327, 14.442)
        // Treći argument (0.1) je dopušteno odstupanje u decimalama
        assertEquals(0.0, distanceSame, 0.1)

        // Test 2: Udaljenost od Rijeke do Zagreba (koordinate: 45.815, 15.981)
        // Očekivana stvarna zračna udaljenost je oko 131 km
        val distanceZagreb = calculateDistanceToRijeka(45.815, 15.981)
        // Dopuštamo odstupanje od 2 kilometra zbog matematičkih zaokruživanja
        assertEquals(131.0, distanceZagreb, 2.0)
    }
}
