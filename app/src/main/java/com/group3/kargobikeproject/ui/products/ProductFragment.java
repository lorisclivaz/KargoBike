package com.group3.kargobikeproject.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group3.kargobikeproject.Adapter.ProductAdapter;
import com.group3.kargobikeproject.AddProductActivity;
import com.group3.kargobikeproject.Model.Entity.Product;
import com.group3.kargobikeproject.ProductDetailActivity;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.Utils.RecyclerViewItemClickListener;
import com.group3.kargobikeproject.ViewModel.ProductListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductFragment extends Fragment {

    private ProductViewModel mViewModel;
    private List<Product> products;
    private ProductAdapter recyclerAdapter;
    private ProductListViewModel viewModel;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_fragment, container, false);

        return inflater.inflate(R.layout.product_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView =getView().findViewById(R.id.productsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        products = new ArrayList<>();
        recyclerAdapter = new ProductAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + products.get(position).toString());

                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("IdProduct", products.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + products.get(position).toString());
            }
        });

        FloatingActionButton fab = getView().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), AddProductActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
            getActivity().finish();
                    startActivity(intent);
                }
        );
        ProductListViewModel.Factory factory = new ProductListViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ProductListViewModel.class);
        viewModel.getBeverages().observe(this, productEntities -> {
            if (productEntities != null) {
                products = productEntities;
                recyclerAdapter.setData(products);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);






    }

}
