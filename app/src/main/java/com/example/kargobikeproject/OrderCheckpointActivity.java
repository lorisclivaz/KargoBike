package com.example.kargobikeproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.kargobikeproject.Fragment.AddCheckPointDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderCheckpointActivity extends AppCompatActivity implements AddCheckPointDialog.OnInputListener{
    ListView listCheckPoints;
    Button addCheckPoint;
    public String inputGot;
    private ArrayList<String> checkPointList = new ArrayList<String>();
    @Override
    public void sendInput(String input) {
        Log.d("hey", "sendInput: got the input: " + input);
        inputGot = input;
        updateList(inputGot);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_checkpoints);
        setUpCheckPointList();
        setUpAddCheckPointButton();

    }
    private void setUpCheckPointList() {
        listCheckPoints = (ListView) findViewById(R.id.listCheckpoints);
        ArrayAdapter checkPointAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, checkPointList);
        listCheckPoints.setAdapter(checkPointAdapter);
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
    private void updateList(String newItem) {
        checkPointList.add(newItem);
        ArrayAdapter checkPointAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, checkPointList);
        listCheckPoints.setAdapter(checkPointAdapter);

    }
}





