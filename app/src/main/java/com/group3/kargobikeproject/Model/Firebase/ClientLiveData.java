package com.group3.kargobikeproject.Model.Firebase;

import android.util.Log;

import com.group3.kargobikeproject.Model.Entity.Client;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class ClientLiveData extends LiveData<Client> {
    private static final String TAG = "ClientLiveData";

    private final DatabaseReference reference;
    private final ClientLiveData.MyValueEventListener listener = new ClientLiveData.MyValueEventListener();

    public ClientLiveData(DatabaseReference ref) {
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
            Client entity = dataSnapshot.getValue(Client.class);
            entity.setIdClient(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}

