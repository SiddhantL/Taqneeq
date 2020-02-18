package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LinkEventDisplay extends AppCompatActivity implements OnMapReadyCallback {
    private Button buttonScan;
    String addr="";
    double lat,lon;
    DatabaseReference eventData;
    ImageView pic;
    TextView content,title,cost,date,time,day,drinks,adults,musics,foods,venue;
    //qr code scanner object
    String adultstr,costingstr,datestr,drinkstr,foodstr,introstr,musicstr,namestr,timestr,venuestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_event_display);
        Window window = LinkEventDisplay.this.getWindow();
        ViewPager viewPager = findViewById(R.id.viewpager);
        eventData= FirebaseDatabase.getInstance().getReference("exhibitions");
        Intent mIntent = getIntent();
        final String ID=mIntent.getStringExtra("UID");

        ImageAdapter adapter = new ImageAdapter(this,ID);
        viewPager.setAdapter(adapter);
        content=findViewById(R.id.textView);
        pic=findViewById(R.id.eventpic);
        title=findViewById(R.id.textView12);
        cost=findViewById(R.id.t1);
        date=findViewById(R.id.t2);
        time=findViewById(R.id.t3);
        day=findViewById(R.id.t4);
        adults=findViewById(R.id.c1);
        drinks=findViewById(R.id.c2);
        venue=findViewById(R.id.textView20);
        foods=findViewById(R.id.c3);
        musics=findViewById(R.id.c4);
        int position = mIntent.getIntExtra("position", 0);
        eventData.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot pd : dataSnapshot.getChildren()) {
                    if (pd.getKey().toString().equals("Adult")) {
                        adultstr=pd.getValue().toString();
                        adults.setText(" Adult: "+adultstr);
                    }else if (pd.getKey().toString().equals("Date")) {
                        datestr=pd.getValue().toString();
                        date.setText("Date: "+datestr);
                    }else if (pd.getKey().toString().equals("Drinks")) {
                        drinkstr=pd.getValue().toString();
                        drinks.setText(" Drinks: "+drinkstr);
                    }else if (pd.getKey().toString().equals("Food")) {
                        foodstr=pd.getValue().toString();
                        foods.setText(" Food: "+foodstr);
                    }else if (pd.getKey().toString().equals("Intro")) {
                        introstr=pd.getValue().toString();
                        content.setText(introstr);
                    }else if (pd.getKey().toString().equals("Music")) {
                        musicstr=pd.getValue().toString();
                        musics.setText(" Music: "+musicstr);
                    }else if (pd.getKey().toString().equals("Name")) {
                        namestr=pd.getValue().toString();
                        title.setText(namestr);
                    }else if (pd.getKey().toString().equals("Time")) {
                        timestr=pd.getValue().toString();
                        time.setText("Time: "+timestr);
                    }else if (pd.getKey().toString().equals("Venue")) {
                        venuestr=pd.getValue().toString();
                        venue.setText("Venue: "+venuestr);
                    }else if (pd.getKey().toString().equals("Cost")) {
                        costingstr=pd.getValue().toString();
                        cost.setText("Cost:" +costingstr);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        int section = mIntent.getIntExtra("section", 1);
        if (section==1) {
            Drawable myDarawable = getResources().getDrawable(MyData.drawableArray[position]);
            pic.setImageDrawable(myDarawable);
            /*content.setText(introstr);
            cost.setText("Cost: " + costingstr);
            title.setText(namestr);
            date.setText("Date: " + datestr);
            time.setText("Time: "+timestr);
            //day.setText("Day: "+);
            adults.setText(" Adult: "+adultstr);
            drinks.setText(" Drinks: "+drinkstr);
            foods.setText(" Food: "+foodstr);
            musics.setText(" Music: "+musicstr);
            venue.setText(venuestr);*/
            addr=venuestr;
            Geocoder gc = new Geocoder(LinkEventDisplay.this);
            if(gc.isPresent()){
                try {
                    List<Address> list = gc.getFromLocationName(venuestr, 1);
                    Address address = list.get(0);
                    lat = address.getLatitude();
                    lon= address.getLongitude();
                    // Toast.makeText(this, Double.toString(lat), Toast.LENGTH_SHORT).show();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);

                }catch (Exception e){}}
        }else  if (section==2) {
            Drawable myDarawable = getResources().getDrawable(MyData.informaldrawableArray[position]);
            pic.setImageDrawable(myDarawable);
            content.setText(MyData.informalcontentArray[position]);
            cost.setText("Venue: " + MyData.roomArray2[position]);
            title.setText(MyData.informalArray[position]);
            date.setText("Cost: " + Integer.toString(MyData.scoreArray2[position]));
        }else  if (section==3) {
            Drawable myDarawable = getResources().getDrawable(MyData.workshopdrawableArray[position]);
            pic.setImageDrawable(myDarawable);
            content.setText(MyData.workshopcontentArray[position]);
            cost.setText("Venue: " + MyData.roomArray3[position]);
            title.setText(MyData.workshopArray[position]);
            date.setText("Cost: " + Integer.toString(MyData.scoreArray3[position]));
        }
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(LinkEventDisplay.this,R.color.colorBlue));
        //View objects
        buttonScan = (Button) findViewById(R.id.button);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pay=new Intent(LinkEventDisplay.this,PaymentActivity.class);
                pay.putExtra("ID",ID);
                pay.putExtra("Name",namestr);
                startActivity(pay);
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(addr));
        //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 14.0f));

    }
}
