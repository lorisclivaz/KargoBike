package com.group3.kargobikeproject.Model.Repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group3.kargobikeproject.Model.Entity.Product;
import com.group3.kargobikeproject.Model.Entity.Role;
import com.group3.kargobikeproject.Model.Firebase.ProductListLiveData;
import com.group3.kargobikeproject.Model.Firebase.ProductLiveData;
import com.group3.kargobikeproject.Model.Firebase.RoleListeLiveData;
import com.group3.kargobikeproject.Model.Firebase.RoleLiveData;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;

import java.util.List;

public class RoleRepository {


    private static RoleRepository instance;

    public static RoleRepository getInstance() {
        if (instance == null) {
            synchronized (RoleRepository.class) {
                if (instance == null) {
                    instance = new RoleRepository();
                }
            }
        }
        return instance;
    }

    public void insert(final Role role, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("roles").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("roles")
                .child(id)
                .setValue(role, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Role>> getAllRoless() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("roles");
        return new RoleListeLiveData(reference);
    }

    public LiveData<Role> getRole(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("roles")
                .child(name);
        return new RoleLiveData(reference);
    }

    public void update(final Role role, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("roles")
                .child(role.getIdRole())
                .updateChildren(role.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Role role, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("roles")
                .child(role.getIdRole())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
