package com.group3.kargobikeproject.Model.Repository;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class OrderRepository {


    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final Order order, final String time, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("order/"+time).push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("order/"+time)
                .child(id)
                .setValue(order, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }



    public void delete(final Order order, final String time, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("order/"+time).getKey();
        FirebaseDatabase.getInstance()
                .getReference("order/"+time)
                .child(order.getIdOrder())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Order order, final String time, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("order/"+time+"/"+order.getIdOrder())
                .updateChildren(order.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("order/"+time+"/"+order.getIdOrder());
                    }
                });
    }
}
