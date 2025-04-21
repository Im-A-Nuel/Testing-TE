package org.tugaste;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BMIServiceTest {

    @ParameterizedTest(name = "berat={0}, tinggi={1} → BMI={2}")
    @CsvSource({
            // EP
            "60, 1.6, 23.44",
            "45, 1.5, 20.00",
            "90, 1.8, 27.78",
            // BVA – batas bawah dan atas tinggi
            "50, 0.5, 200.00",
            "50, 2.5, 8.00",
            // BVA – batas bawah dan atas berat
            "10, 1.6, 3.91",
            "300, 1.6, 117.19"
    })
    void testHitungBMI(double berat, double tinggi, double expected) {
        double result = BMIService.hitungBMI(berat, tinggi);
        assertEquals(expected, Math.round(result * 100.0) / 100.0);
    }


}