package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.kargobikeproject.Model.Repository.OrderRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class AddOrderActivity extends AppCompatActivity {
    private static final String TAG = "Order";
    OrderRepository orderRepository;
    Order order ;
    TextInputEditText address;
    TextInputEditText deliverStart;
    TextInputEditText deliverEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        orderRepository = new OrderRepository();



        /*
       order = new Order(1,1,1,1,"Chemin des plannetes 20","12.12.2019","14.12.2019",1);

       orderRepository.insert(order, new OnAsyncEventListener() {
           @Override
           public void onSuccess() {
               Log.d(TAG, "product added : success");

               onBackPressed();
           }

           @Override
           public void onFailure(Exception e) {

               Log.d(TAG, "product added : failure");
           }
       });*/
    }
}
