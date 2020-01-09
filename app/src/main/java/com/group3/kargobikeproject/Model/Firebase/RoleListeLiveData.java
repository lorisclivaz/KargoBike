package com.group3.kargobikeproject.Model.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.Model.Entity.Product;
import com.group3.kargobikeproject.Model.Entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleListeLiveData extends LiveData<List<Role>> {
    private static final String TAG = "ProductListLiveData";

    private final DatabaseReference reference;
    private final RoleListeLiveData.MyValueEventListener listener = new RoleListeLiveData.MyValueEventListener();

    public RoleListeLiveData(DatabaseReference ref) {
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

    private List<Role> toShops(DataSnapshot snapshot) {
        List<Role> shops = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Role entity = childSnapshot.getValue(Role.class);
            entity.setIdRole(childSnapshot.getKey());
            shops.add(entity);
        }
        return shops;
    }
}
