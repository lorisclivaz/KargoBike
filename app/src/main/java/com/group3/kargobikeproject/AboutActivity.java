package com.group3.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AboutActivity extends AppCompatActivity {


    ImageButton call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        call = findViewById(R.id.buttonCall);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                System.out.println("phoneeeeeeeeeeeee");
                phoneIntent.setData(Uri.parse("tel:91-000-000-0000"));

                if (ContextCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AboutActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(phoneIntent);
                }
            }
        });


    }
}
