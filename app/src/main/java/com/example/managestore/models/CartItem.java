package com.example.managestore.models;

public class CartItem extends Product{
    private int quantity;

    public CartItem(int productID, String productName, double productPrice, String productLink, String categoryName, int quantity) {
        super(productID, productName, productPrice, productLink, categoryName);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
