package com.example.kargobikeproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        TextView nameOrder = v.findViewById(R.id.NameOrder);
        TextView idRider = v.findViewById(R.id.IdRider);
        TextView idClient = v.findViewById(R.id.IdClient);

        nameOrder.setText(currentOrder.getNameOrder());
        idRider.setText(String.valueOf(currentOrder.getIdRider()));
        idClient.setText(String.valueOf(currentOrder.getIdClient()));


        return v;
    }
}
