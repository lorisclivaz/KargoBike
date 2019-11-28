package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.Status;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class StatusRepository{

    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final Status status, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("status").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("status")
                .child(id)
                .setValue(status, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}


