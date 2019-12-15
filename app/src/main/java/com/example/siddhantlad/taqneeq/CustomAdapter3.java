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

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClass> items;

    public CustomAdapter3(Context context, ArrayList<ModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemClass.setText(items.get(position).getVenue());
        holder.itemScore.setText(Integer.toString(MyData.scoreArray3[position]));
        holder.itemImage.setImageResource(items.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,EventDisplay.class);
                intent.putExtra("position",position);
                intent.putExtra("section",3);
                context.startActivity(intent);

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
        private TextView itemClass;
        private TextView itemScore;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_title);
            itemClass = view.findViewById(R.id.item_date);
            itemScore = view.findViewById(R.id.item_cost);
        }
    }
}