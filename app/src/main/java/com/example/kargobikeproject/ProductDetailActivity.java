package com.example.kargobikeproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.example.kargobikeproject.ViewModel.ProductViewModel;

public class ProductDetailActivity extends AppCompatActivity {
    private boolean isEditable;
    private static final String TAG = "ProductDetails";
    private static final int CREATE_BEVERAGE = 0;
    private static final int EDIT_BEVERAGE = 1;
    private static final int DELETE_BEVERAGE = 2;
    private EditText etName;
    private EditText etDescription;
    private EditText etPrice;
    private Button button_SaveProduct;
    private ProductViewModel viewModel;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String idproduct = getIntent().getStringExtra("IdProduct");
System.out.println("idproduct "+idproduct);

        etName = findViewById(R.id.et_ProductName);
        etDescription = findViewById(R.id.et_description);
        etPrice = findViewById(R.id.et_price);
        button_SaveProduct= findViewById(R.id.button_SaveProduct);
        ProductViewModel.Factory factory = new ProductViewModel.Factory(getApplication(), idproduct);
        viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel.class);
        viewModel.getProduct().observe(this, productEntity -> {
            if (productEntity != null) {
                product = productEntity;
                etName.setText(product.getName());
                etDescription.setText(product.getDescription());
                etPrice.setText(product.getPrice()+"");

            }

        });
        button_SaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveChanges(
                        etName.getText().toString(),
                        etDescription.getText().toString(),
                        Double.parseDouble(etPrice.getText().toString())
                );
                startActivity(new Intent(ProductDetailActivity.this,ProductListActivity.class));
            }
        });

    }
    private void initiateView() {
        isEditable = false;


      /*etName.setFocusable(false);
      etName.setEnabled(false);
        etDescription.setFocusable(false);
        etDescription.setEnabled(false);
        etPrice.setFocusable(false);
        etPrice.setEnabled(false);*/
    }

    private void switchEditableMode() {
        if (!isEditable) {
            etName.setFocusable(true);
            etName.setEnabled(true);
            etDescription.setFocusable(true);
            etDescription.setEnabled(true);
            etPrice.setFocusable(true);
            etPrice.setEnabled(true);
            etPrice.setFocusableInTouchMode(true);

        } else {
            saveChanges(
                    etName.getText().toString(),
                    etDescription.getText().toString(),
                    Double.parseDouble(etPrice.getText().toString())
            );
            //  etName.setFocusable(false);
            //  etName.setEnabled(false);
            //   etDescription.setFocusable(false);
            // etDescription.setEnabled(false);
            //  etPrice.setFocusable(false);
            //  etPrice.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    private void createProduct(String name, String descritpion, double price) {


        product = new Product();
        product.setName(name);
        product.setDescription(descritpion);
        product.setPrice(price);

        viewModel.createProduct(product, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createClient: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createClient: failure", e);
            }
        });
    }

    private void saveChanges(String name, String descritpion, double price) {

        product.setName(name);
        product.setDescription(descritpion);
        product.setPrice(price);

        viewModel.updateProduct(product, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateProduct: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateProduct: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {

    }

    private void updateContent() {
        if (product != null) {
            etName.setText(product.getName());
            etDescription.setText(product.getDescription());
            etPrice.setText(product.getPrice()+" CHF");
        }
    }
}
