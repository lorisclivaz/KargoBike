package com.group3.kargobikeproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group3.kargobikeproject.Model.Entity.CheckPoint;
import com.group3.kargobikeproject.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckPointAdapter extends RecyclerView.Adapter<CheckPointAdapter.MyViewHolder> {

    ArrayList<CheckPoint> checkPoints;
    Context mContext;
    public CheckPointAdapter(ArrayList<CheckPoint> checkPoints)
    {
        this.checkPoints = checkPoints;
    }

    @NonNull
    @Override
    public CheckPointAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_checkpoint,parent,false);

        return new CheckPointAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckPointAdapter.MyViewHolder myViewHolder, int position) {

        myViewHolder.checkPointName.setText(checkPoints.get(position).getCheckPointName());
        myViewHolder.nameRider.setText(checkPoints.get(position).getNameRider());
        myViewHolder.timeStamp.setText(checkPoints.get(position).getTimeStamp());
        myViewHolder.type.setText(checkPoints.get(position).getTypeName());
        double latNum = checkPoints.get(position).getLatitude();
        double lonNum = checkPoints.get(position).getLongtitude();
        DecimalFormat df = new DecimalFormat("#.#######");
        myViewHolder.lat.setText("Lat: " + df.format(latNum));
        myViewHolder.lon.setText("Long: " + df.format(lonNum));

    }

    @Override
    public int getItemCount() {
        return checkPoints.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView checkPointName, nameRider, timeStamp, type,lat,lon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkPointName = itemView.findViewById(R.id.checkpoint_name);
            nameRider = itemView.findViewById(R.id.checkpoint_rider);
            timeStamp = itemView.findViewById(R.id.checkpoint_timeStamp);
            type = itemView.findViewById(R.id.checkpoint_type);
            lat = itemView.findViewById(R.id.checkpoint_lat);
            lon = itemView.findViewById(R.id.checkpoint_lon);

        }
    }
}