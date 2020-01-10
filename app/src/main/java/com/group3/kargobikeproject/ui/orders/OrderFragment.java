package com.group3.kargobikeproject.ui.orders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group3.kargobikeproject.Adapter.OrderAdapter;
import com.group3.kargobikeproject.AddOrderActivity;
import com.group3.kargobikeproject.Model.Entity.BikeService;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Model.Repository.OrderRepository;
import com.group3.kargobikeproject.ModifyAndDeleteOrderActivity;
import com.group3.kargobikeproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.group3.kargobikeproject.ViewModel.BikeServiceViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderFragment extends Fragment {


    public static OrderFragment newInstance() {
        return new OrderFragment();
    }
    static final String Extra_ClientName = "NameClient";
    DatabaseReference ref;
    ArrayList<Order> orders;
    ArrayAdapter listAdapter;
    RecyclerView recyclerView;
    SearchView searchView;
    OrderAdapter adapterClass;
    Order clickOrder;
    Button addOrder;
    Button buttonViewCheckPoint;
    OrderRepository repository;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        checkBike();
        return inflater.inflate(R.layout.order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get the time, if it comes from the history, otherwise it comes from main and then create thisday
        String thisDay;
        try
        {
            thisDay = getArguments().getString("time","default");
        }catch(Exception e){
            Date dateToday = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            thisDay = formatter.format(dateToday);
        }
        ref = FirebaseDatabase.getInstance().getReference().child("order/"+thisDay);
        recyclerView = getView().findViewById(R.id.recyclerViewUser);
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
    private void checkBike(){
        //get authentication
        mAuth = FirebaseAuth.getInstance();
        //Repository for the BikeService
        BikeServiceViewModel.Factory factory = new BikeServiceViewModel.Factory(getActivity().getApplication(),"0");
        BikeServiceViewModel viewModelService = ViewModelProviders.of(this, factory).get(BikeServiceViewModel.class);
        //Check bike alert every day
        Calendar calendar = Calendar.getInstance();
        //get current day
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences settings = this.getActivity().getSharedPreferences("prefs",0);
        //get last day safed or default 0
        int lastDay = settings.getInt("day",0);

        // when the last day is not today, set today and show alert
        if (lastDay != currentDay){
            //alert to check the bike
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setCancelable(false)
                    .setTitle("Confirmation")
                    .setMessage("When you click \"checked\", you confirme that you check your bike " +
                            "and the bike is ready for use and nothing is defective.")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user == null){
                                        builder.show();
                                    } else {
                                        // add line in db with user and date
                                        Date date = new Date();
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        SimpleDateFormat formatterTime = new SimpleDateFormat("dd-MM-yyyy");
                                        String thisDay = formatter.format(date);
                                        String time = formatterTime.format(date);
                                        BikeService service = new BikeService(thisDay,user.getUid());
                                        viewModelService.createBikeService(service,time, new OnAsyncEventListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d("MainActivityBikeCheck", "createService: success");
                                                //the user have checked the bike for that day
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putInt("day",currentDay);
                                                editor.commit();
                                            }
                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d("MainActivityBikeCheck", "createService: failure", e);
                                            }
                                        });

                                    }
                                }
                            });
            builder.show();
        }

    }


}
