package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class OrderRepository {


    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final Order order, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("orders").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("order")
                .child(id)
                .setValue(order, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
