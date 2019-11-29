package com.example.kargobikeproject.Model.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Entity.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TypeLiveData extends LiveData<Type> {
    private static final String TAG = "TypeLiveData";

    private final DatabaseReference reference;
    private final TypeLiveData.MyValueEventListener listener = new TypeLiveData.MyValueEventListener();

    public TypeLiveData (DatabaseReference ref) {
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
            Type entity = dataSnapshot.getValue(Type.class);
            entity.setIdType(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
