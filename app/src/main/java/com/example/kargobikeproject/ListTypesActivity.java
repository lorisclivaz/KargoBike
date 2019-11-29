package com.example.kargobikeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kargobikeproject.Adapter.OrderAdapter;
import com.example.kargobikeproject.Adapter.TypeAdapter;
import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Entity.Type;
import com.example.kargobikeproject.Model.Repository.TypeRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListTypesActivity extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Type> types;
    TypeRepository typeRepository;
    Button buttonAddType;
    RecyclerView typeListView;


    private static final String TAG = "Type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_types);
        buttonAddType = findViewById(R.id.buttonAddType);
        typeListView = findViewById(R.id.listTypes);
        typeRepository = new TypeRepository();

        ref = FirebaseDatabase.getInstance().getReference().child("type");

        buttonAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type newType = new Type("sampleCheckPointType");

                typeRepository.insert(newType, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "type added");
                        Toast.makeText(ListTypesActivity.this, "new type added",
                                Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "type was not added", e);
                        Toast.makeText(ListTypesActivity.this, "new type was not added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(ref!=null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        types = new ArrayList<>();

                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {

                            types.add(ds.getValue(Type.class));
                        }

                        TypeAdapter adapterClass = new TypeAdapter(types);
                        typeListView.setAdapter(adapterClass);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
