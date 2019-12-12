package com.group3.kargobikeproject.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.group3.kargobikeproject.Model.Entity.Type;
import com.group3.kargobikeproject.R;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {

    ArrayList<Type> types;

    public TypeAdapter(ArrayList<Type> types)
    {
        this.types = types;
    }

    @NonNull
    @Override
    public TypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type,parent,false);

        return new TypeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.MyViewHolder myViewHolder, int position) {
        Log.d("type", types.get(position).getName());
        Log.d("type", types.toString());
        myViewHolder.nameType.setText(types.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView nameType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameType = itemView.findViewById(R.id.nameType);
        }
    }
}
