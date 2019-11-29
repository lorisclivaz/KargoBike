package com.example.kargobikeproject.Model.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.kargobikeproject.Model.Entity.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TypeListLiveData extends LiveData<List<Type>> {
    private static final String TAG = "TypeListLiveData";

    private final DatabaseReference reference;
    private final TypeListLiveData.MyValueEventListener listener = new TypeListLiveData.MyValueEventListener();

    public TypeListLiveData(DatabaseReference ref) {
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

    private List<Type> toShops(DataSnapshot snapshot) {
        List<Type> shops = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Type entity = childSnapshot.getValue(Type.class);
            entity.setIdType(childSnapshot.getKey());
            shops.add(entity);
        }
        return shops;
    }
}
