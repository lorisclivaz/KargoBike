package com.example.kargobikeproject.Model.Repository;

import com.example.kargobikeproject.Model.Entity.BikeService;
import com.example.kargobikeproject.Model.Entity.User;
import com.example.kargobikeproject.Model.Firebase.BikeServiceListLiveData;
import com.example.kargobikeproject.Model.Firebase.BikeServiceLiveData;
import com.example.kargobikeproject.Model.Firebase.UserListLiveData;
import com.example.kargobikeproject.Model.Firebase.UserLiveData;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BikeServiceRepository {

    private static BikeServiceRepository instance;

    private BikeServiceRepository(){
    }

    public static BikeServiceRepository getInstance() {
        if (instance == null) {
            synchronized (BikeServiceRepository.class) {
                if (instance == null) {
                    instance = new BikeServiceRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<BikeService> getBikeService(final String bikeServiceId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bikeService").child(bikeServiceId);
        return new BikeServiceLiveData(reference);
    }

    public LiveData<List<BikeService>> getAllBikeService() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bikeService");
        return new BikeServiceListLiveData(reference);
    }

    public void insert(final BikeService bikeService, final OnAsyncEventListener callback) {

        String key = FirebaseDatabase.getInstance().getReference("bikeService").push().getKey();
        // add a user
        FirebaseDatabase.getInstance()
                .getReference("bikeService")
                .child(key)
                .setValue(bikeService.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("bikeService/"+bikeService.getIdService());

                    }
                });
    }

    public void update(final BikeService bikeService, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("bikeService")
                .child(bikeService.getIdService())
                .updateChildren(bikeService.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("bikeService/"+bikeService.getIdService());
                    }
                });
    }

    public void delete(final BikeService bikeService, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("bikeService")
                .child(bikeService.getIdService())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
