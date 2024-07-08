package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.managestore.database.DatabaseHelper;
import com.example.managestore.models.Category;
import com.example.managestore.models.Product;
import com.example.managestore.models.User;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertData(String[] listCategory) {
        ContentValues values = new ContentValues();
        for (String categoryName : listCategory) {
            values.put("CategoryName", categoryName);
            database.insert("Categories", null, values);
        }
    }

    public long createCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("CategoryName", category.getCategoryName());

        return database.insert("Categories", null, values);
    }

    public long getCategoriesCount() {
        return database.rawQuery("select * from Categories",null).getCount();
    }

    public Category getCategoryByName(String categoryName) {
        Cursor cursor = database.query("Categories", null, "CategoryName = ?", new String[]{categoryName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Category category = new Category(
                    cursor.getInt(cursor.getColumnIndexOrThrow("CategoryID")),
                    cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"))
            );
            cursor.close();
            return category;
        }
        return null;
    }
}
