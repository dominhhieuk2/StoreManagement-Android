package com.example.managestore;

import android.content.Context;
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

public class CheckoutListAdapter extends BaseAdapter {
    Context context;
    List<CartItem> listCheckoutItem;
    LayoutInflater inflater;
    CartDAO cartDAO;
    UserDAO userDAO;

    public CheckoutListAdapter(Context ctx, List<CartItem> checkoutItemList, CartDAO cartDAO, UserDAO userDAO) {
        this.context = ctx;
        this.listCheckoutItem = checkoutItemList;
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.listCheckoutItem.size();
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
        convertView = inflater.inflate(R.layout.checkout_item, null);

        TextView name = convertView.findViewById(R.id.checkoutItemName);
        TextView category = convertView.findViewById(R.id.checkoutItemCategory);
        TextView price = convertView.findViewById(R.id.checkoutItemPrice);
        TextView quantity = convertView.findViewById(R.id.checkoutItemQuantity);
        ImageView img = convertView.findViewById(R.id.checkoutItemImg);

        name.setText(listCheckoutItem.get(position).getProductName());
        category.setText(listCheckoutItem.get(position).getCategoryName());
        quantity.setText(listCheckoutItem.get(position).getQuantity() + "");

        BigDecimal bigDecimal = new BigDecimal(listCheckoutItem.get(position).getProductPrice());
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        price.setText(formatter.format(bigDecimal) + " VND");

        Glide.with(context).load(listCheckoutItem.get(position).getProductLink()).into(img);

        return convertView;
    }
}
