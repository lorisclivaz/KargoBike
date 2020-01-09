package com.group3.kargobikeproject.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.Adapter.OrderAdapter;
import com.group3.kargobikeproject.AddOrderActivity;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Model.Repository.OrderRepository;
import com.group3.kargobikeproject.ModifyAndDeleteOrderActivity;
import com.group3.kargobikeproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryFragment extends Fragment {


    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
    }
    static final String Extra_ClientName = "NameClient";
    DatabaseReference ref;
    ArrayList<Order> orders;
    ArrayAdapter listAdapter;
    RecyclerView recyclerView;
    SearchView searchView;
    OrderAdapter adapterClass;
    Order clickOrder;
    Button buttonViewCheckPoint;
    TextView tv_date;
    OrderRepository repository;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.history_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Date dateToday = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String thisDay = formatter.format(dateToday);

        ref = FirebaseDatabase.getInstance().getReference().child("order/"+thisDay);
        recyclerView = getView().findViewById(R.id.recyclerViewUser);
        searchView = (SearchView) getView().findViewById(R.id.SearchBarUser);
        tv_date = (TextView) getView().findViewById(R.id.tv_titleHistoryOrder);
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
                                intent.putExtra("IdOrder", orders.get(position).getIdOrder());

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

    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, 1, Menu.NONE, "add Order")
                .setIcon(R.drawable.ic_plus)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
