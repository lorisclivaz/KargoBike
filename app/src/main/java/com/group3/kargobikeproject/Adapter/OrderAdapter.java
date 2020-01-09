package com.group3.kargobikeproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group3.kargobikeproject.Model.Entity.Order;
import com.group3.kargobikeproject.OrderCheckpointActivity;
import com.group3.kargobikeproject.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    ArrayList<Order> orders;
    Context mContext;

    private onItemCLickListener mListener;

    public interface onItemCLickListener
    {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(onItemCLickListener listener)
    {

        mListener = listener;
    }

    public OrderAdapter(ArrayList<Order> orders)
    {
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order,parent,false);

        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.nameClient.setText(orders.get(position).getNameClient());
        myViewHolder.startDate.setText(orders.get(position).getDeliverStart());
        myViewHolder.endDate.setText(orders.get(position).getDeliverEnd());
        myViewHolder.checkpointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                //if you need position, just use recycleViewHolder.getAdapterPosition();
                Intent intent = new Intent(v.getContext(), OrderCheckpointActivity.class);
                intent.putExtra("ORDER_CLIENT", orders.get(position).getNameClient());
                intent.putExtra("ORDER_ID", orders.get(position).getIdOrder());


                v.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return orders.size();
    }

  public   class MyViewHolder extends RecyclerView.ViewHolder
    {
        Button checkpointButton;
        TextView nameClient,startDate, endDate;

        public MyViewHolder(@NonNull View itemView, onItemCLickListener listener) {
            super(itemView);

            nameClient = itemView.findViewById(R.id.nameClient);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            checkpointButton = itemView.findViewById(R.id.buttonViewCheckPoint);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(listener != null)
                    {
                        int position = getAdapterPosition();


                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);




                        }
                    }
                }
            });


        }
    }


}
