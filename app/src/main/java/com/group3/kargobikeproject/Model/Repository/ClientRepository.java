package com.group3.kargobikeproject.Model.Repository;

import com.group3.kargobikeproject.Model.Entity.Client;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class ClientRepository {

    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final Client client, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("clients").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("client")
                .child(id)
                .setValue(client, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
