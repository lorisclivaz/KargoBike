package com.example.kargobikeproject.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kargobikeproject.Model.Entity.Type;
import com.example.kargobikeproject.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddCheckPointDialog extends DialogFragment {
    private static final String TAG = "AddCheckPointDialog";
    private EditText input;
    private TextView actionOk, actionCancel;
    public OnInputListener mOnInputListener;
    Spinner spinnerType;
    private List<String> listTypes;
    public interface OnInputListener{
        void sendInput(String input, String type);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_check_point, container, false);
        actionOk= view.findViewById((R.id.action_ok));
        actionCancel= view.findViewById(R.id.action_cancel);
        input = view.findViewById(R.id.input);
        listTypes = getArguments().getStringArrayList("types");
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapter);






        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });
        actionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString();
                String type = spinnerType.getSelectedItem().toString();
                mOnInputListener.sendInput(name,type);
                getDialog().dismiss();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}
