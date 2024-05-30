package com.example.managestore;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managestore.dao.CategoryDAO;
import com.example.managestore.dao.ProductDAO;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.Category;
import com.example.managestore.models.Product;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView signupText;
    Button loginButton;
    UserDAO userDAO;
    CategoryDAO categoryDAO;
    ProductDAO productDAO;
    final String[] listCategory = {"Phone", "Laptop", "Watch"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = (TextView) findViewById(R.id.signupText);
        userDAO = new UserDAO(this);
        userDAO.open();
        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();
        productDAO = new ProductDAO(this);
        productDAO.open();

        // insert category data if table is empty
        long categoriesCount = categoryDAO.getCategoriesCount();
        if(categoriesCount == 0) {
            categoryDAO.insertData(listCategory);
        }

        // insert product data if table is empty
        long productsCount = productDAO.getProductsCount();
        if(productsCount == 0) {
            Category phone = categoryDAO.getCategoryByName("Phone");
            Category laptop = categoryDAO.getCategoryByName("Laptop");
            Category watch = categoryDAO.getCategoryByName("Watch");

            List<Product> productList = new ArrayList<>();
            productList.add(new Product("IPhone 15 Pro", 25690000, true, 200, "The iPhone 15 Pro features a titanium frame, A17 Bionic chip, and an upgraded camera system with improved low-light performance and ProRAW capabilities. It offers a Super Retina XDR display with ProMotion technology, enhanced 5G connectivity, and runs on iOS 17.", "https://cdn.hoanghamobile.com/i/preview/Uploads/2023/09/13/iphone-15-pro-white-titanium-pure-back-iphone-15-pro-white-titanium-pure-front-2up-screen-usen.png", phone.getCategoryID()));
            productList.add(new Product("Samsung Galaxy S24 Ultra", 31990000, true, 300, "The Samsung Galaxy S24 Ultra features a sleek design, the powerful Snapdragon 8 Gen 3 processor, and an advanced quad-camera system with improved low-light capabilities and 200MP main sensor. It offers a Dynamic AMOLED 2X display, enhanced 5G connectivity, and runs on the latest version of One UI based on Android 14.", "https://clickbuy.com.vn/uploads/pro/samsung-galaxy-s24-ultra_197559.jpg", phone.getCategoryID()));
            productList.add(new Product("Asus TUF Dash", 18950000, true, 150, "The Asus TUF Dash features a sleek and durable design, powered by Intel's latest processors and NVIDIA GeForce RTX graphics for high-performance gaming. It offers a fast-refresh-rate display, enhanced cooling system, and long battery life, making it ideal for both gaming and everyday use.", "https://laptopaz.vn/media/product/1933_5.png", laptop.getCategoryID()));
            productList.add(new Product("Asus ROG Zephyrus", 34900000, true, 100, "The ASUS ROG Zephyrus features a slim and lightweight design, powered by AMD Ryzen or Intel Core processors and NVIDIA GeForce RTX graphics for top-tier gaming performance. It offers a high-refresh-rate display, advanced cooling technology, and customizable RGB lighting, making it ideal for gamers and power users.", "https://www.civip.com.vn/media/product/8166_h732__6_.png", laptop.getCategoryID()));
            productList.add(new Product("Apple Watch Series 9 41mm", 9890000, true, 250, "The Apple Watch Series 9 41mm features a brighter Always-On Retina display, powered by the new S9 chip for improved performance and battery life. It offers enhanced health and fitness tracking, advanced sensors, and runs on watchOS 10, providing a seamless and intuitive user experience.", "https://cdn.hoanghamobile.com/i/productlist/dsp/Uploads/2023/09/20/s9-41-pink-lightpink-aluminium.png", watch.getCategoryID()));
            productDAO.insertData(productList);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                if (!usernameStr.isEmpty() && !passwordStr.isEmpty()){
                    if (userDAO.checkUser(usernameStr,passwordStr)) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", usernameStr);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter username and password!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
        categoryDAO.close();
        productDAO.close();
    }
}