package com.group3.kargobikeproject;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.group3.kargobikeproject.Adapter.CheckPointAdapter;
import com.group3.kargobikeproject.Fragment.AddCheckPointDialog;
import com.group3.kargobikeproject.Model.Entity.CheckPoint;
import com.group3.kargobikeproject.Model.Entity.Type;
import com.group3.kargobikeproject.Model.Repository.CheckPointRepository;
import com.group3.kargobikeproject.Utils.GpsTracker;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
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
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderCheckpointActivity extends AppCompatActivity implements AddCheckPointDialog.OnInputListener {
    DatabaseReference ref;
    DatabaseReference refType;
    private ArrayList<CheckPoint> checkPoints = new ArrayList<CheckPoint>();
    private ArrayList<String> types = new ArrayList<String>();
    TextView textOrderCheckPointHistory;
    RecyclerView listCheckPoints;
    CheckPointRepository checkPointRepository;
    Button addCheckPoint, addSignature, openCamera;
    public String inputGot;
    public String typeGot;
    public String userEmail;
    String idOrderThis;
    private FirebaseUser firebaseUser = null;

    double lat ;
    double lon ;
    GpsTracker gt;
    Location l;
    // private ArrayList<String> checkPointList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_checkpoints);
        textOrderCheckPointHistory = findViewById(R.id.textOrderCheckPointHistory);
        textOrderCheckPointHistory.setText("Checkpoint History");
        idOrderThis = getIntent().getStringExtra("ORDER_ID");
        Toast.makeText(this, "idOrderThis", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, idOrderThis, Toast.LENGTH_SHORT).show();
        ref = FirebaseDatabase.getInstance().getReference().child("checkPoint");
        listCheckPoints = findViewById(R.id.listCheckPoints);
        ActivityCompat.requestPermissions(OrderCheckpointActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        gt = new GpsTracker(getApplicationContext());
        setUpButtons();
        checkPointRepository = new CheckPointRepository();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            userEmail = firebaseUser.getEmail();
        }
        refType = FirebaseDatabase.getInstance().getReference().child("type");


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ref != null) {
            Query newQuery = ref.orderByChild("idOrder").equalTo(idOrderThis);
            newQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        checkPoints = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

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
        if (refType != null) {
            refType.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        types = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            types.add(ds.getValue(Type.class).getName());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setUpButtons() {
        addCheckPoint = findViewById(R.id.buttonAddCheckpoint);
        addSignature = findViewById(R.id.buttonAddSignature);
        openCamera = findViewById(R.id.buttonOpenCamera);
        addCheckPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hey", "onClick: opening dialog");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("types", types);
                l = gt.getLocation();
                AddCheckPointDialog dialog = new AddCheckPointDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "AddCheckPointDialog");

            }
        });
        addSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignatureActivity.class);
                intent.putExtra("ORDER_ID", idOrderThis);
                startActivity(intent);
            }
        });
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CameraActivity.class);
                intent.putExtra("ORDER_ID", idOrderThis);
                startActivity(intent);
            }
        });
    }

    @Override
    public void sendInput(String input, String type) {
        Log.d("hey", "sendInput: got the input: " + input);
        inputGot = input;
        typeGot = type;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (userEmail != null) {
            CheckPoint newCP;
            if( l == null){
                newCP = new CheckPoint(getIntent().getStringExtra("ORDER_ID"), inputGot, userEmail, formatter.format(date), typeGot);
            }else {
                lat = l.getLatitude();
                lon = l.getLongitude();
                newCP = new CheckPoint(getIntent().getStringExtra("ORDER_ID"), inputGot, userEmail, formatter.format(date), typeGot, lat, lon);
            }
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
        } else {
            Toast.makeText(OrderCheckpointActivity.this, "please log in first",
                    Toast.LENGTH_SHORT).show();
        }
    }
}




