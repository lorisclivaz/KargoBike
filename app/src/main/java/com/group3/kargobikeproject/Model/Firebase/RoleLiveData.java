package com.group3.kargobikeproject.Model.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.Model.Entity.Payment;
import com.group3.kargobikeproject.Model.Entity.Role;

public class RoleLiveData  extends LiveData<Role> {
    private static final String TAG = "PaymentLiveData";

    private final DatabaseReference reference;
    private final RoleLiveData.MyValueEventListener listener = new RoleLiveData.MyValueEventListener();

    public RoleLiveData(DatabaseReference ref) {
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
            Role entity = dataSnapshot.getValue(Role.class);
            entity.setIdRole(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}

