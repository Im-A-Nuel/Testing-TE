package org.tugaste;

public class BMIService {

    // Menghitung BMI berdasarkan berat badan dan tinggi badan
    public static double hitungBMI(double beratBadan, double tinggiBadan) {
        if (beratBadan <= 0 || tinggiBadan <= 0) {
            throw new IllegalArgumentException("Berat badan dan tinggi badan harus lebih besar dari 0");
        }
        return beratBadan / (tinggiBadan * tinggiBadan);
    }

    // Mengklasifikasikan BMI
    public static String klasifikasiBMI(double bmi) {
        if (bmi < 18.5) {
            return "Kurang berat badan";
        } else if (bmi < 24.9) {
            return "Normal";
        } else if (bmi < 29.9) {
            return "Kelebihan berat badan";
        } else {
            return "Obesitas";
        }
    }

    public static void main(String[] args) {
        double x = hitungBMI(10, 1.6);
        System.out.println(x);
        System.out.println(klasifikasiBMI(x));
    }

}
