package com.example.managestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.managestore.dao.CartDAO;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.Product;
import com.example.managestore.models.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView productImg;
    TextView productName;
    TextView productCategory;
    TextView productPrice;
    TextView productStatus;
    TextView productQuantity;
    TextView productDescription;
    EditText quantityEdt;
    Button addCartBtn;
    CartDAO cartDAO;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product_detail);

        cartDAO = new CartDAO(this);
        cartDAO.open();
        userDAO = new UserDAO(this);
        userDAO.open();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        productImg = findViewById(R.id.productImage);
        productName = findViewById(R.id.productDetailName);
        productCategory = findViewById(R.id.productDetailCategory);
        productPrice = findViewById(R.id.productDetailPrice);
        productStatus = findViewById(R.id.productStatus);
        productQuantity = findViewById(R.id.productQuantity);
        productDescription = findViewById(R.id.productDescription);
        addCartBtn = findViewById(R.id.addCartBtn);
        quantityEdt = findViewById(R.id.quantityEdt);

        Intent intent = getIntent();
        Product product = (Product) intent.getExtras().getSerializable("product");

        Glide.with(this).load(product.getProductLink()).into(productImg);
        productName.setText(product.getProductName());
        productCategory.setText(product.getCategoryName());

        BigDecimal bigDecimal = new BigDecimal(product.getProductPrice());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        productPrice.setText(formatter.format(bigDecimal) + " VND");

        if(product.isProductStatus()) productStatus.setText("Status: Available");
        else productStatus.setText("Status: Not available");

        productQuantity.setText("Quantity: " + product.getProductQuantity());
        productDescription.setText(product.getDescription());

        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        User user = userDAO.getUserByUsername(username);

        addCartBtn.setOnClickListener(v -> {
            if(quantityEdt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Must specify quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantityValue = Integer.parseInt(quantityEdt.getText().toString());
            if(quantityValue <= 0) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            cartDAO.addToCart(product.getProductID(), user.getUserID(), quantityValue);

            Toast.makeText(this, "Add to cart successfully", Toast.LENGTH_SHORT).show();

            getOnBackPressedDispatcher().onBackPressed();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.product_detail), (v, insets) -> {
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