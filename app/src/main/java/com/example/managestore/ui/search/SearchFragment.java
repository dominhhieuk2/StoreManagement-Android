package com.example.managestore.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.managestore.R;
import com.example.managestore.dao.ProductDAO;
import com.example.managestore.databinding.FragmentSearchBinding;
import com.example.managestore.models.Product;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    List<Product> productList;
    ListView listView;
    private ProductDAO productDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SearchViewModel searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        productDAO = new ProductDAO(getActivity());
        productDAO.open();

        productList = productDAO.getProducts();
        ProductListAdapter listViewAdapter = new ProductListAdapter(root.getContext(), productList);
        listView = binding.productList;
        listView.setAdapter(listViewAdapter);

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