package com.group3.kargobikeproject.Model.Repository;

import com.google.firebase.database.DatabaseReference;
import com.group3.kargobikeproject.Model.Entity.CheckPoint;
import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.Model.Firebase.CheckPointListLiveData;
import com.group3.kargobikeproject.Model.Firebase.UserListLiveData;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CheckPointRepository {

    private static CheckPointRepository instance;

    private CheckPointRepository(){
    }

    public static CheckPointRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new CheckPointRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<CheckPoint>> getAllCheckpoints() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("checkPoint");
        return new CheckPointListLiveData(reference);
    }

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