package com.example.kargobikeproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

public class ModifyAndDeleteOrderActivity extends AppCompatActivity {


    TextInputEditText nameClient, nameRider, nameRoute, address, deliverStart, deliverEnd;
    Spinner status;
    ArrayList<String> spinnerDataList;
    ArrayAdapter<String> adapter;
    ListOrderActivity ordermethode;
    Order order;
    OrderRepository orderRepository;

    String clientGetname, riderGetName, routeGetName, adressGet, deliverStartGet, deliverEndGet, statusGet;


    private DatabaseReference mDatabaseReference;
    ValueEventListener listener;

    Button deleteOrder, modifyOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_and_delete_order);

        orderRepository = new OrderRepository();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("order");


        nameClient = findViewById(R.id.NameClient);
        nameRider =  findViewById(R.id.NameRider);
        nameRoute = findViewById(R.id.nameRoute);
        address = findViewById(R.id.Address);
        deliverStart = findViewById(R.id.DeliverStart);
        deliverEnd = findViewById(R.id.DeliverEnd);

        //Spinner Status
        status = (Spinner)findViewById(R.id.spinnerStatus);
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(ModifyAndDeleteOrderActivity.this,android.R.layout.simple_spinner_dropdown_item, spinnerDataList);

        status.setAdapter(adapter);
        retrieveData();


        clientGetname =  getIntent().getStringExtra("Name_Client");
        riderGetName =  getIntent().getStringExtra("Name_Rider");
        routeGetName =  getIntent().getStringExtra("Name_Route");
        adressGet =  getIntent().getStringExtra("address");
        deliverStartGet =  getIntent().getStringExtra("deliverStart");
        deliverEndGet =  getIntent().getStringExtra("deliverEnd");
        statusGet =  getIntent().getStringExtra("status");

        nameClient.setText(clientGetname);
        nameRider.setText(riderGetName);
        nameRoute.setText(routeGetName);
        address.setText(adressGet);
        deliverStart.setText(deliverStartGet);
        deliverEnd.setText(deliverEndGet);

        setSpinText(status, statusGet);


        //Button
        deleteOrder = findViewById(R.id.buttonDeleteOrder);
        modifyOrder = findViewById(R.id.buttonModifyOrder);



        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order( nameClient.getText().toString(),nameRider.getText().toString(),
                        nameRoute.getText().toString(),
                        address.getText().toString(),deliverStart.getText().toString()
                        ,deliverEnd.getText().toString()
                        ,status.getSelectedItem().toString());

                orderRepository.delete(order, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {

                        startActivity(new Intent(ModifyAndDeleteOrderActivity.this, ListOrderActivity.class));

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
