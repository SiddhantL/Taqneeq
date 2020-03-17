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
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.itemTitle.setText(items.get(position).getTitle());
        holder.winner1.setText(items.get(position).getWinner1());
        holder.winner2.setText(items.get(position).getWinner2());
        holder.winner3.setText(items.get(position).getWinner3());
        holder.itemRoom.setText(items.get(position).getClassroom());
        holder.itemScore.setText(Integer.toString(items.get(position).getScore()));
        for (int i=0;i<MyData.nameArray.length;i++){
            if(MyData.nameArray[i].equals(items.get(position).getTitle())){
                holder.itemImage.setImageResource(MyData.drawableArray[i]);
            }
        }
        for (int i=0;i<MyData.informalArray.length;i++){
            if(MyData.informalArray[i].equals(items.get(position).getTitle())){
                holder.itemImage.setImageResource(MyData.informaldrawableArray[i]);
            }
        }
        for (int i=0;i<MyData.workshopArray.length;i++){
            if(MyData.workshopArray[i].equals(items.get(position).getTitle())){
                holder.itemImage.setImageResource(MyData.workshopdrawableArray[i]);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int section=0,positions=0;
                for (int i=0;i<MyData.nameArray.length;i++){
                    if(MyData.nameArray[i].equals(items.get(position).getTitle())){
                        section=1;
                        positions=i;
                    }
                }
                for (int i=0;i<MyData.informalArray.length;i++){
                    if(MyData.informalArray[i].equals(items.get(position).getTitle())){
                        section=2;
                        positions=i;
                    }
                }
                for (int i=0;i<MyData.workshopArray.length;i++){
                    if(MyData.workshopArray[i].equals(items.get(position).getTitle())){
                        section=3;
                        positions=i;
                    }
                }
                Intent newIntent=new Intent(context,EventDisplay.class);
                newIntent.putExtra("position",positions);
                newIntent.putExtra("section",section);
                context.startActivity(newIntent);
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
            itemImage = view.findViewById(R.id.item_images);
            itemTitle = view.findViewById(R.id.item_title);
            itemRoom = view.findViewById(R.id.item_date);
            itemScore = view.findViewById(R.id.item_cost);
           winner1 = view.findViewById(R.id.textView6);
            winner2 = view.findViewById(R.id.textView10);
            winner3 = view.findViewById(R.id.textView2);
        }
    }
}