package com.example.kargobikeproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kargobikeproject.Adapter.OrderAdapter;
import com.example.kargobikeproject.Model.Entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOrderActivity extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Order> orders;
    RecyclerView recyclerView;
    SearchView searchView;
    Button buttonViewCheckPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

    ref = FirebaseDatabase.getInstance().getReference().child("order");
    recyclerView = findViewById(R.id.recyclerViewOrder);
    searchView = (SearchView) findViewById(R.id.SearchBar);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(ref!=null)
        {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        orders = new ArrayList<>();

                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            Order o = new Order();
                            o = ds.getValue(Order.class);
                            o.setIdOrder(ds.getKey());
                            orders.add(o);
                        }

                        OrderAdapter adapterClass = new OrderAdapter(orders);
                        recyclerView.setAdapter(adapterClass);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str)
    {
        ArrayList<Order> orders = new ArrayList<>();

        for (Order object : orders)
        {
            if (object.getNameClient().toLowerCase().contains(str.toLowerCase()))
            {
                orders.add(object);
            }
        }

        OrderAdapter orderAdapter = new OrderAdapter(orders);
        recyclerView.setAdapter(orderAdapter);
    }
}
