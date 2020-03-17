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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LinkEventDisplay extends AppCompatActivity implements OnMapReadyCallback {
    private Button buttonScan;
    String addr="";
    double lat,lon;
    DatabaseReference eventData;
    ImageView pic;
    FirebaseAuth mAuth;
    Button like,share;
    DatabaseReference liked;
    Boolean alreadyLiked;
    TextView content,title,cost,date,time,day,venue;
    String days;
    CheckBox drinks,adults,musics,foods;
    //qr code scanner object
    String adultstr,costingstr,datestr,drinkstr,foodstr,introstr,musicstr,namestr,timestr,venuestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_event_display);
        Window window = LinkEventDisplay.this.getWindow();
        ViewPager viewPager = findViewById(R.id.viewpager);
        eventData = FirebaseDatabase.getInstance().getReference("exhibitions");
        Intent mIntent = getIntent();
        final String ID = mIntent.getStringExtra("UID");

        ImageAdapter adapter = new ImageAdapter(this, ID);
        viewPager.setAdapter(adapter);
        content = findViewById(R.id.textView);
        pic = findViewById(R.id.eventpic);
        title = findViewById(R.id.textView12);
        cost = findViewById(R.id.t1);
        date = findViewById(R.id.t2);
        time = findViewById(R.id.t3);
        day = findViewById(R.id.t4);
        adults = findViewById(R.id.c1);
        drinks = findViewById(R.id.c2);
        venue = findViewById(R.id.textView20);
        foods = findViewById(R.id.c3);
        musics = findViewById(R.id.c4);
        like = findViewById(R.id.button11);
        share = findViewById(R.id.button12);
        int position = mIntent.getIntExtra("position", 0);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            liked = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("Saved");
            try {
                liked.child(ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            if (dataSnapshot.getValue().equals(ID)) {
                                alreadyLiked = true;
                                like.setBackgroundResource(R.drawable.liked);
                            }
                        } else {
                            alreadyLiked = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } catch (Exception e) {
            }
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    if (alreadyLiked) {
                        liked.child(ID).removeValue();
                        like.setBackgroundResource(R.drawable.like);
                    } else {
                        liked.child(ID).setValue(ID);
                        like.setBackgroundResource(R.drawable.liked);
                    }
                } else {
                    Toast.makeText(LinkEventDisplay.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LinkEventDisplay.this, RegisterActivity.class));
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey There! I want you to check out this event on the evEntry App to view it open the link from WhatsApp and select evEntry to open the link with: " + "http://evEntry.epizy.com/events/" + ID);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
        eventData.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot pd : dataSnapshot.getChildren()) {
                    if (pd.getKey().toString().equals("Adult")) {
                        adultstr = pd.getValue().toString();
                    } else if (pd.getKey().toString().equals("Date")) {
                        datestr = pd.getValue().toString();
                        date.setText("Date: " + datestr);
                    } else if (pd.getKey().toString().equals("Drinks")) {
                        drinkstr = pd.getValue().toString();
                    } else if (pd.getKey().toString().equals("Food")) {
                        foodstr = pd.getValue().toString();
                    } else if (pd.getKey().toString().equals("Intro")) {
                        introstr = pd.getValue().toString();
                        content.setText(introstr);
                    } else if (pd.getKey().toString().equals("Music")) {
                        musicstr = pd.getValue().toString();
                    } else if (pd.getKey().toString().equals("Name")) {
                        namestr = pd.getValue().toString();
                        title.setText(namestr);
                    } else if (pd.getKey().toString().equals("Time")) {
                        timestr = pd.getValue().toString();
                        time.setText("Time: " + timestr);
                    } else if (pd.getKey().toString().equals("Venue")) {
                        venuestr = pd.getValue().toString();
                        venue.setText(venuestr);
                    } else if (pd.getKey().toString().equals("Cost")) {
                        costingstr = pd.getValue().toString();
                        cost.setText("Cost:" + costingstr);
                    }
                    if (musicstr != null) {
                        if (adultstr.equals("Yes")) {
                            adults.setChecked(true);
                        }
                        if (foodstr.equals("Yes")) {
                            foods.setChecked(true);
                        }
                        if (musicstr.equals("Yes")) {
                            musics.setChecked(true);
                        }
                        if (drinkstr.equals("Yes")) {
                            drinks.setChecked(true);
                        }
                    }

                    if (datestr!=null) {
                        String year = datestr.substring(6, 10);
                        String month = (datestr.substring(3, 5));
                        String dates = (datestr.substring(0, 2));
                        days = new String();
                        //Toast.makeText(context, Integer.parseInt(date)+"/"+Integer.parseInt(month)+"/"+Integer.parseInt(year), Toast.LENGTH_SHORT).show();
                        Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(dates)); // Note that Month value is 0-based. e.g., 0 for January.
                        int result = calendar.get(Calendar.DAY_OF_WEEK);
                        switch (result) {
                            case Calendar.MONDAY:
                                days = "Monday";
                                break;
                            case Calendar.TUESDAY:
                                days = "Tuesday";
                                break;
                            case Calendar.WEDNESDAY:
                                days = "Wednesday";
                                break;
                            case Calendar.THURSDAY:
                                days = "Thursday";
                                break;
                            case Calendar.FRIDAY:
                                days = "Friday";
                                break;
                            case Calendar.SATURDAY:
                                days = "Saturday";
                                break;
                            case Calendar.SUNDAY:
                                days = "Sunday";
                                break;
                        }
                        day.setText("Day: "+days);
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
