package com.example.managestore.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managestore.ProductDetailActivity;
import com.example.managestore.R;
import com.example.managestore.dao.ProductDAO;
import com.example.managestore.databinding.FragmentHomeBinding;
import com.example.managestore.models.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView horizontalList;
    private ProductDAO productDAO;
    HorizontalListAdapter horizontalListAdapter;
    List<Product> productList;
    LinearLayout verticalListContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        horizontalList = binding.horizontalList;
        verticalListContainer = binding.verticalListContainer;

        productDAO = new ProductDAO(getActivity());
        productDAO.open();

        productList = productDAO.getProducts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        horizontalListAdapter = new HorizontalListAdapter(root.getContext(), productList);
        horizontalList.setAdapter(horizontalListAdapter);
        horizontalList.setLayoutManager(layoutManager);

        for (Product product : productList) {
            View itemView = inflater.inflate(R.layout.product_horizontal, verticalListContainer, false);

            TextView name = itemView.findViewById(R.id.horizontalItemName);
            TextView category = itemView.findViewById(R.id.horizontalItemCategory);
            TextView price = itemView.findViewById(R.id.horizontalItemPrice);
            ImageView img = itemView.findViewById(R.id.horizontalItemImg);

            name.setText(product.getProductName());
            category.setText(product.getCategoryName());

            BigDecimal bigDecimal = new BigDecimal(product.getProductPrice());
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            price.setText(formatter.format(bigDecimal) + " VND");

            Glide.with(root.getContext()).load(product.getProductLink()).into(img);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            });

            verticalListContainer.addView(itemView);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}