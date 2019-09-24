package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAlertAdapter extends RecyclerView.Adapter<CustomAlertAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<AlertModelClass> items;

    public CustomAlertAdapter(Context context, ArrayList<AlertModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.alertitems, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemClass.setText(items.get(position).getClassroom());
        holder.itemAlert.setText(items.get(position).getAlert());
        holder.itemScore.setText(Integer.toString(items.get(position).getScore()));
        holder.itemImage.setImageResource(items.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,EventDisplay.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemAlert;
        private TextView itemClass;
        private TextView itemScore;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_title);
            itemAlert = view.findViewById(R.id.item_alert);
            itemClass = view.findViewById(R.id.item_room);
            itemScore = view.findViewById(R.id.item_score);
        }
    }
}