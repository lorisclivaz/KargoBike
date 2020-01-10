package com.group3.kargobikeproject.Adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group3.kargobikeproject.Model.Entity.BikeService;
import com.group3.kargobikeproject.Model.Entity.CheckPoint;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.ViewModel.UserListViewModel;
import com.group3.kargobikeproject.ViewModel.UserViewModel;
import com.group3.kargobikeproject.ui.BikeServiceFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class BikeServiceAdapter extends RecyclerView.Adapter<BikeServiceAdapter.MyViewHolder> {

    List<BikeService> bikeServices;
    List<String> usernames;
    Context mContext;
    Application application;
    BikeServiceFragment fragment;
    public BikeServiceAdapter(List<BikeService> bikeServices, Application application,BikeServiceFragment fragment)
    {
        this.bikeServices = bikeServices;
        this.application = application;
        this.fragment=fragment;
        this.usernames=new ArrayList<String>();
    }

    @NonNull
    @Override
    public BikeServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bikeservice,parent,false);

        return new BikeServiceAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeServiceAdapter.MyViewHolder myViewHolder, int position) {

        myViewHolder.nameRider.setText(bikeServices.get(position).getIdRider());
        myViewHolder.timeStamp.setText(bikeServices.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return bikeServices.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameRider, timeStamp, type;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameRider = itemView.findViewById(R.id.tv_rider);
            timeStamp = itemView.findViewById(R.id.tv_timestamp);
        }
    }

    public void setBikeServices (List<BikeService> bikeServices, String timestamp) {
        UserListViewModel.Factory factoryUser = new UserListViewModel.Factory(application);
        UserListViewModel viewModelUser = ViewModelProviders.of(fragment, factoryUser).get(UserListViewModel.class);
        //set services to null
        this.bikeServices.clear();
        //fill services again
        for (BikeService service: bikeServices
        ) {
            //get services for that day
            if (service.getCreatedAt().contains(timestamp)){
                //the id is already converted to the name
                if (usernames.contains(service.getIdRider())){
                    this.bikeServices.add(service);
                    notifyDataSetChanged();
                } else{
                    // get the user and change the id to the name
                    viewModelUser.getUser(service.getIdRider()).observe(fragment, userEntity -> {
                        if (userEntity != null) {
                            //save name in the IdField, just to show in the view
                            service.setIdRider(userEntity.getFirstName()+" "+userEntity.getLastName());
                            //list to see after if it is already converted
                            usernames.add(userEntity.getFirstName()+" "+userEntity.getLastName());
                            this.bikeServices.add(service);
                            notifyDataSetChanged();
                        }
                    });
                }


            }

        }
        notifyDataSetChanged();
    }
}