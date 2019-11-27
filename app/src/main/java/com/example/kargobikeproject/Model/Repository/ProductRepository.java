package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class ProductRepository {
    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
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


}
