package com.example.kargobikeproject.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kargobikeproject.Adapter.OrderAdapter;
import com.example.kargobikeproject.AddOrderActivity;
import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Model.Repository.OrderRepository;
import com.example.kargobikeproject.ModifyAndDeleteOrderActivity;
import com.example.kargobikeproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private OrderViewModel mViewModel;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }
    static final String Extra_ClientName = "NameClient";
    DatabaseReference ref;
    ArrayList<Order> orders;
    RecyclerView recyclerView;
    SearchView searchView;
    OrderAdapter adapterClass;
    Order clickOrder;
    Button buttonViewCheckPoint;
    OrderRepository repository;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ref = FirebaseDatabase.getInstance().getReference().child("order");
        recyclerView = getView().findViewById(R.id.recyclerViewOrder);
        searchView = (SearchView) getView().findViewById(R.id.SearchBar);
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

                                Intent intent = new Intent(getActivity(), ModifyAndDeleteOrderActivity.class);
                                intent.putExtra("Name_Client", orders.get(position).getNameClient());
                                intent.putExtra("Name_Rider", orders.get(position).getNameRider());
                                intent.putExtra("Name_Route", orders.get(position).getNameRoute());
                                intent.putExtra("address", orders.get(position).getAddress());
                                intent.putExtra("deliverStart", orders.get(position).getDeliverStart());
                                intent.putExtra("deliverEnd", orders.get(position).getDeliverEnd());
                                intent.putExtra("status", orders.get(position).getOrderStatus());

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
                intent = new Intent(getActivity(), ModifyAndDeleteOrderActivity.class);
                clickOrder = orders.get(position);

                intent.putExtra("Name_Client", orders.get(position).getNameClient());
                intent.putExtra("Name_Rider", orders.get(position).getNameRider());
                intent.putExtra("Name_Route", orders.get(position).getNameRoute());
                intent.putExtra("address", orders.get(position).getAddress());
                intent.putExtra("deliverStart", orders.get(position).getDeliverStart());
                intent.putExtra("deliverEnd", orders.get(position).getDeliverEnd());
                intent.putExtra("status", orders.get(position).getOrderStatus());



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
            Toast.makeText(getActivity(), "Add Order", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AddOrderActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
