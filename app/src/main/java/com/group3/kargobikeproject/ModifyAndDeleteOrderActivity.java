package com.group3.kargobikeproject;

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

import com.group3.kargobikeproject.Adapter.OrderAdapter;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Model.Repository.OrderRepository;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ModifyAndDeleteOrderActivity extends AppCompatActivity {

    //Variables about ModifyAndDeleteOrderActivity
    private TextInputEditText nameClient, nameRider, nameRoute, address;
    private  TextView deliverStart, deliverEnd;
    private  Spinner statusModify;
    private ArrayList<String> spinnerDataList;
    private  ArrayAdapter<String> adapter;
    private Order order;
    private  OrderAdapter orderAdapter;
    private OrderRepository orderRepository;
    private String idorder;
    private DatePickerDialog.OnDateSetListener dateListener;
    private String clientGetname, riderGetName, adressGet, deliverStartGet, deliverEndGet, statusGet;
    private DatabaseReference mDatabaseReference,statusReference;
    private ValueEventListener listener;
    private  Button deleteOrder, modifyOrder;
    private Date deliverEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_and_delete_order);

        //Database function
        orderRepository = new OrderRepository();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("order");
        statusReference = FirebaseDatabase.getInstance().getReference("status");

        //Get the id about the layout
        nameClient = findViewById(R.id.ClientName);
        nameRider =  findViewById(R.id.DeliveryName);
        address = findViewById(R.id.AddressClient);
        deliverStart = findViewById(R.id.DeliverStart);
        deliverEnd = findViewById(R.id.deliverEnd);



        //EndDate calendar
        deliverEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ModifyAndDeleteOrderActivity.this,
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
                deliverEndDate = new Date(year,month,dayOfMonth);
            }
        };


        clientGetname =  getIntent().getStringExtra("Name_Client");
        riderGetName =  getIntent().getStringExtra("Name_Rider");
        adressGet =  getIntent().getStringExtra("address");
        deliverStartGet =  getIntent().getStringExtra("deliverStart");
        deliverEndGet =  getIntent().getStringExtra("deliverEnd");

        idorder = getIntent().getStringExtra("IdOrder");



        nameClient.setText(clientGetname);
        nameRider.setText(riderGetName);
        address.setText(adressGet);
        deliverStart.setText(deliverStartGet);
        deliverEnd.setText(deliverEndGet);
        setSpinText(statusModify, statusGet);



        //Button
        deleteOrder = findViewById(R.id.buttonDeleteOrder);
        modifyOrder = findViewById(R.id.buttonModifyOrder);


        modifyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order(idorder, nameClient.getText().toString(),nameRider.getText().toString(),
                        nameRoute.getText().toString(),
                        address.getText().toString(),deliverStart.getText().toString()
                        ,deliverEnd.getText().toString()
                        ,statusModify.getSelectedItem().toString());

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String time = formatter.format(deliverEndDate);

                orderRepository.update(order,time, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(ModifyAndDeleteOrderActivity.this,MenuFragementActivity.class));


                        onBackPressed();

                        Log.d("DB", "Order added : success");
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d("DB", "product added : failure");
                    }
                });


            }
        });



        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order( idorder,nameClient.getText().toString(),nameRider.getText().toString(),
                        nameRoute.getText().toString(),
                        address.getText().toString(),deliverStart.getText().toString()
                        ,deliverEnd.getText().toString()
                        ,statusModify.getSelectedItem().toString());


                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String time = formatter.format(deliverEndDate);

                orderRepository.delete(order,time, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {

                        startActivity(new Intent(ModifyAndDeleteOrderActivity.this,MenuFragementActivity.class));


                        onBackPressed();

                        Log.d("DB", "Order added : success");
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d("DB", "product added : failure");
                    }
                });


            }
        });



    }

    public void retrieveData()
    {
        listener = statusReference.addValueEventListener(new ValueEventListener() {
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

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }
}
