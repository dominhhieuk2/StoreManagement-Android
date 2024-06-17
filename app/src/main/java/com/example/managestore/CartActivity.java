package com.example.managestore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.managestore.dao.CartDAO;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.CartItem;
import com.example.managestore.models.User;
import com.example.managestore.ui.search.ProductListAdapter;
import com.example.managestore.ui.search.SearchFragment;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<CartItem> cartItemList;
    CartListAdapter listViewAdapter;
    LinearLayout cartEmptyFooter;
    Button browseBtn;
    LinearLayout cartFooter;
    ListView listView;
    TextView totalPrice;
    TextView noItemsText;
    CartDAO cartDAO;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cart_layout);

        listView = findViewById(R.id.cartList);
        totalPrice = findViewById(R.id.totalPrice);
        cartFooter = findViewById(R.id.cartFooter);
        noItemsText = findViewById(R.id.noItemsText);
        cartEmptyFooter = findViewById(R.id.cartEmptyFooter);
        browseBtn = findViewById(R.id.browseBtn);

        cartDAO = new CartDAO(this);
        cartDAO.open();
        userDAO = new UserDAO(this);
        userDAO.open();

        // get user cart item list
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        User user = userDAO.getUserByUsername(username);
        cartItemList = cartDAO.getCartItems(user.getUserID());

        if(cartItemList.size() > 0) {
            noItemsText.setVisibility(View.GONE);
            cartEmptyFooter.setVisibility(View.GONE);

            double totalPriceValue = 0;

            listViewAdapter = new CartListAdapter(this, cartItemList, cartDAO, userDAO);
            listView.setAdapter(listViewAdapter);

            for (CartItem cartItem : cartItemList) {
                totalPriceValue = totalPriceValue + cartItem.getProductPrice() * cartItem.getQuantity();
            }

            BigDecimal bigDecimal = new BigDecimal(totalPriceValue);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            totalPrice.setText(formatter.format(bigDecimal) + " VND");

            listView.setOnItemLongClickListener((parent, view, position, id) -> {
                AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create(); //Read Update
                alertDialog.setTitle("Remove Confirm");
                alertDialog.setMessage("Are you sure to remove this item from your cart?");

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {
                    alertDialog.hide();
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm", (dialog, which) -> {
                    cartDAO.removeFromCart(cartItemList.get(position).getCartID());

                    CartActivity.this.recreate();

                    alertDialog.hide();
                });

                alertDialog.show();

                return false;
            });
        }
        else  {
            cartFooter.setVisibility(View.GONE);
        }

        browseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

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