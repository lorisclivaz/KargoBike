package com.group3.kargobikeproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsUserActivity extends AppCompatActivity {

    private TextView firstName, lastName, eMail, phoneNumber, workRegion;
    private String firstNameGet, lastNameGet, eMailGet, phoneNumberGet, workRegionGet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_user);

        //Get the layout
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        eMail = findViewById(R.id.Email);
        phoneNumber = findViewById(R.id.PhoneNumber);
        workRegion = findViewById(R.id.WorkCountry);


        //Get the information about the user
        firstNameGet =  getIntent().getStringExtra("FirstName");
        lastNameGet = getIntent().getStringExtra("LastName");
        eMailGet = getIntent().getStringExtra("Email");
        phoneNumberGet = getIntent().getStringExtra("PhoneNumber");
        workRegionGet = getIntent().getStringExtra("WorkCountry");


        //We set the info to the textView
        firstName.setText(firstNameGet);
        lastName.setText(lastNameGet);
        eMail.setText(eMailGet);
        phoneNumber.setText(phoneNumberGet);
        workRegion.setText(workRegionGet);







    }
}
