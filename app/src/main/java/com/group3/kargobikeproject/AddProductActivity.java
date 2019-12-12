package com.group3.kargobikeproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.group3.kargobikeproject.Model.Entity.Product;
import com.group3.kargobikeproject.Model.Repository.ProductRepository;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;

public class AddProductActivity extends AppCompatActivity {
    Button button_AddProduct;
    ProductRepository productRepository;
    EditText et_ProductName;
    EditText et_description;
    EditText et_price;
    private static final String TAG = "Product";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        productRepository=new ProductRepository();
        setContentView(R.layout.activity_add_product);
        button_AddProduct=findViewById(R.id.button_AddProduct);
        et_ProductName=findViewById(R.id.et_ProductName);
        et_description=findViewById(R.id.et_description);
        et_price=findViewById(R.id.et_price);
        //add a product to the database
        button_AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product newProduct = new Product(et_ProductName.getText().toString(),et_description.getText().toString(),Double.valueOf(et_price.getText().toString()));

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
