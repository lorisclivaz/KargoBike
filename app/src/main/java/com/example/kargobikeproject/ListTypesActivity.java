package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Model.Entity.Type;
import com.example.kargobikeproject.Model.Repository.TypeRepository;
import com.example.kargobikeproject.Utils.OnAsyncEventListener;

public class ListTypesActivity extends AppCompatActivity {
    TypeRepository typeRepository;
    Button buttonAddType;
    ListView typeListView;
    private static final String TAG = "Type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_types);
        buttonAddType = findViewById(R.id.buttonAddType);
        typeListView = findViewById(R.id.listTypes);
        typeRepository = new TypeRepository();
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
}
