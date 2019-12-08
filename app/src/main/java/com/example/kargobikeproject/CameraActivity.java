package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {
    Button cancelButtonCamera, saveButtonCamera,openCameraButton;
    LinearLayout mContent;
    Bitmap tempBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cancelButtonCamera = findViewById(R.id.cancelButtonCamera);
        saveButtonCamera = findViewById(R.id.saveButtonCamera);
        saveButtonCamera.setEnabled(false);
        openCameraButton = findViewById(R.id.openButtonCamera);
        mContent = findViewById(R.id.canvasLayoutCamera);
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
        saveButtonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveAndUpload(tempBitmap);
            }
        });
        cancelButtonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1  && resultCode == RESULT_OK) {
            saveButtonCamera.setEnabled(true);
            Bitmap image = (Bitmap) data.getExtras().get("data");
            tempBitmap = image;
            BitmapDrawable bdrawable = new BitmapDrawable(this.getResources(),image);
            mContent.setBackground(bdrawable);
        }
    }
    public void saveAndUpload(Bitmap imageToUpload) {
        try {
            // Output the file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageToUpload.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            String imageString =  Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

            String id = FirebaseDatabase.getInstance().getReference("image").push().getKey();
            FirebaseDatabase.getInstance()
                    .getReference("image")
                    .child(id)
                    .setValue(imageString, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            Toast.makeText(CameraActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CameraActivity.this, "Upload OK", Toast.LENGTH_SHORT).show();
                        }
                    });
            Log.v("log_tag", imageString + "heyCamera");
            Intent intent = new Intent(CameraActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            Log.v("log_tag", e.toString());
        }

    }

}
