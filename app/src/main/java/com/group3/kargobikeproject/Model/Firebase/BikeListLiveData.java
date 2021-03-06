package com.group3.kargobikeproject.Model.Firebase;

import android.util.Log;

import com.group3.kargobikeproject.Model.Entity.Bike;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class BikeListLiveData extends LiveData<List<Bike>> {
    private static final String TAG = "BikeListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public BikeListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toShops(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Bike> toShops(DataSnapshot snapshot) {
        List<Bike> shops = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Bike entity = childSnapshot.getValue(Bike.class);
            entity.setIdBike(childSnapshot.getKey());
            shops.add(entity);
        }
        return shops;
    }
}

