package org.tugaste;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BMIServiceTest {

    // parameterize test untuk EP & BVA
    @ParameterizedTest(name = "berat={0}, tinggi={1} → BMI={2}")
    @CsvSource({
            // EP
            "10, 1.6, 3.91",    // Berat batas bawah
            "300, 1.6, 117.19", // Berat batas atas
            "50, 0.5, 200.00",  // Tinggi batas bawah
            "50, 2.5, 8.00",    // Tinggi batas atas

            // nilai valid
            "60, 1.8, 18.52",
            // EP
            "45, 1.5, 20.00",
            "60, 1.6, 23.44",
            "90, 1.8, 27.78"
    })
    void testHitungBMI(double berat, double tinggi, double expected) {
        double result = BMIService.hitungBMI(berat, tinggi);
        assertEquals(expected, Math.round(result * 100.0) / 100.0);
    }

    // test berat negatif dan berat badan untuk exception
    @Test
    @DisplayName("Jika berat negatif → lempar IllegalArgumentException")
    void testBeratNegatif() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                BMIService.hitungBMI(-1, 1.6));
        assertEquals("Berat badan dan tinggi badan harus lebih besar dari 0", ex.getMessage());

    }

    // test berat negatif dan berat badan untuk exception
    @Test
    @DisplayName("Jika tinggi negatif → lempar IllegalArgumentException")
    void testTinggiNegatif() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                BMIService.hitungBMI(50, -1));
        assertEquals("Berat badan dan tinggi badan harus lebih besar dari 0", ex.getMessage());

    }

    // test dihentikan
    @ParameterizedTest(name = "Berat={0}, Tinggi={1} → Test dihentikan")
    @DisplayName("Jika berat/tinggi = 0, test akan dihentikan")
    @CsvSource({
            "0, 1.6",
            "60, 0",
            "0, 0"
    })
    void testBeratAtauTinggiNol(double berat, double tinggi) {
        assertThrows(IllegalArgumentException.class, () ->
                BMIService.hitungBMI(berat, tinggi));
    }

}