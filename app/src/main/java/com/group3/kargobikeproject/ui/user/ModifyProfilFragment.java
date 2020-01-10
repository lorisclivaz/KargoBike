package com.group3.kargobikeproject.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group3.kargobikeproject.Adapter.ListAdapter;
import com.group3.kargobikeproject.MenuFragementActivity;
import com.group3.kargobikeproject.Model.Entity.Location;
import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.group3.kargobikeproject.ViewModel.UserViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class ModifyProfilFragment extends Fragment {

    private User user;
    private UserViewModel viewModel;
    private TextView tv_mail;
    private EditText et_firstName, et_lastName, et_PhoneNumber;
    private Spinner spinner;
    private ListAdapter<String> adpaterLocationList;
    private String userId;
    private Context context;
    private FirebaseUser firebaseUser;
    ArrayList<String> locationName;

    Button returnButton;
    Button safeButton;

    public static ModifyProfilFragment newInstance() {
        return new ModifyProfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_modify_profil, container, false);

        //spinner
        this.spinner=(Spinner) rootView.findViewById(R.id.spinner_locationName);
        this.adpaterLocationList = new ListAdapter<>(ModifyProfilFragment.this.getContext(), R.layout.row_location, new ArrayList<>());
        this.spinner.setAdapter(adpaterLocationList);
        setupViewModels();
        //get current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        userId = firebaseUser.getUid();

        //create connection to model view
        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(),userId);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        // get the user
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                //safe the existing user
                user = userEntity;
                updateContent();
                getActivity().setTitle("Edit " + user.getMail());
            }
        });

        context = getActivity().getApplicationContext();

        //return button to the MainActivity
        returnButton = rootView.findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MenuFragementActivity.class));
            }
        });

        safeButton = rootView.findViewById(R.id.buttonSafe);
        safeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Get the field values
                user.setFirstName(String.valueOf(et_firstName.getText()));
                user.setLastName(String.valueOf(et_lastName.getText()));
                user.setPhoneNumber(String.valueOf(et_PhoneNumber.getText()));
                user.setRegionWorking((String) spinner.getSelectedItem());

                //update the data
                viewModel.updateUser(user, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(getActivity(), MenuFragementActivity.class));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        //Toast.makeText(ModifyProfilActivity.this, "Cannot save changes", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tv_mail = rootView.findViewById(R.id.tv_mail);
        et_firstName = rootView.findViewById(R.id.et_firstName);
        et_lastName = rootView.findViewById(R.id.et_lastName);
        et_PhoneNumber = rootView.findViewById(R.id.et_PhoneNumber);

        return rootView;
    }


    private void updateContent() {
        if (user != null) {
            tv_mail.setText(user.getMail());
            et_firstName.setText(user.getFirstName());
            et_lastName.setText(user.getLastName());
            String phone = String.valueOf(user.getPhoneNumber()) == null ? "" : String.valueOf(user.getPhoneNumber());
            et_PhoneNumber.setText(phone);


            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            // Create a reference with an initial file path and name
            StorageReference pathReference = storageRef.child("users/"+user.getIdUser());
        }
    }

    private void updateAdapterLocationList(List<String> locationName) {
        adpaterLocationList.updateData(new ArrayList<>(locationName));
    }

    private void setupViewModels() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("location");
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        //Array shopnames
                        String workRegion = String.valueOf(user.getRegionWorking()) == null ? "" : String.valueOf(user.getRegionWorking());
                        int workRegioPosition = 0;
                        int countPosition = 0;
                        locationName = new ArrayList<String>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Location loc = ds.getValue(Location.class);
                            locationName.add(loc.getName());
                            if (loc.getName().equals(workRegion)){
                                workRegioPosition=countPosition;
                            }
                            countPosition++;
                        }
                        Collections.swap(locationName,0,workRegioPosition);
                        updateAdapterLocationList(locationName);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

}
