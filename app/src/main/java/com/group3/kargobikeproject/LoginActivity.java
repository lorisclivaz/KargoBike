package com.group3.kargobikeproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.group3.kargobikeproject.ViewModel.UserListViewModel;
import com.group3.kargobikeproject.ViewModel.UserViewModel;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    Button buttonAddProduct;
    Button transportOrder;
    Button modifyProfil;
    Button manageTypes;
    Button btn_authorizedUser;
    Button button_DisplayProductList;
    Button getSignature, takePicture;
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
        setContentView(R.layout.activity_login);
        buttonAddProduct=findViewById(R.id.buttonAddProduct);
        modifyProfil=findViewById(R.id.buttonModifyProfil);
        transportOrder=findViewById(R.id.TransportOrder);
        button_DisplayProductList= findViewById(R.id.button_DisplayProductList);
        manageTypes = findViewById(R.id.buttonManageType);
        btn_authorizedUser = findViewById(R.id.button_authorizeUser);
        getSignature = findViewById(R.id.signatureButton);
        takePicture = findViewById(R.id.takePictureButton);

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

        //Check the Bike if it is the first time this day to login
        //checkBike();

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


        if (mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

        //end google authentification

    }

    //for google authentification
    void SingInGoogle(){
        Intent singIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(singIntent,GOOGLE_SIGN);
    }

    private void checkBike(){
        //Check bike alert every day
        Calendar calendar = Calendar.getInstance();
        //get current day
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences settings = getSharedPreferences("prefs",0);
        //get last day safed or default 0
        int lastDay = settings.getInt("day",0);

        // when the last day is not today, set today and show alert
        if (lastDay != currentDay){
            //alert to check the bike
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle("Confirmation")
                    .setMessage("When you click \"checked\", you confirme that you check your bike " +
                            "and the bike is ready for use and nothing is defective. \n Login first.")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user == null){
                                        builder.show();
                                    } else {
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putInt("day",currentDay);
                                        editor.commit();
                                        // add line in db with user and date

                                    }
                                }
                            })
                    .setNegativeButton("Login",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SingInGoogle();
                                    builder.show();
                                }
                            });
            builder.show();
        }

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
                        // when the user has no access, he will redirected to the noaccessactivity and can't go back
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
            startActivity(new Intent(LoginActivity.this,MenuFragementActivity.class));
        }
        else
        {
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.GONE);
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
