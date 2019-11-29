package com.example.kargobikeproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NoAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_access);
    }

    @Override
    public void onBackPressed() {

    }

}
