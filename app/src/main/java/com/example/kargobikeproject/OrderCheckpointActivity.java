package com.example.kargobikeproject;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderCheckpointActivity extends AppCompatActivity {
    ListView listCheckPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_checkpoints);
        listCheckPoints = (ListView) findViewById(R.id.listCheckpoints);
        ArrayList<String> checkPointList = new ArrayList<String>();
        checkPointList.add("Sierre");
        checkPointList.add("Sion");
        checkPointList.add("Brig");
        checkPointList.add("Sierre1");
        checkPointList.add("Sion1");
        checkPointList.add("Brig1");
        checkPointList.add("Sierre2");
        checkPointList.add("Sion2");
        checkPointList.add("Brig2");
        checkPointList.add("Sierre3");
        checkPointList.add("Sion3");
        checkPointList.add("Brig3");
        checkPointList.add("Sierre4");
        checkPointList.add("Sion4");
        checkPointList.add("Brig4");
        checkPointList.add("Sierre5");
        checkPointList.add("Sion5");
        checkPointList.add("Brig5");

        ArrayAdapter checkPointAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, checkPointList);
        listCheckPoints.setAdapter(checkPointAdapter);
    }
}
