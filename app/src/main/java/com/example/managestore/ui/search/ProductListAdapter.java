package com.example.managestore.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managestore.R;

public class ProductListAdapter extends BaseAdapter {
    Context context;
    String[] listProduct;
    LayoutInflater inflater;

    public ProductListAdapter(Context ctx, String[] productList) {
        this.context = ctx;
        this.listProduct = productList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.listProduct.length;
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

        name.setText(listProduct[position]);
        category.setText("Category");
        price.setText("$99.00");
        img.setImageResource(R.drawable.sample_product);

        return convertView;
    }
}
