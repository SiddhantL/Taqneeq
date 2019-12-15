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
        for (int i=0;i<MyData.nameArray.length;i++){
            if(MyData.nameArray[i].equals(items.get(position).getTitle())){
                holder.itemImage.setImageResource(MyData.drawableArray[i]);
                holder.itemScore.setText(Integer.toString(MyData.scoreArray[i]));
                holder.itemClass.setText(MyData.roomArray[i]);
            }
        }
        for (int i=0;i<MyData.informalArray.length;i++){
            if(MyData.informalArray[i].equals(items.get(position).getTitle())){
                holder.itemImage.setImageResource(MyData.informaldrawableArray[i]);
                holder.itemScore.setText(Integer.toString(MyData.scoreArray2[i]));
                holder.itemClass.setText(MyData.roomArray2[i]);
            }
        }
        for (int i=0;i<MyData.workshopArray.length;i++){
            if(MyData.workshopArray[i].equals(items.get(position).getTitle())){
                holder.itemImage.setImageResource(MyData.workshopdrawableArray[i]);
                holder.itemScore.setText(Integer.toString(MyData.scoreArray3[i]));
                holder.itemClass.setText(MyData.roomArray3[i]);
            }
        }
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
            itemClass = view.findViewById(R.id.item_date);
            itemScore = view.findViewById(R.id.item_cost);
        }
    }
}