package com.group3.kargobikeproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.group3.kargobikeproject.Adapter.ListAdapter;
import com.group3.kargobikeproject.Model.Entity.Location;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Model.Repository.OrderRepository;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddOrderActivity extends AppCompatActivity {

    //Variables about AddOrderActivity
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
    private Button submit;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private Date deliverEndDate;
    private ListAdapter<String> adpaterLocationList;
    private ArrayList<String> locationName;


    //Notification
    private RequestQueue mRequestQue;
    private String UrlServer = "https://fcm.googleapis.com/fcm/send";
    private String NOTIFICATION_TITLE="New Order !";
    private String NOTIFICATION_BODY="Location : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        orderRepository = new OrderRepository();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("status");

        //notification subscribe to a topic called all
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        mRequestQue = Volley.newRequestQueue(AddOrderActivity.this);


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
        submit = findViewById(R.id.buttonSubmit);

        //add data on spinner
        //spinner
        this.adpaterLocationList = new ListAdapter<>(this, R.layout.row_location, new ArrayList<>());
        this.spinnerLocationDelivery.setAdapter(adpaterLocationList);
        this.spinnerLocationClient.setAdapter(adpaterLocationList);


        List<String> listProduct = new ArrayList<String>();
        listProduct.add("product 1");
        listProduct.add("product 2");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listProduct);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerProductSelected.setAdapter(dataAdapter2);

        //StartDate calendar

        //Get the current time
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String formattedDate = df.format(c);
        deliverStart.setText(formattedDate);

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

        deliverEndHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        deliverEndHour.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                deliverEnd.setText(dayOfMonth+"."+(month+1)+"."+year);
            }
        };





        //Send info to firebase to add an order
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = new Order( nameClient.getText().toString(),addressClient.getText().toString(),spinnerLocationClient.getSelectedItem().toString(), nameDelivery.getText().toString(),addressDelivery.getText().toString(),spinnerLocationDelivery.getSelectedItem().toString(),deliverStart.getText().toString(),deliverEnd.getText().toString()+" "+deliverEndHour.getText().toString(),spinnerProductSelected.getSelectedItem().toString());
                Log.d(TAG, "Order added : inside "+nameClient.getText().toString()+" "+addressClient.getText().toString()+" "+spinnerLocationClient.getSelectedItem().toString()+" "+nameDelivery.getText().toString()+" "+addressDelivery.getText().toString()+" "+spinnerLocationDelivery.getSelectedItem().toString()+" "+deliverStart.getText().toString()+" "+deliverEnd.getText().toString()+" "+spinnerProductSelected.getSelectedItem().toString());


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

                orderRepository.insert(order, time, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Order added : success");

                        //Send notification if success
                        sendNotification();

                       startActivity(new Intent(AddOrderActivity.this,MenuFragementActivity.class));

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


    //2eme essaie
    private void sendNotification()
    {
        Log.d("Notifaaa", "2eme senNotification()");
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("to","/topics/all");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title",NOTIFICATION_TITLE);
            notificationObj.put("body",NOTIFICATION_BODY+spinnerLocationClient.getSelectedItem().toString());

            mainObj.put("notification",notificationObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlServer,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Notifaaa", "2eme onResponse ok -> " + response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Notifaaa", "2eme onErrorResponse errore");
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("content-type", "application/json");
                    params.put("authorization", "key=AIzaSyBkh6x30rB6vy7FNh5sj2BK64_yYIbKgwo");
                    return params;
                }
            };

            mRequestQue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateAdapterLocationList(List<String> locationName) {
        adpaterLocationList.updateData(new ArrayList<>(locationName));
    }

    private void setupViewModels() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("location");
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        locationName = new ArrayList<String>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Location loc = ds.getValue(Location.class);
                            locationName.add(loc.getName());
                        }
                        updateAdapterLocationList(locationName);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }


}
