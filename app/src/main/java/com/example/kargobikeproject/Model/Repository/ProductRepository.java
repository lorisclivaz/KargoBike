package com.example.kargobikeproject.Model.Repository;

import androidx.lifecycle.LiveData;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Firebase.ProductListLiveData;
import com.example.kargobikeproject.Model.Firebase.ProductLiveData;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductRepository {
    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'

    private static ProductRepository instance;

    public static ProductRepository getInstance() {
        if (instance == null) {
            synchronized (ProductRepository.class) {
                if (instance == null) {
                    instance = new ProductRepository();
                }
            }
        }
        return instance;
    }

    public void insert(final Product product, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("products").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("products")
                .child(id)
                .setValue(product, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Product>> getAllProducts() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("products");
        return new ProductListLiveData(reference);
    }

    public LiveData<Product> getProduct(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("products")
                .child(name);
        return new ProductLiveData(reference);
    }

    public void update(final Product product, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("products")
                .child(product.getId())
                .updateChildren(product.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Product product, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("products")
                .child(product.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
