package com.example.kargobikeproject.Model.Firebase;

import android.util.Log;

import com.example.kargobikeproject.Model.Entity.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class PaymentListLiveData extends LiveData<List<Payment>> {
    private static final String TAG = "PaymentListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public PaymentListLiveData(DatabaseReference ref) {
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

    private List<Payment> toShops(DataSnapshot snapshot) {
        List<Payment> shops = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Payment entity = childSnapshot.getValue(Payment.class);
            entity.setIdPayment(childSnapshot.getKey());
            shops.add(entity);
        }
        return shops;
    }
}
