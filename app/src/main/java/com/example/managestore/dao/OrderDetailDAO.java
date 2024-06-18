package com.example.managestore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.managestore.database.DatabaseHelper;
import com.example.managestore.models.OrderDetail;

import java.util.List;


public class OrderDetailDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public OrderDetailDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertMany(List<OrderDetail> listOrderDetail) {
        ContentValues values = new ContentValues();
        for (OrderDetail orderDetail : listOrderDetail) {
            values.put("ProductID", orderDetail.getProductID());
            values.put("Quantity", orderDetail.getQuantity());
            values.put("OrderID", orderDetail.getOrderID());

            database.insert("OrderDetail", null, values);
        }
    }
}
