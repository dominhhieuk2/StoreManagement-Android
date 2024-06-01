package com.example.managestore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.managestore.dao.CartDAO;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.User;

public class CartActivity extends AppCompatActivity {
    CartDAO cartDAO;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cart_layout);

        cartDAO = new CartDAO(this);
        cartDAO.open();
        userDAO = new UserDAO(this);
        userDAO.open();

        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        User user = userDAO.getUserByUsername(username);
        Log.d("check", cartDAO.getCartItems(user.getUserID()).get(0).getProductName());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cart_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartDAO.close();
        userDAO.close();
    }
}