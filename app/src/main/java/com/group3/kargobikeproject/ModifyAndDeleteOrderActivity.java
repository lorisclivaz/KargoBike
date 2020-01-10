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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ModifyAndDeleteOrderActivity extends AppCompatActivity {

    //Variables about ModifyAndDeleteOrderActivity
    private static final String TAG = "Order";
    private DatabaseReference mDatabaseReference;
    private ValueEventListener listener;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> spinnerDataList;
    private OrderRepository orderRepository;
    private Order order ;
    private Spinner spinnerLocationClient;
    private Spinner spinnerLocationDelivery;
    private Spinner spinnerProductSelected;
    private TextInputEditText nameClient;
    private TextInputEditText nameDelivery;
    private TextInputEditText addressClient;
    private TextInputEditText addressDelivery;
    private TextView deliverStart, deliverEnd,deliverEndHour;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Button deleteOrder;
    private Button modifyOrder;

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private Date deliverEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_and_delete_order);

        //Database function
        orderRepository = new OrderRepository();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("order");


        //Find in the layout
        nameClient = findViewById(R.id.ClientName);
        addressClient = findViewById(R.id.AddressClient);
        spinnerLocationClient = findViewById(R.id.spinnerLocationClient);
        nameDelivery = findViewById(R.id.DeliveryName);
        addressDelivery = findViewById(R.id.deliveryAddress);
        spinnerLocationDelivery = findViewById(R.id.spinnerLocationDelivery);
        spinnerProductSelected = findViewById(R.id.spinnerProductSelected);
        deliverStart = findViewById(R.id.deliverStart);
        deliverEnd = findViewById(R.id.deliverEnd);
        deliverEndHour = findViewById(R.id.deliverEndHours);
        deleteOrder = findViewById(R.id.buttonDelete);
        modifyOrder = findViewById(R.id.buttonUpdate);

        nameClient.setText(getIntent().getStringExtra("Name_Client"));
        addressClient.setText(getIntent().getStringExtra("AddressClient"));
        nameDelivery.setText(getIntent().getStringExtra("DeliveryName"));
        addressDelivery.setText(getIntent().getStringExtra("DeliveryAdress"));
        deliverStart.setText(getIntent().getStringExtra("DeliverStart"));
        deliverEnd.setText(getIntent().getStringExtra("DeliveryEnd"));




        //add data on spinner
        List<String> listLocationClient = new ArrayList<String>();
        listLocationClient.add("Sierre");
        listLocationClient.add("Sion");
        listLocationClient.add("Martigny");
        listLocationClient.add("Monthey");
        listLocationClient.add("Visp");
        listLocationClient.add("Brig");
        listLocationClient.add("Lausanne");
        listLocationClient.add("Verbier");
        listLocationClient.add("Crans Montana");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listLocationClient);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        List<String> listProduct = new ArrayList<String>();
        listProduct.add("product 1");
        listProduct.add("product 2");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listProduct);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocationClient.setAdapter(dataAdapter);
        spinnerLocationDelivery.setAdapter(dataAdapter);
        spinnerProductSelected.setAdapter(dataAdapter2);
        int spinnerPositionClient = dataAdapter .getPosition(getIntent().getStringExtra("LocationClient"));
        int spinnerPositionDelivery = dataAdapter .getPosition(getIntent().getStringExtra("LocationDelivery"));
        int spinnerPositionProductSelected = dataAdapter2.getPosition(getIntent().getStringExtra("ProductSelected"));

        spinnerLocationClient.setSelection(spinnerPositionClient);
        spinnerLocationDelivery.setSelection(spinnerPositionDelivery);
        spinnerProductSelected.setSelection(spinnerPositionProductSelected);


        modifyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order( nameClient.getText().toString(),addressClient.getText().toString(),spinnerLocationClient.getSelectedItem().toString(), nameDelivery.getText().toString(),addressDelivery.getText().toString(),spinnerLocationDelivery.getSelectedItem().toString(),deliverStart.getText().toString(),deliverEnd.getText().toString()+" "+deliverEndHour.getText().toString(),spinnerProductSelected.getSelectedItem().toString());
                Log.d(TAG, "Order added : inside "+nameClient.getText().toString()+" "+addressClient.getText().toString()+" "+spinnerLocationClient.getSelectedItem().toString()+" "+nameDelivery.getText().toString()+" "+addressDelivery.getText().toString()+" "+spinnerLocationDelivery.getSelectedItem().toString()+" "+deliverStart.getText().toString()+" "+deliverEnd.getText().toString()+" "+spinnerProductSelected.getSelectedItem().toString());
                order.setIdOrder(getIntent().getStringExtra("IdOrder"));

                String dateString = deliverEnd.getText().toString();
                try {
                    deliverEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
                    Log.d(TAG, "Order added :deliverEndDate "+deliverEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Order added :Erreur e ");
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String time = formatter.format(deliverEndDate);
                Log.d(TAG, "Order added :time"+time);

                orderRepository.update(order, time, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Order added : success");


                        startActivity(new Intent(ModifyAndDeleteOrderActivity.this,MenuFragementActivity.class));

                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d(TAG, "Order added : failure");
                    }
                });


            }
        });

        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order( nameClient.getText().toString(),addressClient.getText().toString(),spinnerLocationClient.getSelectedItem().toString(), nameDelivery.getText().toString(),addressDelivery.getText().toString(),spinnerLocationDelivery.getSelectedItem().toString(),deliverStart.getText().toString(),deliverEnd.getText().toString()+" "+deliverEndHour.getText().toString(),spinnerProductSelected.getSelectedItem().toString());
                Log.d(TAG, "Order added : inside "+nameClient.getText().toString()+" "+addressClient.getText().toString()+" "+spinnerLocationClient.getSelectedItem().toString()+" "+nameDelivery.getText().toString()+" "+addressDelivery.getText().toString()+" "+spinnerLocationDelivery.getSelectedItem().toString()+" "+deliverStart.getText().toString()+" "+deliverEnd.getText().toString()+" "+spinnerProductSelected.getSelectedItem().toString());
                order.setIdOrder(getIntent().getStringExtra("IdOrder"));

                String dateString = deliverEnd.getText().toString();
                try {
                    deliverEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
                    Log.d(TAG, "Order added :deliverEndDate "+deliverEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Order added :Erreur e ");
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String time = formatter.format(deliverEndDate);
                Log.d(TAG, "Order added :time"+time);

                orderRepository.delete(order, time, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Order added : success");


                        startActivity(new Intent(ModifyAndDeleteOrderActivity.this,MenuFragementActivity.class));

                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d(TAG, "Order added : failure");
                    }
                });


            }
        });




    }
    //Methode for the spinner to take information
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
