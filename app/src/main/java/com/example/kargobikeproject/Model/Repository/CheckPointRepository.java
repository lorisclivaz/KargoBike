package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.CheckPoint;
import com.example.kargobikeproject.Model.Entity.Type;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckPointRepository {
    public void insert(final CheckPoint checkPoint, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("checkPoint").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("checkPoint")
                .child(id)
                .setValue(checkPoint, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}