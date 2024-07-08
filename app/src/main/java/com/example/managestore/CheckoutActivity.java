package com.example.managestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.managestore.dao.CartDAO;
import com.example.managestore.dao.OrderDAO;
import com.example.managestore.dao.OrderDetailDAO;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.CartItem;
import com.example.managestore.models.OrderDetail;
import com.example.managestore.models.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;

public class CheckoutActivity extends AppCompatActivity {
    List<CartItem> checkoutList;
    CheckoutListAdapter listViewAdapter;
    ListView listView;
    TextView totalPrice;
    CartDAO cartDAO;
    UserDAO userDAO;
    OrderDAO orderDAO;
    OrderDetailDAO orderDetailDAO;
    private static final String TAG = "MyTag";
    PaymentButtonContainer paymentButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.checkout_layout);

        listView = findViewById(R.id.checkoutList);
        totalPrice = findViewById(R.id.checkoutTotalPrice);
        paymentButtonContainer = findViewById(R.id.payment_button_container);

        cartDAO = new CartDAO(this);
        cartDAO.open();
        userDAO = new UserDAO(this);
        userDAO.open();
        orderDAO = new OrderDAO(this);
        orderDAO.open();
        orderDetailDAO = new OrderDetailDAO(this);
        orderDetailDAO.open();

        // get user cart item list
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        User user = userDAO.getUserByUsername(username);
        checkoutList = cartDAO.getCartItems(user.getUserID());

        double totalPriceValue = 0;
        if(checkoutList.size() > 0) {
            listViewAdapter = new CheckoutListAdapter(this, checkoutList, cartDAO, userDAO);
            listView.setAdapter(listViewAdapter);

            for (CartItem cartItem : checkoutList) {
                totalPriceValue = totalPriceValue + cartItem.getProductPrice() * cartItem.getQuantity();
            }

            BigDecimal bigDecimal = new BigDecimal(totalPriceValue);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            totalPrice.setText(formatter.format(bigDecimal) + " VND");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            double finalTotalPriceValue = totalPriceValue;
            paymentButtonContainer.setup(
                    new CreateOrder() {
                        @Override
                        public void create(@NotNull CreateOrderActions createOrderActions) {
                            double usdPrice = finalTotalPriceValue * 0.000039;
                            DecimalFormat formatter = new DecimalFormat("#.##");

                            ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                            purchaseUnits.add(
                                    new PurchaseUnit.Builder()
                                            .amount(
                                                    new Amount.Builder()
                                                            .currencyCode(CurrencyCode.USD)
                                                            .value(formatter.format(usdPrice))
                                                            .build()
                                            )
                                            .build()
                            );
                            OrderRequest order = new OrderRequest(
                                    OrderIntent.CAPTURE,
                                    new AppContext.Builder()
                                            .userAction(UserAction.PAY_NOW)
                                            .build(),
                                    purchaseUnits
                            );
                            createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                        }
                    },
                    new OnApprove() {
                        @Override
                        public void onApprove(@NotNull Approval approval) {
                            approval.getOrderActions().capture(new OnCaptureComplete() {
                                @Override
                                public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                    Log.d(TAG, String.format("CaptureOrderResult: %s", result));

                                    long orderId = orderDAO.createOrder(finalTotalPriceValue, user.getUserID());

                                    List<OrderDetail> listOrderDetail = new ArrayList<>();
                                    for (CartItem cartItem : checkoutList) {
                                        listOrderDetail.add(new OrderDetail(cartItem.getProductID(), cartItem.getQuantity(), (int) orderId));
                                    }
                                    orderDetailDAO.insertMany(listOrderDetail);
                                    cartDAO.clearCart(user.getUserID());

                                    Intent intent = new Intent(CheckoutActivity.this, PaymentSuccessActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
            );
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.checkout_layout), (v, insets) -> {
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
        orderDAO.close();
        orderDetailDAO.close();
    }
}