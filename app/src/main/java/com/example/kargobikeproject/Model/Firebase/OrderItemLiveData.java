package com.example.kargobikeproject.Model.Firebase;

import android.util.Log;

import com.example.kargobikeproject.Model.Entity.OrderItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class OrderItemLiveData extends LiveData<OrderItem> {
    private static final String TAG = "OrderItemLiveData";

    private final DatabaseReference reference;
    private final OrderItemLiveData.MyValueEventListener listener = new OrderItemLiveData.MyValueEventListener();

    public OrderItemLiveData(DatabaseReference ref) {
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
            OrderItem entity = dataSnapshot.getValue(OrderItem.class);
            entity.setIdOrderItem(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}


