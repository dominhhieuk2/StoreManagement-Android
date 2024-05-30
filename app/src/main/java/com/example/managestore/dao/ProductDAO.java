package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.managestore.database.DatabaseHelper;
import com.example.managestore.models.Product;
import com.example.managestore.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertData(List<Product> listProduct) {
        ContentValues values = new ContentValues();
        for (Product product : listProduct) {
            values.put("ProductName", product.getProductName());
            values.put("ProductPrice", product.getProductPrice());
            values.put("ProductStatus", product.isProductStatus());
            values.put("ProductQuantity", product.getProductQuantity());
            values.put("Description", product.getDescription());
            values.put("CategoryID", product.getCategoryID());
            values.put("ProductLink", product.getProductLink());

            database.insert("Product", null, values);
        }
    }

    public long getProductsCount() {
        return database.rawQuery("select * from Product",null).getCount();
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from Product left join Categories on Product.CategoryID = Categories.CategoryID",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("ProductName"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("ProductPrice"));
                int statusNumber = cursor.getInt(cursor.getColumnIndexOrThrow("ProductStatus"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("ProductQuantity"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
                String link = cursor.getString(cursor.getColumnIndexOrThrow("ProductLink"));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));

                boolean status = statusNumber == 1;

                Product product = new Product(name, price, status, quantity, description, link, categoryName);
                productList.add(product);
                cursor.moveToNext();
            }
        }
        return productList;
    }

    public long createProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("productName", product.getProductName());
        values.put("productLink", product.getProductLink());
        values.put("productPrice", product.getProductPrice());
        values.put("productStatus", product.isProductStatus());
        values.put("productQuantity", product.getProductQuantity());
        values.put("description", product.getDescription());
        values.put("categoryID", product.getCategoryID());

        return database.insert("Product", null, values);
    }
}
