package com.example.kargobikeproject.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kargobikeproject.R;

public class AddCheckPointDialog extends DialogFragment {
    private static final String TAG = "AddCheckPointDialog";
    private EditText input;
    private TextView actionOk, actionCancel;

    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputListener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_check_point, container, false);
        actionOk= view.findViewById((R.id.action_ok));
        actionCancel= view.findViewById(R.id.action_cancel);
        input = view.findViewById(R.id.input);


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
                String i = input.getText().toString();
                mOnInputListener.sendInput(i);
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
