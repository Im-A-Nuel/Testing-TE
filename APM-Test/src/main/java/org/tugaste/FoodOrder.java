package org.tugaste;

public class FoodOrder {

    private UserAccount userAccount;
    private FoodDeliveryService foodDeliveryService;

    public FoodOrder(UserAccount userAccount, FoodDeliveryService foodDeliveryService) {
        this.userAccount = userAccount;
        this.foodDeliveryService = foodDeliveryService;
    }

    public void placeOrder(String foodItem, double price, String address) {

        if (userAccount.hasSufficientBalance(price)) {
            foodDeliveryService.deliverFood(foodItem, address);
        } else {
            System.out.println("Saldo tidak cukup untuk melakukan pemesanan.");
        }

    }
}
