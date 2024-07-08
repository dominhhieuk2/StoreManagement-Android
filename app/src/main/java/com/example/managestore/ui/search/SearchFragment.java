package com.example.managestore.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.managestore.ProductDetailActivity;
import com.example.managestore.dao.ProductDAO;
import com.example.managestore.databinding.FragmentSearchBinding;
import com.example.managestore.models.Product;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    List<Product> productList;
    ListView listView;
    EditText searchEdt;
    private ProductDAO productDAO;
    ProductListAdapter listViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SearchViewModel searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchEdt = binding.searchEdt;
        listView = binding.productList;

        productDAO = new ProductDAO(getActivity());
        productDAO.open();

        productList = productDAO.getProducts();
        listViewAdapter = new ProductListAdapter(root.getContext(), productList);
        listView.setAdapter(listViewAdapter);

        searchEdt.setOnEditorActionListener((v, actionId, event) -> {
            String searchValue = searchEdt.getText().toString();
            productList = productDAO.searchProducts(searchValue);
            listViewAdapter = new ProductListAdapter(root.getContext(), productList);
            listView.setAdapter(listViewAdapter);

            return false;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("product", productList.get(position));
            startActivity(intent);
        });

//        final TextView textView = binding.textGallery;
//        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        productDAO.close();
        binding = null;
    }
}