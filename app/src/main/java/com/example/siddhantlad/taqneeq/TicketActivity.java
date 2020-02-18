package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class TicketActivity extends AppCompatActivity {
    String name;
    String id,enter,venue,date,time;
    ImageView imageEvent;
    TextView dateTV,entersTV,timeTV,venueTV,idTV;
    String timeformat,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Intent mIntent=getIntent();
        Window window = TicketActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(TicketActivity.this,R.color.colorBlue));
        timeTV=findViewById(R.id.time);
        venueTV=findViewById(R.id.venue);
        imageEvent=findViewById(R.id.imageEvent);
        name=mIntent.getStringExtra("Name");
        id=mIntent.getStringExtra("ID");
        date=mIntent.getStringExtra("Date");
        venue=mIntent.getStringExtra("Venue");
        time=mIntent.getStringExtra("Time");
        enter=mIntent.getStringExtra("Enters");
        String setampm;
        setampm = new String();
        String hour = time.substring(0,2);
        String minute = time.substring(3,5);
        int ampm = Integer.parseInt(hour);
        if (ampm > 12) {
            setampm = "pm";
            ampm = ampm - 12;
        } else if (ampm < 12) {
            setampm = "am";

        }
        timeformat = Integer.toString(ampm) + ":" + minute + setampm;
        final String finalSetampm = setampm;
        final int finalAmpm = ampm;
        TextView names=findViewById(R.id.eventName);
        dateTV=findViewById(R.id.date);
        entersTV=findViewById(R.id.enters);
        names.setText(name);
        entersTV.setText(enter);
        dateTV.setText(date);
        timeTV.setText(Integer.toString(finalAmpm) + ":" + minute + finalSetampm);
        venueTV.setText(venue);
        FirebaseStorage stor= FirebaseStorage.getInstance();
        stor.getReference().child(id+"/"+id+Integer.toString(1)+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Glide.with(TicketActivity.this).load(url).into(imageEvent);
            }
        });
    }
}
