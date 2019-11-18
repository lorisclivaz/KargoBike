package com.example.kargobikeproject.Model.Firebase;

import android.util.Log;

import com.example.kargobikeproject.Model.Entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class OrderLiveData extends LiveData<Order> {
    private static final String TAG = "OrderLiveData";

    private final DatabaseReference reference;
    private final OrderLiveData.MyValueEventListener listener = new OrderLiveData.MyValueEventListener();

    public OrderLiveData(DatabaseReference ref) {
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
            Order entity = dataSnapshot.getValue(Order.class);
            entity.setIdOrder(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}


