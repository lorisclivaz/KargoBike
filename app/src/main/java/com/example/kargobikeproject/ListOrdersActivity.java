package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.kargobikeproject.Adapter.OrderAdapter;

import java.util.ArrayList;

public class ListOrdersActivity extends AppCompatActivity {

    ArrayList<Order> orders;
    private ListView maListeV;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        maListeV = (ListView)findViewById(R.id.ListOrder) ;

        orderAdapter = new OrderAdapter(getApplicationContext(), 0);
        orders = new ArrayList<>();

        orders.add(new Order(1,2,"Pizza"));
        orders.add(new Order(1,2,"thai"));
        orders.add(new Order(1,2,"jeuxVIdeo"));
        orders.add(new Order(1,2,"Console de jeux"));

        maListeV.setAdapter(orderAdapter);
        orderAdapter.addAll(orders );
    }
}
