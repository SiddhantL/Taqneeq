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

public class CustomWinnerAdapter extends RecyclerView.Adapter<CustomWinnerAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<WinnerModelClass> items;

    public CustomWinnerAdapter(Context context, ArrayList<WinnerModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.winneritems, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.itemTitle.setText(items.get(position).getTitle());
        holder.winner1.setText(items.get(position).getWinner1());
        holder.winner2.setText(items.get(position).getWinner2());
        holder.winner3.setText(items.get(position).getWinner3());
        holder.itemRoom.setText(items.get(position).getClassroom());
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
        private TextView itemRoom;
        private TextView itemScore;
        private TextView winner1;
        private TextView winner2;
        private TextView winner3;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_title);
            itemRoom = view.findViewById(R.id.item_room);
            itemScore = view.findViewById(R.id.item_score);
           winner1 = view.findViewById(R.id.textView6);
            winner2 = view.findViewById(R.id.textView10);
            winner3 = view.findViewById(R.id.textView2);
        }
    }
}