package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.managestore.database.DatabaseHelper;

import com.example.managestore.models.User;

import java.util.ArrayList;
import java.util.List;

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

    public User getUserById(int userId) {
        Cursor cursor = database.query("Users", null, "UsersID = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setUserID(cursor.getInt(cursor.getColumnIndex("UsersID")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("UserName")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
            user.setAccountName(cursor.getString(cursor.getColumnIndex("AccountName")));
            user.setAvatar(cursor.getString(cursor.getColumnIndex("Avatar")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));
            user.setUserStatus(cursor.getInt(cursor.getColumnIndex("UserStatus")) == 1);
            user.setRoleID(cursor.getInt(cursor.getColumnIndex("RoleID")));
            cursor.close();
            return user;
        }
        return null;
    }

    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("Password", user.getPassword());
        values.put("AccountName", user.getAccountName());
        values.put("Avatar", user.getAvatar());
        values.put("Address", user.getAddress());
        values.put("Phone", user.getPhone());
        values.put("UserStatus", user.isUserStatus());
        values.put("RoleID", user.getRoleID());

        return database.update("Users", values, "UsersID = ?", new String[]{String.valueOf(user.getUserID())});
    }

    public int deleteUser(int userId) {
        return database.delete("Users", "UsersID = ?", new String[]{String.valueOf(userId)});
    }
}
