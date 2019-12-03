package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class OrderRepository {


    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final Order order, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("order").push().getKey();
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



    public void delete(final Order order, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("order").getKey();
        FirebaseDatabase.getInstance()
                .getReference("order")
                .child(order.getIdOrder())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Order order, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("order")
                .child(order.getIdOrder())
                .updateChildren(order.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
