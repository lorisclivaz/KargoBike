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

import com.group3.kargobikeproject.Adapter.ListAdapter;
import com.group3.kargobikeproject.Adapter.OrderAdapter;
import com.group3.kargobikeproject.Model.Entity.Location;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Model.Entity.Product;
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
import java.util.Collections;
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
    private ListAdapter<String> adpaterLocationListClient;
    private ListAdapter<String> adpaterLocationListDestination;
    private ArrayList<String> locationNameClient;
    private ArrayList<String> locationNameDesination;
    private ListAdapter<String> adpaterProductList;
    private ArrayList<String> productName;


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
        deliverEndHour.setText("");
        //add data on spinner
        //spinner
        this.adpaterLocationListClient = new ListAdapter<>(this, R.layout.row_location, new ArrayList<>());
        this.adpaterLocationListDestination = new ListAdapter<>(this, R.layout.row_location, new ArrayList<>());
        this.spinnerLocationDelivery.setAdapter(adpaterLocationListDestination);
        this.spinnerLocationClient.setAdapter(adpaterLocationListClient);

        this.adpaterProductList = new ListAdapter<>(this, R.layout.row_location, new ArrayList<>());
        this.spinnerProductSelected.setAdapter(adpaterProductList);

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


        setupViewModels();

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


    private void updateAdapterLocationListClient(List<String> locationName) {
        adpaterLocationListClient.updateData(new ArrayList<>(locationName));
    }

    private void updateAdapterLocationListDestination(List<String> locationName) {
        adpaterLocationListDestination.updateData(new ArrayList<>(locationName));
    }

    private void updateAdapterProductList(List<String> productName) {
        adpaterProductList.updateData(new ArrayList<>(productName));
    }

    private void setupViewModels() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("location");
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        String locationSource = getIntent().getStringExtra("LocationClient");
                        String locationDestination = getIntent().getStringExtra("LocationDelivery");
                        int locationSourcePosition = 0;
                        int locationDestinationPosition = 0;
                        int count=0;
                        locationNameClient = new ArrayList<String>();
                        locationNameDesination = new ArrayList<String>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Location loc = ds.getValue(Location.class);
                            locationNameClient.add(loc.getName());
                            locationNameDesination.add(loc.getName());
                            if (loc.getName().equals(locationSource)){
                                locationSourcePosition=count;
                            }
                            if (loc.getName().equals(locationDestination)){
                                locationDestinationPosition=count;
                            }
                            count++;
                        }
                        Collections.swap(locationNameClient,0,locationSourcePosition);
                        Collections.swap(locationNameDesination,0,locationDestinationPosition);
                        updateAdapterLocationListClient(locationNameClient);
                        updateAdapterLocationListDestination(locationNameDesination);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //product
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("products");
        if (ref2 != null) {
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        productName = new ArrayList<String>();
                        String  productN = getIntent().getStringExtra("ProductSelected");
                        int productPosition = 0;
                        int count=0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Product loc = ds.getValue(Product.class);
                            productName.add(loc.getName()+" "+loc.getDescription()+" "+loc.getPrice());
                            if (productN.equals(loc.getName()+" "+loc.getDescription()+" "+loc.getPrice())){
                                productPosition=count;
                            }
                            count++;
                        }
                        Collections.swap(locationNameClient,0,productPosition);
                        updateAdapterProductList(productName);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

}
