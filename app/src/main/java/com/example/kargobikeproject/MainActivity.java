package com.example.kargobikeproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.kargobikeproject.Model.Entity.Status;
import com.example.kargobikeproject.Model.Entity.User;
import com.example.kargobikeproject.Model.Repository.StatusRepository;
import com.example.kargobikeproject.ViewModels.UserListViewModel;
import com.example.kargobikeproject.ViewModels.UserViewModel;
import com.example.kargobikeproject.util.OnAsyncEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {
Button buttonAddProduct;
Button AddGpsCheckpoint;
Button checkBike;
Button transportOrder;
Button modifyProfil;
Button showCheckPointHistory;
Button addOrder;
Button manageTypes;
Button btn_authorizedUser;

    Button button_DisplayProductList;
//Add status data
    /*
    private static final String TAG = "Order";
    Status status;
    StatusRepository statusRepository;
*/

    //for google authentification
    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    Button btn_login,btn_logout;
    GoogleSignInClient mGoogleSignInClient;
    //end google authentification


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddProduct=findViewById(R.id.buttonAddProduct);
        AddGpsCheckpoint=findViewById(R.id.buttonGps);
        modifyProfil=findViewById(R.id.buttonModifyProfil);
        checkBike=findViewById(R.id.buttonCheckBike);
        transportOrder=findViewById(R.id.TransportOrder);
        showCheckPointHistory = findViewById(R.id.buttonOrderCheckPoint);
        button_DisplayProductList= findViewById(R.id.button_DisplayProductList);

                addOrder = findViewById(R.id.buttonAddOrder);
        manageTypes = findViewById(R.id.buttonManageType);
        btn_authorizedUser = findViewById(R.id.button_authorizeUser);

        //Add status data
        /*
        statusRepository = new StatusRepository();
        status = new Status("unChecked", "inProgress", "checked");
        statusRepository.insert(status, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "status added : success");

                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {

                Log.d(TAG, "status added : success");
            }
        });
                */

        //for google authentification
        btn_login = findViewById(R.id.buttonLoginWithGoogle);
        btn_logout = findViewById(R.id.buttonLogout);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id ))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        btn_login.setOnClickListener(v -> SingInGoogle());
        btn_logout.setOnClickListener(v -> Logout());

        if (mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

        //end google authentification

        btn_authorizedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddAuthorizedUser.class));
            }
        });
        button_DisplayProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProductListActivity.class));
            }
        });
        transportOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListOrderActivity.class));
            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddOrderActivity.class));
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        });

        AddGpsCheckpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GpsCheckPointActivity.class));
            }
        });

        modifyProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ModifyProfilActivity.class));
            }
        });

        checkBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CheckBikeActivity.class));
            }
        });
        showCheckPointHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrderCheckpointActivity.class));
            }
        });

        manageTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListTypesActivity.class));
            }
        });
    }

    //for google authentification
    void SingInGoogle(){
        Intent singIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(singIntent,GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try
            {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if(account != null)firebaseAuthWithGoogle(account);

            }catch (ApiException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        Log.d("TAG","firebaseAuthWithGoogle: "+account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this,task -> {
            if(task.isSuccessful()){
                Log.d("TAG","Singin Success");

                FirebaseUser user = mAuth.getCurrentUser();
                //start of user creation test
                //get all Users
                UserListViewModel allUsersViewModel;
                UserListViewModel.Factory factory = new UserListViewModel.Factory(getApplication());
                allUsersViewModel = ViewModelProviders.of(this, factory).get(UserListViewModel.class);
                allUsersViewModel.getUsers().observe(this, userEntitys -> {
                    if (userEntitys != null) {
                        int userHaveAccess =0;
                        //check if the User exist
                        Boolean userExist = false;
                        for(User listUser : userEntitys){
                            if (listUser.getMail().equals(user.getEmail())){
                                userExist=true;
                                userHaveAccess=listUser.getAccess();
                                break;
                            }
                        }
                        //Create the user if he does not exist
                        if (!userExist){
                            //create the user
                            User userNew = new User (user.getUid(),"","",user.getEmail(),"","",0);
                            //create connection to model view
                            UserViewModel.Factory factoryUser = new UserViewModel.Factory(getApplication(),user.getUid());
                            UserViewModel viewModel = ViewModelProviders.of(this, factoryUser).get(UserViewModel.class);
                            viewModel.createUser(userNew, new OnAsyncEventListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d("ModifyProfilActivity", "createUser: success");
                                }
                                @Override
                                public void onFailure(Exception e) {
                                    Log.d("ModifyProfilActivity", "createUser: failure", e);
                                }
                            });
                        }/*
                        if(userHaveAccess==0){
                            startActivity(new Intent(MainActivity.this, NoAccessActivity.class));
                        }*/

                    }
                });
                //end of user creation test
                updateUI(user);
            }
            else
            {
                Log.w("TAG","Singin Failure",task.getException());

                Toast.makeText(this, "Singin Failed",Toast.LENGTH_SHORT);
                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null)
        {
            String email = user.getEmail();
            String uid = user.getUid();

            Toast.makeText(this, "email : "+email+" uid : "+uid, Toast.LENGTH_SHORT).show();
            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }
    }

    void Logout(){
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            updateUI(null);
            Toast.makeText(this, "You are logout now", Toast.LENGTH_SHORT).show();
        });
    }
    //end google authentification







}
