package com.example.kargobikeproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kargobikeproject.Model.Repository.OrderRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class AddAuthorizedUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorized_user);
        Spinner spinnerUser = (Spinner) findViewById(R.id.spinnerUser);

        final ArrayList<String> arrayUser = new ArrayList<>();
        arrayUser.add("Solange");
        arrayUser.add("Loris");
        arrayUser.add("Nghi");
        arrayUser.add("Romain");
        arrayUser.add("Loic");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayUser);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(spinnerArrayAdapter);
    }
}
