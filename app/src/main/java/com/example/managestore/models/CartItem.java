package com.example.managestore.models;

public class CartItem extends Product{
    private int quantity;
    private int cartID;

    public CartItem(int cartID, int productID, String productName, double productPrice, String productLink, String categoryName, int quantity) {
        super(productID, productName, productPrice, productLink, categoryName);
        this.quantity = quantity;
        this.cartID = cartID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCartID() {
        return cartID;
    }
}
