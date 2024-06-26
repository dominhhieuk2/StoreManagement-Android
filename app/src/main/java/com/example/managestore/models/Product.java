package com.example.managestore.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int productID;
    private String productLink;
    private String productName;
    private double productPrice;
    private boolean productStatus;
    private int productQuantity;
    private String description;
    private int categoryID;
    private String categoryName;

    public Product(String productName, double productPrice, boolean productStatus, int productQuantity, String description, String productLink, int categoryID) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productQuantity = productQuantity;
        this.description = description;
        this.productLink = productLink;
        this.categoryID = categoryID;
    }

    public Product(int productID, String productName, double productPrice, boolean productStatus, int productQuantity, String description, String productLink, String categoryName) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productQuantity = productQuantity;
        this.description = description;
        this.productLink = productLink;
        this.categoryName = categoryName;
    }

    public Product(int productID, String productName, double productPrice, String productLink, String categoryName) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productLink = productLink;
        this.categoryName = categoryName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

