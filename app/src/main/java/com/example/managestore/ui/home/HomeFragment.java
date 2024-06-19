package com.example.managestore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managestore.dao.ProductDAO;
import com.example.managestore.databinding.FragmentHomeBinding;
import com.example.managestore.models.Product;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView horizontalList;
    ListView verticalList;
    private ProductDAO productDAO;
    HorizontalListAdapter horizontalListAdapter;
    VerticalListAdapter verticalListAdapter;
    List<Product> productList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        horizontalList = binding.horizontalList;
        verticalList = binding.verticalList;

        productDAO = new ProductDAO(getActivity());
        productDAO.open();

        productList = productDAO.getProducts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        horizontalListAdapter = new HorizontalListAdapter(root.getContext(), productList);
        horizontalList.setAdapter(horizontalListAdapter);
        horizontalList.setLayoutManager(layoutManager);

        verticalListAdapter = new VerticalListAdapter(root.getContext(), productList);
        verticalList.setAdapter(verticalListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}