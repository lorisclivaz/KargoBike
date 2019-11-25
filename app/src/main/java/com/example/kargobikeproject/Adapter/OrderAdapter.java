package com.example.kargobikeproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kargobikeproject.Order;
import com.example.kargobikeproject.R;


public class OrderAdapter extends ArrayAdapter<Order> {


    public OrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(R.layout.order_item, null);

        Order currentOrder = getItem(position);

        TextView idOrder = v.findViewById(R.id.IdOrder);
        TextView idClient = v.findViewById(R.id.IdClient);
        TextView idRider = v.findViewById(R.id.IdRider);
        TextView idRoute = v.findViewById(R.id.IdRoute);
        TextView address = v.findViewById(R.id.Address);
        TextView deliverStart = v.findViewById(R.id.DeliverStart);
        TextView deliverEnd = v.findViewById(R.id.deliverEnd);
        TextView orderStatus = v.findViewById(R.id.OrderStatus);

        idOrder.setText(String.valueOf(currentOrder.getIdOrder()));
        idRider.setText(String.valueOf(currentOrder.getIdRider()));
        idClient.setText(String.valueOf(currentOrder.getIdClient()));
        idRoute.setText(String.valueOf(currentOrder.getIdRoute()));
        address.setText(currentOrder.getAddress());
        deliverStart.setText(currentOrder.getDeliverStart());
        deliverEnd.setText(currentOrder.getDeliverEnd());
        orderStatus.setText(String.valueOf(currentOrder.getOrderStatus()));


        return v;
    }
}
