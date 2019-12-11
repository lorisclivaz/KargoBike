package com.group3.kargobikeproject.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.group3.kargobikeproject.Adapter.UserAdapter;
import com.group3.kargobikeproject.AddOrderActivity;
import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.ModifyAndDeleteOrderActivity;
import com.group3.kargobikeproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFragment extends Fragment {




    DatabaseReference ref;
    ArrayList<User> users;
    RecyclerView recyclerView;
    SearchView searchView;
    UserAdapter adapterClass;
    Order clickOrder;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ref = FirebaseDatabase.getInstance().getReference().child("users");
        recyclerView = getView().findViewById(R.id.recyclerViewUser);
        searchView = (SearchView) getView().findViewById(R.id.SearchBarUser);
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        users = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User o = new User();
                            o = ds.getValue(User.class);
                            o.setIdUser(ds.getKey());
                            users.add(o);
                        }

                        adapterClass = new UserAdapter(users);
                        adapterClass.setOnItemClickListener(new UserAdapter.onItemCLickListener() {
                            @Override
                            public void onItemClick(int position) {


                                Intent intent = new Intent(getActivity(), ModifyAndDeleteOrderActivity.class);
                                intent.putExtra("IdUser", users.get(position).getIdUser());


                                startActivity(intent);



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



    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, Menu.NONE, "UserList")
                .setIcon(R.drawable.ic_plus)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == 1) {
            Toast.makeText(getActivity(), "UserList", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AddOrderActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
