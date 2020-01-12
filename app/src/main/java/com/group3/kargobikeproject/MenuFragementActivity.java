package com.group3.kargobikeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.Model.Entity.User;

import java.util.ArrayList;
import java.util.Calendar;

public class MenuFragementActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button btn_login,btn_logout;
    TextView tv_userEmail ;
    String extraEmail;
    private DatabaseReference mDatabaseReference;
    ValueEventListener listener;
    private AppBarConfiguration mAppBarConfiguration;
    private Button buttonLogout;
    int role;
    ArrayList<User> users;
    ArrayList<String> arrayUser;
    NavigationView navigationView;
    ArrayList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fragement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        extraEmail =getIntent().getStringExtra("email_Client");
      //  tv_userEmail.setText("test");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
         navigationView = (NavigationView) findViewById(R.id.nav_view);

        retrieveData();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_orders , R.id.nav_home,R.id.nav_user, R.id.nav_autorise,R.id.nav_bikeservice ,
                R.id.nav_logout, R.id.nav_checkpointtype, R.id.nav_modifyProfil, R.id.nav_products, R.id.nav_user,R.id.nav_historyOrder)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragement, menu);
        tv_userEmail=findViewById(R.id.textView_email);
        tv_userEmail.setText(extraEmail);
        MenuItem aboutItem = menu.findItem(R.id.action_settings);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MenuFragementActivity.this,AboutActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void retrieveData()
    {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    String mail = item.child("mail").getValue(String.class);

                    if(mail.equals(extraEmail)) {
                        role = item.child("role").getValue(Integer.class);
                        System.out.println(mail+"  "+extraEmail+item.child("role").getValue(Integer.class));

                    }
                }
                if(role==0)
                    hideItem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.superUserGroup).setVisible(false);

    }


}
