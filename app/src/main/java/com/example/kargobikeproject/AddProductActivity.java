package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Repository.ProductRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;

public class AddProductActivity extends AppCompatActivity {
    Button button_AddProduct;
    ProductRepository productRepository;
    EditText et_firstName;
    EditText et_intStock;
    private static final String TAG = "Product";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        productRepository=new ProductRepository();
        setContentView(R.layout.activity_add_product);
        button_AddProduct=findViewById(R.id.button_AddProduct);
        et_firstName=findViewById(R.id.et_firstName);
        et_intStock=findViewById(R.id.et_intStock);
        //add a product to the database
        button_AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product newProduct = new Product(et_firstName.getText().toString(),Integer.parseInt(et_intStock.getText().toString()));

                productRepository.insert(newProduct, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "product added : success");
                        startActivity(new Intent(AddProductActivity.this,MainActivity.class));

                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "product added: failure", e);
                    }
                });
            }
        });






    }
}
