package com.example.kargobikeproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kargobikeproject.Adapter.OrderAdapter;
import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Model.Repository.OrderRepository;
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
    OrderAdapter adapterClass;
    Button buttonViewCheckPoint;
    OrderRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        ref = FirebaseDatabase.getInstance().getReference().child("order");
        recyclerView = findViewById(R.id.recyclerViewOrder);
        searchView = (SearchView) findViewById(R.id.SearchBar);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        orders = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Order o = new Order();
                            o = ds.getValue(Order.class);
                            o.setIdOrder(ds.getKey());
                            orders.add(o);
                        }

                        adapterClass = new OrderAdapter(orders);
                        adapterClass.setOnItemClickListener(new OrderAdapter.onItemCLickListener() {
                            @Override
                            public void onItemClick(int position) {

                                Intent intent;
                                intent = new Intent(ListOrderActivity.this, ModifyAndDeleteOrderActivity.class);
                                startActivity(intent);

                                Log.d("TAG", orders.get(position).getNameClient());
                            }
                        });
                        recyclerView.setAdapter(adapterClass);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (searchView != null) {
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

    private void search(String str) {
        ArrayList<Order> list = new ArrayList<>();

        for (Order object : orders) {
            if (object.getNameClient().toLowerCase().contains(str.toLowerCase())) {
                list.add(object);
            }
        }

        adapterClass = new OrderAdapter(list);
        adapterClass.setOnItemClickListener(new OrderAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent;
                intent = new Intent(ListOrderActivity.this, ModifyAndDeleteOrderActivity.class);
                startActivity(intent);

                Log.d("TAG", orders.get(position).getNameClient());
            }
        });
        recyclerView.setAdapter(adapterClass);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, Menu.NONE, "add Order")
                .setIcon(R.drawable.ic_plus)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == 1) {
            Toast.makeText(ListOrderActivity.this, "Add Order", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListOrderActivity.this, AddOrderActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
