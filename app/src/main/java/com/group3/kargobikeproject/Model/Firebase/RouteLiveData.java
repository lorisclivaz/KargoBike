package com.group3.kargobikeproject.Model.Firebase;

import android.util.Log;

import com.group3.kargobikeproject.Model.Entity.Route;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class RouteLiveData extends LiveData<Route> {
    private static final String TAG = "RouteLiveData";

    private final DatabaseReference reference;
    private final RouteLiveData.MyValueEventListener listener = new RouteLiveData.MyValueEventListener();

    public RouteLiveData(DatabaseReference ref) {
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
            Route entity = dataSnapshot.getValue(Route.class);
            entity.setIdRoute(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
