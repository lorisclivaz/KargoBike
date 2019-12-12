package com.group3.kargobikeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    Button cancelButtonCamera, saveButtonCamera,openCameraButton;
    LinearLayout mContent;
    Bitmap tempBitmap;
    String idOrderThis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        idOrderThis = getIntent().getStringExtra("ORDER_ID");

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

            byte[] data = outputStream.toByteArray();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://kargobikegroup3.appspot.com");
            StorageReference pathReference = storageRef.child("camera/"+idOrderThis);

            UploadTask uploadTask = pathReference.putBytes(data);
            uploadTask.addOnFailureListener(CameraActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CameraActivity.this, "Upload Error: " +
                            e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(CameraActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                   // while(!uri.isComplete());
                    Uri url = uri.getResult();
                    Log.v("image url", url.toString());
                }
            });
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
