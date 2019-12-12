package com.group3.kargobikeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group3.kargobikeproject.Adapter.TypeAdapter;
import com.group3.kargobikeproject.Model.Entity.Type;
import com.group3.kargobikeproject.Model.Repository.TypeRepository;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
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
    EditText addTypeInput;
    RecyclerView typeListView;


    private static final String TAG = "Type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_types);
        buttonAddType = findViewById(R.id.buttonAddType);
        addTypeInput = findViewById(R.id.addTypeInput);
        typeListView = findViewById(R.id.listTypes);
        typeRepository = new TypeRepository();

        ref = FirebaseDatabase.getInstance().getReference().child("type");

        buttonAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputGot = addTypeInput.getText().toString();
                if(!inputGot.isEmpty() && !checkTypeExists(inputGot, types)) {
                    Type newType = new Type(inputGot);
                    typeRepository.insert(newType, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "type added");
                            Toast.makeText(ListTypesActivity.this, "new type added",
                                    Toast.LENGTH_SHORT).show();
                            addTypeInput.getText().clear();
                            addTypeInput.onEditorAction(EditorInfo.IME_ACTION_DONE);

                        }
                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "type was not added", e);
                            Toast.makeText(ListTypesActivity.this, "new type was not added",
                                    Toast.LENGTH_SHORT).show();
                            addTypeInput.getText().clear();
                            addTypeInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        }
                    });
                }
                else {
                    Toast.makeText(ListTypesActivity.this, "type already exists",
                            Toast.LENGTH_SHORT).show();
                    addTypeInput.getText().clear();
                    addTypeInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
            }
        });
    }
    private boolean checkTypeExists(String newType, ArrayList<Type> types) {
        boolean result = false;
        for (Type n : types) {
            if(newType.toLowerCase().equals(n.getName().toLowerCase())) {
                result = true;
            }
        }
        return result;
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
