package com.group3.kargobikeproject.ui.checkpoint;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group3.kargobikeproject.Adapter.TypeAdapter;
import com.group3.kargobikeproject.Model.Entity.Type;
import com.group3.kargobikeproject.Model.Repository.TypeRepository;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ListTypesFragment extends Fragment {
    DatabaseReference ref;
    ArrayList<Type> types;
    TypeRepository typeRepository;
    Button buttonAddType;
    EditText addTypeInput;
    RecyclerView typeListView;

    public static ListTypesFragment newInstance() {
        return new ListTypesFragment();
    }

    private static final String TAG = "Type";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_list_types, container, false);
        buttonAddType = rootView.findViewById(R.id.buttonAddType);
        addTypeInput = rootView.findViewById(R.id.addTypeInput);
        typeListView = rootView.findViewById(R.id.listTypes);
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
                            Toast.makeText(getActivity(), "new type added",
                                    Toast.LENGTH_SHORT).show();
                            addTypeInput.getText().clear();
                            addTypeInput.onEditorAction(EditorInfo.IME_ACTION_DONE);

                        }
                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "type was not added", e);
                            Toast.makeText(getActivity(), "new type was not added",
                                    Toast.LENGTH_SHORT).show();
                            addTypeInput.getText().clear();
                            addTypeInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "type already exists",
                            Toast.LENGTH_SHORT).show();
                    addTypeInput.getText().clear();
                    addTypeInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
            }
        });
        return rootView;
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
    public void onStart() {
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
