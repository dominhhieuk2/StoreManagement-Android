package com.example.managestore.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managestore.R;
import com.example.managestore.models.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {
    List<Product> listProduct;
    Context context;
    LayoutInflater inflater;

    public HorizontalListAdapter(Context ctx, List<Product> listProduct) {
        this.listProduct = listProduct;
        this.context = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.product_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = listProduct.get(position);

        holder.name.setText(product.getProductName());
        holder.category.setText(product.getCategoryName());

        BigDecimal bigDecimal = new BigDecimal(product.getProductPrice());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.price.setText(formatter.format(bigDecimal) + " VND");

        Glide.with(context).load(product.getProductLink()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView category;
        TextView price;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productName);
            category = itemView.findViewById(R.id.productCategory);
            price = itemView.findViewById(R.id.productPrice);
            img = itemView.findViewById(R.id.productImg);
        }
    }
}


