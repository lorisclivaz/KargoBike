package com.group3.kargobikeproject.Model.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.group3.kargobikeproject.Model.Entity.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProductLiveData extends LiveData<Product> {
    private static final String TAG = "ProductLiveData";

    private final DatabaseReference reference;
    private final ProductLiveData.MyValueEventListener listener = new ProductLiveData.MyValueEventListener();

    public ProductLiveData(DatabaseReference ref) {
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
            Product entity = (Product) dataSnapshot.getValue(Product.class);
            System.out.println("dataSnapshot "+dataSnapshot);
            System.out.println(dataSnapshot.getKey());
            entity.setId(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}

