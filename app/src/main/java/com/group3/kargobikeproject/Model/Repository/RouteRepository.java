package com.group3.kargobikeproject.Model.Repository;

import com.group3.kargobikeproject.Model.Entity.Route;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class RouteRepository {

    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final Route route, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("routes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("route")
                .child(id)
                .setValue(route, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
