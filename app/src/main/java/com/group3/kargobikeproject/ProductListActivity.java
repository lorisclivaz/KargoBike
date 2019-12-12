package com.group3.kargobikeproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group3.kargobikeproject.Adapter.ProductAdapter;
import com.group3.kargobikeproject.Model.Entity.Product;
import com.group3.kargobikeproject.Utils.RecyclerViewItemClickListener;
import com.group3.kargobikeproject.ViewModel.ProductListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private List<Product> products;
    private ProductAdapter recyclerAdapter;
    private ProductListViewModel viewModel;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

    setContentView(R.layout.activity_product_list);


    RecyclerView recyclerView = findViewById(R.id.productsRecyclerView);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
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

            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
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

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );
   ProductListViewModel.Factory factory = new ProductListViewModel.Factory(getApplication());
    viewModel = ViewModelProviders.of(this, factory).get(ProductListViewModel.class);
        viewModel.getBeverages().observe(this, productEntities -> {
        if (productEntities != null) {
            products = productEntities;
            recyclerAdapter.setData(products);
        }
    });

        recyclerView.setAdapter(recyclerAdapter);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

}
