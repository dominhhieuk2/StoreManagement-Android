package com.example.managestore.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.managestore.R;
import com.example.managestore.models.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    Context context;
    List<Product> listProduct;
    LayoutInflater inflater;

    public ProductListAdapter(Context ctx, List<Product> productList) {
        this.context = ctx;
        this.listProduct = productList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.listProduct.size();
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
        convertView = inflater.inflate(R.layout.product_layout, null);
        TextView name = convertView.findViewById(R.id.productName);
        TextView category = convertView.findViewById(R.id.productCategory);
        TextView price = convertView.findViewById(R.id.productPrice);
        ImageView img = convertView.findViewById(R.id.productImg);

        name.setText(listProduct.get(position).getProductName());
        category.setText(listProduct.get(position).getCategoryName());

        BigDecimal bigDecimal = new BigDecimal(listProduct.get(position).getProductPrice());
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        price.setText(formatter.format(bigDecimal) + " Đ");

        Glide.with(context).load(listProduct.get(position).getProductLink()).into(img);
//        img.setImageResource(R.drawable.sample_product);

        return convertView;
    }
}
