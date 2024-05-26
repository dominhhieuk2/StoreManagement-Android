package com.example.managestore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PRM392.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Roles table
        db.execSQL("CREATE TABLE Roles ( " +
                "RoleID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RoleName NVARCHAR(50) UNIQUE NOT NULL)");

        // Create Users table
        db.execSQL("CREATE TABLE Users ( " +
                "UsersID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserName NVARCHAR(50) NOT NULL, " +
                "Password NVARCHAR(50) NOT NULL, " +
                "AccountName NVARCHAR(20) , " +
                "Avatar NVARCHAR(20) , " +
                "Address NVARCHAR(50), " +
                "Phone NVARCHAR(50), " +
                "UserStatus BIT, " +
                "RoleID INTEGER, " +
                "FOREIGN KEY (RoleID) REFERENCES Roles(RoleID))");

        // Create Categories table
        db.execSQL("CREATE TABLE Categories ( " +
                "CategoryID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CategoryName NVARCHAR(50) NOT NULL)");

        // Create Products table
        db.execSQL("CREATE TABLE Product ( " +
                "ProductID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProductLink NVARCHAR(20) NOT NULL, " +
                "ProductName NVARCHAR(50) NOT NULL, " +
                "ProductPrice MONEY NOT NULL, " +
                "ProductStatus BIT NOT NULL, " +
                "ProductQuantity INT NOT NULL, " +
                "Description NVARCHAR(100) NOT NULL, " +
                "CategoryID INTEGER, " +
                "FOREIGN KEY (CategoryID) REFERENCES Categorie(CategoryID))");

        // Create Orders table
        db.execSQL("CREATE TABLE Orders ( " +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OrderDate DATETIME NOT NULL, " +
                "Total MONEY, " +
                "UsersID INTEGER, " +
                "FOREIGN KEY (UsersID) REFERENCES Users(UsersID))");

        // Create OrderDetail table
        db.execSQL("CREATE TABLE OrderDetail ( " +
                "OrderDetailID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProductID INTEGER, " +
                "Quantity INT NOT NULL, " +
                "OrderID INTEGER, " +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID))");

        // Create Cart table
        db.execSQL("CREATE TABLE Cart ( " +
                "CartID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProductID INTEGER, " +
                "Quantity INT NOT NULL, " +
                "UsersID INTEGER, " +
                "FOREIGN KEY (ProductID) REFERENCES Product(ProductID), " +
                "FOREIGN KEY (UsersID) REFERENCES Users(UsersID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Roles");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Categories");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        onCreate(db);
    }
}
