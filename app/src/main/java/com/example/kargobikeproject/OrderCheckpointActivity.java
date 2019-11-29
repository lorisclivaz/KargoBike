package com.example.kargobikeproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.kargobikeproject.Adapter.CheckPointAdapter;
import com.example.kargobikeproject.Adapter.OrderAdapter;
import com.example.kargobikeproject.Fragment.AddCheckPointDialog;
import com.example.kargobikeproject.Model.Entity.CheckPoint;
import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Model.Repository.CheckPointRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class    OrderCheckpointActivity extends AppCompatActivity implements AddCheckPointDialog.OnInputListener{
    DatabaseReference ref;
    private ArrayList<CheckPoint> checkPoints = new ArrayList<CheckPoint>();
    TextView textOrderCheckPointHistory;
    RecyclerView listCheckPoints;
    CheckPointRepository checkPointRepository;
    Button addCheckPoint;
    public String inputGot;
    public String userEmail;
    String idOrderThis;
    private FirebaseUser firebaseUser = null;
   // private ArrayList<String> checkPointList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_checkpoints);
        textOrderCheckPointHistory = findViewById(R.id.textOrderCheckPointHistory);
        textOrderCheckPointHistory.setText("Checkpoints History");
        idOrderThis = getIntent().getStringExtra("ORDER_ID");
        ref = FirebaseDatabase.getInstance().getReference().child("checkPoint");
        listCheckPoints = findViewById(R.id.listCheckPoints);
        setUpAddCheckPointButton();
        checkPointRepository = new CheckPointRepository();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null) {
            userEmail = firebaseUser.getEmail();
        }




    }
    @Override
    protected void onStart()
    {
        super.onStart();

        if(ref!=null)
        {
            Query newQuery = ref.orderByChild("idOrder").equalTo(idOrderThis);
            newQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        checkPoints = new ArrayList<>();

                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {

                            checkPoints.add(ds.getValue(CheckPoint.class));
                        }

                        CheckPointAdapter adapterClass = new CheckPointAdapter(checkPoints);
                        listCheckPoints.setAdapter(adapterClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setUpAddCheckPointButton () {
        addCheckPoint = findViewById(R.id.buttonAddCheckpoint);
        addCheckPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hey", "onClick: opening dialog");
                AddCheckPointDialog dialog = new AddCheckPointDialog();
                dialog.show(getSupportFragmentManager(), "AddCheckPointDialog");

            }
        });
    }

    @Override
    public void sendInput(String input) {
        Log.d("hey", "sendInput: got the input: " + input);
        inputGot = input;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(userEmail!=null) {
            CheckPoint newCP = new CheckPoint(getIntent().getStringExtra("ORDER_ID"),inputGot, userEmail, formatter.format(date));
            checkPointRepository.insert(newCP, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d("checkpoint", "checkpoint added");
                    Toast.makeText(OrderCheckpointActivity.this, "new checkpoint added",
                            Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Exception e) {
                    Log.d("checkpoint", "checkpoint not added");
                    Toast.makeText(OrderCheckpointActivity.this, "new checkpoint was not added",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(OrderCheckpointActivity.this, "please log in first",
                    Toast.LENGTH_SHORT).show();
        }
    }
}




