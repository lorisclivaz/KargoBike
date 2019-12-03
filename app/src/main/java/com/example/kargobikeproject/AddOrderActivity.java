package com.example.kargobikeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Model.Repository.OrderRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class AddOrderActivity extends AppCompatActivity {
    private static final String TAG = "Order";

    private DatabaseReference mDatabaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;

    OrderRepository orderRepository;
    Order order ;
    Spinner status;
    TextInputEditText nameClient;
    TextInputEditText nameRider;
    TextInputEditText route;
    TextInputEditText address;
    TextView deliverStart, deliverEnd;
    private DatePickerDialog.OnDateSetListener dateListener;

    Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        orderRepository = new OrderRepository();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("status");





        //Find in the layout
        nameClient = findViewById(R.id.NameClient);
        address = findViewById(R.id.Address);
        deliverStart = findViewById(R.id.deliverStart);
        deliverEnd = findViewById(R.id.deliverEnd);
        route = findViewById(R.id.nameRoute);
        nameRider = findViewById(R.id.NameRider);
        submit = findViewById(R.id.buttonSubmit);


        //StartDate calendar
        deliverStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("salut","deliver start " );

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddOrderActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year, month,day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                deliverStart.setText(dayOfMonth+"."+(month+1)+"."+year);
            }
        };


        //EndDate calendar
        deliverEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("salut","deliver end" );
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddOrderActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year, month,day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                deliverEnd.setText(dayOfMonth+"."+(month+1)+"."+year);
            }
        };

        //Spinner Status
        status = (Spinner)findViewById(R.id.spinnerStatusModify);
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(AddOrderActivity.this,android.R.layout.simple_spinner_dropdown_item, spinnerDataList);

        status.setAdapter(adapter);
        retrieveData();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order( nameClient.getText().toString(),nameRider.getText().toString(),
                        route.getText().toString(),
                        address.getText().toString(),deliverStart.getText().toString()
                        ,deliverEnd.getText().toString()
                        ,status.getSelectedItem().toString());

                orderRepository.insert(order, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Order added : success");

                        startActivity(new Intent(AddOrderActivity.this,MainActivity.class));



                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d(TAG, "product added : failure");
                    }
                });


            }
        });




    }

    public void retrieveData()
    {
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){

                    spinnerDataList.add(item.getValue().toString());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
