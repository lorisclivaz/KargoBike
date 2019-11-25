package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class AddOrderActivity extends AppCompatActivity {

    TextInputEditText address;
    TextInputEditText deliverStart;
    TextInputEditText deliverEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);


        FirebaseDatabase.getInstance().getReference().push()
                .child("Order").setValue(new Order(1,1,1,1,"Chemin des plannetes 20","12.12.2019","14.12.2019",1));
    }
}
