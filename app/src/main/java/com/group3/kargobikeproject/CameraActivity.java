package com.group3.kargobikeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group3.kargobikeproject.ui.orders.OrderFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    Button cancelButtonCamera, saveButtonCamera,openCameraButton;
    ImageView mContent;
    Bitmap tempBitmap;
    String idOrderThis;
    String newPicFile = idOrderThis + ".jpg";
    StorageReference storageRef;
    FirebaseStorage storage;
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
        storage = FirebaseStorage.getInstance();
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
                Intent intent = new Intent(CameraActivity.this, OrderCheckpointActivity.class);
                intent.putExtra("ORDER_ID",idOrderThis);
                startActivity(intent);
            }
        });
        retrieveImage();
    }
    public void retrieveImage() {
        storageRef = storage.getReferenceFromUrl("gs://kargobikegroup3.appspot.com");
        StorageReference pathReference = storageRef.child("camera/"+idOrderThis);
        final long ONE_MEGABYTE = 1024 * 1024;
        Task<Uri> downloadUrl = pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                openCameraButton.setEnabled(false);
                saveButtonCamera.setEnabled(false);
                new DownloadImageTask((ImageView) findViewById(R.id.canvasLayoutCamera))
                        .execute(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(),"No form Taken", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1  && resultCode == RESULT_OK) {
            saveButtonCamera.setEnabled(true);
            Bitmap image = (Bitmap) data.getExtras().get("data");
            tempBitmap = image;
            BitmapDrawable bdrawable = new BitmapDrawable(this.getResources(),image);
            mContent.setImageBitmap(image);
        }
    }
    public void saveAndUpload(Bitmap imageToUpload) {
        try {
            // Output the file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageToUpload.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            byte[] data = outputStream.toByteArray();

            storageRef = storage.getReferenceFromUrl("gs://kargobikegroup3.appspot.com");
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
            Intent intent = new Intent(CameraActivity.this, MenuFragementActivity.class);
            startActivity(intent);
            finish();
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            Log.v("log_tag", e.toString());
        }

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Log.d("hey", urldisplay);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            BitmapDrawable bdrawable = new BitmapDrawable(getApplicationContext().getResources(),result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                bmImage.setForeground(bdrawable);
            }
            bmImage.setBackground(bdrawable);
            bmImage.setImageBitmap(result);
        }
    }
}
