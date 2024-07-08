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

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("Password", user.getPassword());
        values.put("AccountName", user.getAccountName());
        values.put("Avatar", user.getAvatar());
        values.put("Address", user.getAddress());
        values.put("Phone", user.getPhone());
        values.put("UserStatus", user.isUserStatus());
        values.put("RoleID", user.getRoleID());

        int rowsAffected = database.update("Users", values, "UserName = ?", new String[]{user.getUserName()});
        return rowsAffected > 0;
    }

    public User getUserByUsername(String username) {
        Cursor cursor = database.query("Users", null, "UserName = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("UsersID")),
                    cursor.getString(cursor.getColumnIndexOrThrow("UserName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("AccountName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Avatar")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Address")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Phone")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("UserStatus")) > 0,
                    cursor.getInt(cursor.getColumnIndexOrThrow("RoleID"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public boolean updatePassword(String username, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("Password", newPassword);
        int rowsAffected = database.update("Users", values, "UserName = ?", new String[]{username});
        return rowsAffected > 0;
    }

}
