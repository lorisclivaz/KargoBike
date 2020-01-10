package com.group3.kargobikeproject.ui.authoriseUser;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.AddAuthorizedUser;
import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.group3.kargobikeproject.ViewModel.UserViewModel;

import java.util.ArrayList;

public class AuthoriseUserFragment extends Fragment {

    private AuthoriseUserViewModel mViewModel;
    private DatabaseReference mDatabaseReference;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayUser;
    ArrayList<User> userList;
    ValueEventListener listener;
    Spinner spinnerUser;
    Switch switchAuth;
    Switch switchIsDispatcher;
    Boolean switchState;
    UserViewModel viewModel;
    User userSelected;
    public static AuthoriseUserFragment newInstance() {
        return new AuthoriseUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authorise_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AuthoriseUserViewModel.class);
        spinnerUser = (Spinner) getView().findViewById(R.id.spinnerUserAuth);
        switchAuth = getView().findViewById(R.id.switchAuth);
        switchState = switchAuth.isChecked();
        switchIsDispatcher=getView().findViewById(R.id.switchIsDispatcher);

        spinnerUser.setPrompt("Select the user");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

        arrayUser  = new ArrayList<>();
        userList  = new ArrayList<>();
        userSelected = new User();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, arrayUser);

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

                        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(),"0");
                        viewModel = ViewModelProviders.of(getActivity(), factory).get(UserViewModel.class);

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

        switchIsDispatcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int role =0;

                if (isChecked)
                    role=1;

                userSelected.setRole(role);
                System.out.println(role);
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
                    int role = item.child("role").getValue(Integer.class);
                    int isAccess = item.child("access").getValue(Integer.class);

                    User user = new User(key,firstname,lastname,mail,regionWorking,phoneNumber,isAccess,role);

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
