package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClass> items;
String timeformat,day;
    public CustomAdapter(Context context, ArrayList<ModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemVenue.setText(items.get(position).getVenue());
        holder.itemCost.setText(Integer.toString(items.get(position).getCost()));
        FirebaseStorage stor=FirebaseStorage.getInstance();
        String idEvent=items.get(position).getId();
        holder.itemImage.setImageResource(items.get(position).getImage());
        stor.getReference().child(idEvent+"/"+idEvent+Integer.toString(1)+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Glide.with(context.getApplicationContext()).load(url).into(holder.itemImage);
            }
        });
        holder.itemInfo1.setImageDrawable(null);
        holder.itemInfo2.setImageDrawable(null);
        holder.itemInfo3.setImageDrawable(null);
        holder.itemInfo4.setImageDrawable(null);
        String hour = items.get(position).getTime().substring(0,2);
        final String minute = items.get(position).getTime().substring(3,5);
        ArrayList<String> info = new ArrayList<>();
        Drawable adult = ResourcesCompat.getDrawable(context.getResources(), R.drawable.adultlef, null);
        Drawable drinks = ResourcesCompat.getDrawable(context.getResources(), R.drawable.alcohollef, null);
        Drawable music = ResourcesCompat.getDrawable(context.getResources(), R.drawable.musiclef, null);
        Drawable food = ResourcesCompat.getDrawable(context.getResources(), R.drawable.foodlef, null);
        if (items.get(position).getAdult().equals("Yes")) {
            info.add("Adult");
        }
        if (items.get(position).getDrinks().equals("Yes")) {
            info.add("Drinks");
        }
        if (items.get(position).getMusic().equals("Yes")) {
            info.add("Music");
        }
        if (items.get(position).getFood().equals("Yes")) {
            info.add("Food");
        }
        for (int k = 0; k < info.size(); k++) {
            if (info.get(k).equals("Adult")) {
                if (k == 0) {
                    holder.itemInfo1.setImageDrawable(adult);
                } else if (k == 1) {
                    holder.itemInfo2.setImageDrawable(adult);
                } else if (k == 2) {
                    holder.itemInfo3.setImageDrawable(adult);
                } else if (k == 3) {
                    holder.itemInfo4.setImageDrawable(adult);
                }
            } else if (info.get(k).equals("Drinks")) {
                if (k == 0) {
                    holder.itemInfo1.setImageDrawable(drinks);
                } else if (k == 1) {
                    holder.itemInfo2.setImageDrawable(drinks);
                } else if (k == 2) {
                    holder.itemInfo3.setImageDrawable(drinks);
                } else if (k == 3) {
                    holder.itemInfo4.setImageDrawable(drinks);
                }
            } else if (info.get(k).equals("Music")) {
                if (k == 0) {
                    holder.itemInfo1.setImageDrawable(music);
                } else if (k == 1) {
                    holder.itemInfo2.setImageDrawable(music);
                } else if (k == 2) {
                    holder.itemInfo3.setImageDrawable(music);
                } else if (k == 3) {
                    holder.itemInfo4.setImageDrawable(music);
                }
            } else if (info.get(k).equals("Food")) {
                if (k == 0) {
                    holder.itemInfo1.setImageDrawable(food);
                } else if (k == 1) {
                    holder.itemInfo2.setImageDrawable(food);
                } else if (k == 2) {
                    holder.itemInfo3.setImageDrawable(food);
                } else if (k == 3) {
                    holder.itemInfo4.setImageDrawable(food);
                }
            }
        }
        String setampm;
        setampm = new String();
        int ampm = Integer.parseInt(hour);
        if (ampm > 12) {
            setampm = "pm";
            ampm = ampm - 12;
        } else if (ampm < 12) {
            setampm = "am";

        }
        holder.itemDate.setText(items.get(position).getDate());
        holder.itemTime.setText(Integer.toString(ampm) + ":" + minute + setampm);
        timeformat = Integer.toString(ampm) + ":" + minute + setampm;
        final String finalSetampm = setampm;
        final int finalAmpm = ampm;
        if (!items.get(position).getId().equals("")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventDisplay.class);
                    intent.putExtra("position", position);
                    intent.putExtra("section", 1);
                    intent.putExtra("costing", Integer.toString(items.get(position).getCost()));
                    intent.putExtra("date", items.get(position).getDate());
                    intent.putExtra("intro", items.get(position).getIntro());
                    intent.putExtra("title", items.get(position).getTitle());
                    intent.putExtra("ID", items.get(position).getId());
                    //Toast.makeText(context, items.get(position).getDate(), Toast.LENGTH_SHORT).show();
                    String year = items.get(position).getDate().substring(6, 10);
                    String month = (items.get(position).getDate().substring(3, 5));
                    String date = (items.get(position).getDate().substring(0, 2));
                    day = new String();
                    //Toast.makeText(context, Integer.parseInt(date)+"/"+Integer.parseInt(month)+"/"+Integer.parseInt(year), Toast.LENGTH_SHORT).show();
                    Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date)); // Note that Month value is 0-based. e.g., 0 for January.
                    int result = calendar.get(Calendar.DAY_OF_WEEK);
                    switch (result) {
                        case Calendar.MONDAY:
                            day = "Monday";
                            break;
                        case Calendar.TUESDAY:
                            day = "Tuesday";
                            break;
                        case Calendar.WEDNESDAY:
                            day = "Wednesday";
                            break;
                        case Calendar.THURSDAY:
                            day = "Thursday";
                            break;
                        case Calendar.FRIDAY:
                            day = "Friday";
                            break;
                        case Calendar.SATURDAY:
                            day = "Saturday";
                            break;
                        case Calendar.SUNDAY:
                            day = "Sunday";
                            break;
                    }
                    intent.putExtra("time", Integer.toString(finalAmpm) + ":" + minute + finalSetampm);
                    intent.putExtra("day", day);
                    intent.putExtra("drinks", items.get(position).getDrinks());
                    intent.putExtra("adult", items.get(position).getAdult());
                    intent.putExtra("music", items.get(position).getMusic());
                    intent.putExtra("food", items.get(position).getFood());
                    intent.putExtra("venue", items.get(position).getVenue());
                    context.startActivity(intent);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage,itemInfo1,itemInfo2,itemInfo3,itemInfo4;
        private TextView itemTitle;
        private TextView itemVenue;
        private TextView itemCost,itemDate,itemTime;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_images);
            itemInfo1 = view.findViewById(R.id.item_info1);
            itemInfo2 = view.findViewById(R.id.item_info2);
            itemInfo4 = view.findViewById(R.id.item_info4);
            itemInfo3 = view.findViewById(R.id.item_info3);
            itemTitle = view.findViewById(R.id.item_title);
            itemVenue = view.findViewById(R.id.item_venue);
            itemCost = view.findViewById(R.id.item_cost);
            itemDate = view.findViewById(R.id.item_date);
            itemTime = view.findViewById(R.id.item_time);
        }
    }
}