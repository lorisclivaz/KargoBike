package com.group3.kargobikeproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group3.kargobikeproject.Model.Entity.User;
import com.group3.kargobikeproject.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{


    ArrayList<User> users;
    Context mContext;

    private UserAdapter.onItemCLickListener mListener;

    public interface onItemCLickListener
    {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(UserAdapter.onItemCLickListener listener)
    {

        mListener = listener;
    }

    public UserAdapter(ArrayList<User> users)
    {
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user,parent,false);

        return new UserAdapter.MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder myViewHolder, int position) {

        myViewHolder.firstName.setText(users.get(position).getFirstName());
        myViewHolder.lastName.setText(users.get(position).getLastName());



    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView firstName, lastName;

        public MyViewHolder(@NonNull View itemView, UserAdapter.onItemCLickListener listener) {
            super(itemView);


            firstName = itemView.findViewById(R.id.UserFirstName);
            lastName = itemView.findViewById(R.id.UserLastName);

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
