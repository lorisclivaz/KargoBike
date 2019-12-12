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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Model.Repository.OrderRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private Spinner status;
    private TextInputEditText nameClient;
    private TextInputEditText nameRider;
    private TextInputEditText route;
    private TextInputEditText address;
    private TextView deliverStart, deliverEnd;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Button submit;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    //For Notifcation after addOrder
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAdVDA-h8:APA91bHscyYtZBcHwNsYNroNz_HdevrFu2LxXJYz04kRlW3mSZPhIxF12kv-nPhoP1rPuud-XPhtT-G9T5mchVfyJtKR6IuWaXq7YW0wPSbt6cv-1pNIF1MN315m8l-4JcZ8p0sp0uSJ";
    final private String contentType = "application/json";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;



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
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
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



        //Send info to firebase to add an order
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

                        //For Notifcation after addOrder
                        TOPIC = "/topics/all"; //topic must match with what the receiver subscribed to
                        NOTIFICATION_TITLE = "Titre de la notif";
                        NOTIFICATION_MESSAGE = "Message blabla";

                        JSONObject notification = new JSONObject();
                        JSONObject notifcationBody = new JSONObject();

                        try {
                            notifcationBody.put("title", NOTIFICATION_TITLE);
                            notifcationBody.put("message", NOTIFICATION_MESSAGE);

                            notification.put("to", TOPIC);
                            notification.put("data", notifcationBody);
                        } catch (JSONException e) {
                            Log.e(TAG, "onCreate: " + e.getMessage() );
                        }
                        sendNotification(notification);


                        startActivity(new Intent(AddOrderActivity.this,MenuFragementActivity.class));



                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d(TAG, "product added : failure");
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

    //For Notifcation after addOrder
    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(AddOrderActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


}
