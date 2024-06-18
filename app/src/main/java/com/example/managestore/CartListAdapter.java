package com.example.managestore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.managestore.dao.CartDAO;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.CartItem;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CartListAdapter extends BaseAdapter {
    Context context;
    List<CartItem> listCartItem;
    LayoutInflater inflater;
    CartDAO cartDAO;
    UserDAO userDAO;

    public CartListAdapter(Context ctx, List<CartItem> cartItemList, CartDAO cartDAO, UserDAO userDAO) {
        this.context = ctx;
        this.listCartItem = cartItemList;
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.listCartItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.cart_item, null);

        TextView name = convertView.findViewById(R.id.cartItemName);
        TextView category = convertView.findViewById(R.id.cartItemCategory);
        TextView price = convertView.findViewById(R.id.cartItemPrice);
        TextView quantity = convertView.findViewById(R.id.cartItemQuantity);
        ImageView img = convertView.findViewById(R.id.cartItemImg);

        TextView decreaseBtn = convertView.findViewById(R.id.decreaseQuantity);
        TextView increaseBtn = convertView.findViewById(R.id.increaseQuantity);

        name.setText(listCartItem.get(position).getProductName());
        category.setText(listCartItem.get(position).getCategoryName());
        quantity.setText(listCartItem.get(position).getQuantity() + "");

        BigDecimal bigDecimal = new BigDecimal(listCartItem.get(position).getProductPrice());
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        price.setText(formatter.format(bigDecimal) + " VND");

        Glide.with(context).load(listCartItem.get(position).getProductLink()).into(img);

        increaseBtn.setOnClickListener(v -> {
            CartItem item = listCartItem.get(position);
            cartDAO.increaseQuantity(item.getCartID(), item.getQuantity());

            ((Activity)context).recreate();
        });

        decreaseBtn.setOnClickListener(v -> {
            CartItem item = listCartItem.get(position);

            if(item.getQuantity() == 1) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update
                alertDialog.setTitle("Remove Confirm");
                alertDialog.setMessage("Are you sure to remove this item from your cart?");

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {
                    alertDialog.hide();
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm", (dialog, which) -> {
                    cartDAO.removeFromCart(listCartItem.get(position).getCartID());

                    ((Activity)context).recreate();

                    alertDialog.hide();
                });

                alertDialog.show();
                return;
            }

            cartDAO.decreaseQuantity(item.getCartID(), item.getQuantity());

            ((Activity)context).recreate();
        });

        return convertView;
    }
}
