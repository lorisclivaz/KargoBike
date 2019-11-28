package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Entity.Route;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
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
