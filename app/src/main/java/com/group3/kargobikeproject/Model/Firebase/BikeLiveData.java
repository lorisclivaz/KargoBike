package com.group3.kargobikeproject.Model.Firebase;


import android.util.Log;

import com.group3.kargobikeproject.Model.Entity.Bike;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class BikeLiveData extends LiveData<Bike> {
    private static final String TAG = "BikeLiveData";

    private final DatabaseReference reference;
    private final BikeLiveData.MyValueEventListener listener = new BikeLiveData.MyValueEventListener();

    public BikeLiveData(DatabaseReference ref) {
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
            Bike entity = dataSnapshot.getValue(Bike.class);
            entity.setIdBike(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
