package com.example.kargobikeproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kargobikeproject.Model.Entity.Order;
import com.example.kargobikeproject.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders)
    {
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.nameClient.setText(orders.get(position).getNameClient());
        myViewHolder.nameRoute.setText(orders.get(position).getNameRoute());
        myViewHolder.nameRider.setText(orders.get(position).getNameRider());
        myViewHolder.address.setText(orders.get(position).getAddress());
        myViewHolder.startDate.setText(orders.get(position).getDeliverStart());
        myViewHolder.endDate.setText(orders.get(position).getDeliverEnd());
        myViewHolder.status.setText(orders.get(position).getOrderStatus());


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView nameClient, nameRoute, nameRider, address, startDate, endDate, status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameClient = itemView.findViewById(R.id.nameClient);
            nameRoute = itemView.findViewById(R.id.nameRoute);
            nameRider = itemView.findViewById(R.id.nameRider);
            address = itemView.findViewById(R.id.Address);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            status = itemView.findViewById(R.id.status);

        }
    }
}
