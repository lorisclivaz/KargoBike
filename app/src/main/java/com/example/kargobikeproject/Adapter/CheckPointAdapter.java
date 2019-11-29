package com.example.kargobikeproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kargobikeproject.Model.Entity.CheckPoint;
import com.example.kargobikeproject.R;

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

    }

    @Override
    public int getItemCount() {
        return checkPoints.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView checkPointName, nameRider, timeStamp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkPointName = itemView.findViewById(R.id.checkpoint_name);
            nameRider = itemView.findViewById(R.id.checkpoint_rider);
            timeStamp = itemView.findViewById(R.id.checkpoint_timeStamp);

        }
    }
}