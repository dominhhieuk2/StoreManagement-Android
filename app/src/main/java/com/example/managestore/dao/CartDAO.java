package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.managestore.database.DatabaseHelper;
import com.example.managestore.models.Cart;
import com.example.managestore.models.CartItem;
import com.example.managestore.models.Product;
import com.example.managestore.models.User;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CartDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addToCart(int productId, int userId, int quantity) {
        ContentValues values = new ContentValues();

        Cursor cursor = database.query("Cart", null, "ProductID = ? and UsersID = ?", new String[]{productId+"", userId+""}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Cart cart = new Cart(cursor.getInt(cursor.getColumnIndexOrThrow("CartID")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("ProductID")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("Quantity")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("UsersID")));

            values.put("ProductID", productId);
            values.put("UsersID", userId);
            values.put("Quantity", quantity + cart.getQuantity());
            database.update("Cart", values, "ProductID = ? and UsersID = ?", new String[]{productId+"", userId+""});
            cursor.close();
        }
        else {
            values.put("ProductID", productId);
            values.put("UsersID", userId);
            values.put("Quantity", quantity);
            database.insert("Cart", null, values);
        }
    }

    public void removeFromCart(int productId, int userId) {
        database.delete("Cart", "ProductID = ? and UsersID = ?", new String[]{productId+"", userId+""});
    }

    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from Cart left join Product on Cart.ProductID = Product.ProductID left join Categories on Product.CategoryID = Categories.CategoryID where Cart.UsersID = ?",new String[]{userId+""});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ProductID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("ProductName"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("ProductPrice"));
                String link = cursor.getString(cursor.getColumnIndexOrThrow("ProductLink"));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity"));

                CartItem cartItem = new CartItem(id, name, price, link, categoryName, quantity);
                cartItems.add(cartItem);
                cursor.moveToNext();
            }
        }

        return cartItems;
    }
}
