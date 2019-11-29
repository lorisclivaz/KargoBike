package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.User;
import com.example.kargobikeproject.Model.Firebase.UserListLiveData;
import com.example.kargobikeproject.Model.Firebase.UserLiveData;
import com.example.kargobikeproject.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class UserRepository {

    private static UserRepository instance;

    private UserRepository(){
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<User> getUser(final String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        return new UserLiveData(reference);
    }

    public LiveData<List<User>> getAllUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        return new UserListLiveData(reference);
    }

    public void insert(final User user, final OnAsyncEventListener callback) {

        //String key = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        // add a user
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getIdUser())
                .setValue(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("users/"+user.getIdUser());

                    }
                });
    }

    public void update(final User user, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getIdUser())
                .updateChildren(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("users/"+user.getIdUser());
                    }
                });
    }

    public void delete(final User user, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getIdUser())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
