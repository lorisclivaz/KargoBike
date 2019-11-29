package com.example.kargobikeproject.Adapter;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kargobikeproject.Model.Entity.Type;
import com.example.kargobikeproject.R;
import com.example.kargobikeproject.Utils.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
