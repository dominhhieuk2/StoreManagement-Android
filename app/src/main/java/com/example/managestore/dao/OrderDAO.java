package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.managestore.database.DatabaseHelper;

import java.text.DateFormat;
import java.util.Date;


public class OrderDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createOrder(double total, int userId) {
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put("OrderDate", DateFormat.getDateTimeInstance(). format(date));
        values.put("Total", total);
        values.put("UsersID", userId);

        return database.insert("Orders", null, values);
    }
}
