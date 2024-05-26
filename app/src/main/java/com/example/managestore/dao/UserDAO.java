package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.managestore.database.DatabaseHelper;
import com.example.managestore.models.User;

public class UserDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Create (Insert) a new user
    public long createUser(User user) {
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("Password", user.getPassword());
        values.put("AccountName", user.getAccountName());
        values.put("Avatar", user.getAvatar());
        values.put("Address", user.getAddress());
        values.put("Phone", user.getPhone());
        values.put("UserStatus", user.isUserStatus());
        values.put("RoleID", user.getRoleID());

        return database.insert("Users", null, values);
    }

    public boolean checkUser(String username, String password) {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM Users WHERE UserName = ? AND Password = ?", new String[]{username, password});
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
