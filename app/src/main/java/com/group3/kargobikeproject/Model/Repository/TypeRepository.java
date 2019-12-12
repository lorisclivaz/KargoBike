package com.group3.kargobikeproject.Model.Repository;


import com.group3.kargobikeproject.Model.Entity.Type;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TypeRepository {
    public void insert(final Type type, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("type").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("type")
                .child(id)
                .setValue(type, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
    public ArrayList<Type> getAllTypes(final OnAsyncEventListener callback) {
        ArrayList<Type> result = null;

        return result;
    }
}
