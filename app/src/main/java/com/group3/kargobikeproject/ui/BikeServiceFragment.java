package com.group3.kargobikeproject.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.group3.kargobikeproject.Adapter.BikeServiceAdapter;
import com.group3.kargobikeproject.Model.Entity.BikeService;
import com.group3.kargobikeproject.ModifyAndDeleteOrderActivity;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.ViewModel.BikeServiceListViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BikeServiceFragment extends Fragment {


    private List<BikeService> serviceList;
    private BikeServiceListViewModel viewModel;
    private BikeServiceListViewModel viewModelListener;
    private TextView tv_date;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Date deliverEndDate;

    public static BikeServiceFragment newInstance() {
        return new BikeServiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.bikeservice_fragment, container, false);
        tv_date = (TextView) rootView.findViewById(R.id.tv_titleBikeService);
        //get today
        Date dateToday = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String thisDay = formatter.format(dateToday);

        //for the list view
        RecyclerView recyclerView = rootView.findViewById(R.id.listBikeServiceRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        serviceList=new ArrayList<BikeService>();

        final BikeServiceAdapter adapter = new BikeServiceAdapter(serviceList, getActivity().getApplication(),this);

        recyclerView.setAdapter(adapter);

        BikeServiceListViewModel.Factory factory = new BikeServiceListViewModel.Factory(getActivity().getApplication(),thisDay);
        viewModel = ViewModelProviders.of(this, factory).get(BikeServiceListViewModel.class);
        viewModel.getBikeServices().observe(this, serviceEntities -> {
            if (serviceEntities != null) {
                adapter.setBikeServices(serviceEntities, (String)tv_date.getText());
            }
        });
        //put today in the field and setup the field
        tv_date.setText(thisDay);
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String dateString =dayOfMonth+"."+(month+1)+"."+year;
                try {
                    deliverEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String time = formatter.format(deliverEndDate);
                tv_date.setText(time);

                //get data for that date
                viewModel.getBikeServices().observe(getActivity(), serviceEntities -> {
                    if (serviceEntities != null) {
                        adapter.setBikeServices(serviceEntities, (String)tv_date.getText());
                    }
                });
            }
        };
        tv_date.setOnClickListener(new View.OnClickListener() {
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
