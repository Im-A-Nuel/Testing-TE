package org.tugaste;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class FoodOrderTest {

    // 71220872 - Nathanael Santoso
    // 71220927 - Imanuel P. A Faot

    private UserAccount userAccount;
    private FoodDeliveryService foodDeliveryService;
    private FoodOrder foodOrder;

    @BeforeEach
    void setUp(){
        userAccount = mock(UserAccount.class);
        foodDeliveryService = mock(FoodDeliveryService.class);
        foodOrder = new FoodOrder(userAccount, foodDeliveryService);
    }

    @Test
    @DisplayName("Test Place Order : Sukses (Saldo Cukup)")
    void testPlaceOrder(){

        String foodItem = "Sosis";
        double price = 100000;
        String address = "Jl.Solo";

        // stub
        when(userAccount.hasSufficientBalance(price)).thenReturn(true);

        // panggil
        foodOrder.placeOrder(foodItem, price, address);

        // verify & assert
        verify(userAccount).hasSufficientBalance(price); // verifikasi cek saldo
        verify(foodDeliveryService).deliverFood(foodItem, address);

    }


    @Test
    @DisplayName("Test Place Order : Gagal (Saldo Tidak Mencukupi)")
    void testPlaceOrderFail(){

        String foodItem = "Tempe";
        double price = 10000;
        String address = "Jl.Solo";

        // stub
        when(userAccount.hasSufficientBalance(price)).thenReturn(false);

        // panggil
        foodOrder.placeOrder(foodItem, price, address);

        // verify & assert
        verify(userAccount).hasSufficientBalance(price); // verifikasi cek saldo
        verify(foodDeliveryService, never()).deliverFood(foodItem, address); // tidak ada pengiriman
    }
}