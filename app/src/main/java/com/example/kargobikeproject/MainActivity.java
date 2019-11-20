package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button buttonAddProduct;
Button AddGpsCheckpoint;
Button checkBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddProduct=findViewById(R.id.buttonAddProduct);
        AddGpsCheckpoint=findViewById(R.id.buttonGps);
        checkBike=findViewById(R.id.buttonCheckBike);


        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        });

        AddGpsCheckpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GpsCheckPointActivity.class));
            }
        });

        checkBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CheckBikeActivity.class));
            }
        });
    }







}
