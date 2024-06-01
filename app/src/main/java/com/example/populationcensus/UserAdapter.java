package com.example.populationcensus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> userArrayList, fullList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
        this.fullList = new ArrayList<>(userArrayList);
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new UserViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = userArrayList.get(position);
        holder.tvFirstname.setText(currentUser.getFirstname());
        holder.tvLastname.setText(currentUser.getLastname());
        holder.tvCity.setText(currentUser.getCity());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    public void updateData(ArrayList<User> newData) {
        fullList.clear();
        fullList.addAll(newData);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        userArrayList.clear();
        if (text.isEmpty()) {
            userArrayList.addAll(fullList);
        } else {
            String query = text.toLowerCase().trim();
            for (User user : fullList) {
                if (user.getFirstname().toLowerCase().contains(query) || user.getLastname().toLowerCase().contains(query)
                 || user.getCity().toLowerCase().contains(query)) {
                    userArrayList.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFirstname, tvLastname, tvCity;

        public UserViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvFirstname = itemView.findViewById(R.id.tvUserFirstname);
            tvLastname = itemView.findViewById(R.id.tvUserLastname);
            tvCity = itemView.findViewById(R.id.tvUserCity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
