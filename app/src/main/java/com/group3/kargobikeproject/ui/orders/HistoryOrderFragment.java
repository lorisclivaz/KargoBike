package com.group3.kargobikeproject.ui.orders;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.group3.kargobikeproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryOrderFragment extends Fragment {

    private Button bt_showHistory;
    private String time;
    private DatePickerDialog.OnDateSetListener dateListener;
    private TextView tv_historyOrdeDate;
    private Date date;

    public static HistoryOrderFragment newInstance() {
        return new HistoryOrderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.history_order_fragment, container, false);
        bt_showHistory = (Button) rootView.findViewById(R.id.btn_seeHistory);
        tv_historyOrdeDate = (TextView) rootView.findViewById(R.id.tv_historyOrdeDate);
        bt_showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("time",time);
                Fragment fragment = new OrderFragment();
                fragment.setArguments(data);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.linearLayout, fragment)
                        .commit();
            }
        });


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        date = new Date();
        time = formatter.format(date);


        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateString =dayOfMonth+"."+(month+1)+"."+year;
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                time = formatter.format(date);
                tv_historyOrdeDate.setText(time);
            }
        };
        tv_historyOrdeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year, month,day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
