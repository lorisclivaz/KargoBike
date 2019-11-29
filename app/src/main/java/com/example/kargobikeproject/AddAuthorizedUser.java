package com.example.kargobikeproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kargobikeproject.Model.Repository.OrderRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddAuthorizedUser extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayUser;
    ValueEventListener listener;
    Spinner spinnerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorized_user);
        spinnerUser = (Spinner) findViewById(R.id.spinnerUserAuth);

        spinnerUser.setPrompt("Select the user");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");


       /* ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayUser);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(spinnerArrayAdapter);*/

        arrayUser  = new ArrayList<>();
        adapter = new ArrayAdapter<String>(AddAuthorizedUser.this,android.R.layout.simple_spinner_dropdown_item, arrayUser);

        spinnerUser.setAdapter(adapter);
    }


    public void retrieveData()
    {
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){

                    arrayUser.add(item.getValue().toString());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
