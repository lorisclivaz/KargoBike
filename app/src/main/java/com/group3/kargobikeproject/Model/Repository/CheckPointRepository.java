package com.group3.kargobikeproject.Model.Repository;


import com.group3.kargobikeproject.Model.Entity.CheckPoint;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;


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