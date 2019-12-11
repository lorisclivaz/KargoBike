package com.group3.kargobikeproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.group3.kargobikeproject.ViewModel.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddAuthorizedUser extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayUser;
    ArrayList<User> userList;
    ValueEventListener listener;
    Spinner spinnerUser;
    Switch switchAuth;
    Boolean switchState;
    UserViewModel viewModel;
    User userSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized_user);

        spinnerUser = (Spinner) findViewById(R.id.spinnerUserAuth);
        switchAuth = findViewById(R.id.switchAuth);
        switchState = switchAuth.isChecked();


        spinnerUser.setPrompt("Select the user");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

        arrayUser  = new ArrayList<>();
        userList  = new ArrayList<>();
        userSelected = new User();
        adapter = new ArrayAdapter<String>(AddAuthorizedUser.this,android.R.layout.simple_spinner_dropdown_item, arrayUser);

        spinnerUser.setAdapter(adapter);
        retrieveData();

        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                for (User u:userList)
                {
                    if (u.getMail().equals(spinnerUser.getSelectedItem().toString()))
                    {

                        userSelected = u;
                        Log.d("ICCCIIIIII"," id user = "+userSelected.getIdUser());

                        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(),"0");
                        viewModel = ViewModelProviders.of(AddAuthorizedUser.this, factory).get(UserViewModel.class);

                        if (u.getAccess() == 0){
                            switchState=false;
                        }  else{
                            switchState=true;
                        }

                        switchAuth.setChecked(switchState);
                        Log.d("ICCCIIIIII","switch = "+switchState +" user = "+userSelected.getMail());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        switchAuth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                int access =0;

                if (isChecked)
                    access=1;

                userSelected.setAccess(access);

                viewModel.updateUser(userSelected, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        
                        Log.d("ICCCIIIIII","Modification ok");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("ICCCIIIIII","Erreur modification");
                    }
                });
            }
        });


    }


    public void retrieveData()
    {
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();
                arrayUser.clear();
                for(DataSnapshot item:dataSnapshot.getChildren()){

                    String key = item.getKey();
                    String mail = item.child("mail").getValue(String.class);
                    String firstname = item.child("firstName").getValue(String.class);
                    String lastname = item.child("lastName").getValue(String.class);
                    String regionWorking = item.child("regionWorking").getValue(String.class);
                    String phoneNumber = item.child("phoneNumber").getValue(String.class);
                    int isAccess = item.child("access").getValue(Integer.class);

                    User user = new User(key,firstname,lastname,mail,regionWorking,phoneNumber,isAccess);

                    userList.add(user);

                    arrayUser.add(mail);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
