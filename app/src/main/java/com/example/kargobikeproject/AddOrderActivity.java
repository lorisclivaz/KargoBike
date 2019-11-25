package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class AddOrderActivity extends AppCompatActivity {

    TextInputEditText address;
    TextInputEditText deliverStart;
    TextInputEditText deliverEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
    }
}
