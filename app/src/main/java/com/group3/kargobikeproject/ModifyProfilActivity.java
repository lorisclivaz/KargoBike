package com.group3.kargobikeproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.ViewModel.UserViewModel;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ModifyProfilActivity extends AppCompatActivity {

    private User user;
    private UserViewModel viewModel;
    private TextView tv_mail;
    private EditText et_firstName, et_lastName, et_PhoneNumber, et_WorkingRegio;
    private String userId;
    private Context context;
    private FirebaseUser firebaseUser;

    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profil);

        //get current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        userId = firebaseUser.getUid();
        initiateView();

        //create connection to model view
        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(),userId);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        // get the user
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                //safe the existing user
                user = userEntity;
                updateContent();
                setTitle("Edit " + user.getMail());
            }
        });

        context = getApplicationContext();

        //return button to the MainActivity
        returnButton = findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyProfilActivity.this, MainActivity.class));
            }
        });
    }

    public void saveUser(View view) {
        //Get the field values
        user.setFirstName(String.valueOf(et_firstName.getText()));
        user.setLastName(String.valueOf(et_lastName.getText()));
        user.setPhoneNumber(String.valueOf(et_PhoneNumber.getText()));
        user.setRegionWorking(String.valueOf(et_WorkingRegio.getText()));

        //update the data
        viewModel.updateUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(ModifyProfilActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Exception e) {
                //Toast.makeText(ModifyProfilActivity.this, "Cannot save changes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateView() {
        tv_mail = findViewById(R.id.tv_mail);
        et_firstName = findViewById(R.id.et_firstName);
        et_lastName = findViewById(R.id.et_lastName);
        et_PhoneNumber = findViewById(R.id.et_PhoneNumber);
        et_WorkingRegio = findViewById(R.id.et_WorkingRegio);
    }

    private void updateContent() {
        if (user != null) {
            tv_mail.setText(user.getMail());
            et_firstName.setText(user.getFirstName());
            et_lastName.setText(user.getLastName());
            String phone = String.valueOf(user.getPhoneNumber()) == null ? "" : String.valueOf(user.getPhoneNumber());
            et_PhoneNumber.setText(phone);
            String workRegion = String.valueOf(user.getRegionWorking()) == null ? "" : String.valueOf(user.getRegionWorking());
            et_WorkingRegio.setText(workRegion);


            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            // Create a reference with an initial file path and name
            StorageReference pathReference = storageRef.child("users/"+user.getIdUser());
        }
    }

}
