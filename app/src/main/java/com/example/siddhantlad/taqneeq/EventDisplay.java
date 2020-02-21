package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventDisplay extends AppCompatActivity implements OnMapReadyCallback{
    private Button buttonScan;
    String addr="";
double lat,lon;
    ImageView pic;
    String ID;
    ArrayList<String> savedEvents;
TextView content,title,cost,date,time,day,drinks,adults,musics,foods,venue;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);
        Window window = EventDisplay.this.getWindow();
//        saveEvent(savedInstanceState);
        ViewPager viewPager = findViewById(R.id.viewpager);
        Intent mIntent = getIntent();
        ID=mIntent.getStringExtra("ID");
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
        String costing=mIntent.getStringExtra("costing");
        String drink=mIntent.getStringExtra("drinks");
        String adult=mIntent.getStringExtra("adult");
        String music=mIntent.getStringExtra("music");
        String food=mIntent.getStringExtra("food");
        final String venues=mIntent.getStringExtra("venue");
        final String dates=mIntent.getStringExtra("date");
        final String titles=mIntent.getStringExtra("title");
        final String times=mIntent.getStringExtra("time");
        String intros=mIntent.getStringExtra("intro");
        String days=mIntent.getStringExtra("day");
        int position = mIntent.getIntExtra("position", 0);
        int section = mIntent.getIntExtra("section", 1);
        if (section==1) {
            Drawable myDarawable = getResources().getDrawable(MyData.drawableArray[position]);
            pic.setImageDrawable(myDarawable);
            content.setText(intros);
            cost.setText("Cost: " + costing);
            title.setText(titles);
            date.setText("Date: " + dates);
            time.setText("Time: "+times);
            day.setText("Day: "+days);
            adults.setText(" Adult: "+adult);
            drinks.setText(" Drinks: "+drink);
            foods.setText(" Food: "+food);
            musics.setText(" Music: "+music);
            venue.setText(venues);
            addr=venues;
            Geocoder gc = new Geocoder(EventDisplay.this);
            if(gc.isPresent()){
                try {
                    List<Address> list = gc.getFromLocationName(venues, 1);
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
        window.setStatusBarColor(ContextCompat.getColor(EventDisplay.this,R.color.colorBlue));
        //View objects
        buttonScan = (Button) findViewById(R.id.button);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pay=new Intent(EventDisplay.this,TicketSelector.class);
                pay.putExtra("ID",ID);
                pay.putExtra("Name",titles);
                pay.putExtra("Date",dates);
                pay.putExtra("Venue",venues);
                pay.putExtra("Time",times);
                pay.putExtra("Enters","1");
                startActivity(pay);
            }
        });


    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

public void saveEvent(Bundle savedState){
   Boolean exists=false;
   savedEvents=new ArrayList<String>();
    savedEvents = savedState.getStringArrayList("savedEvents");
    for (int i=0;i<savedEvents.size();i++){
        if (savedEvents.get(i)==ID){
            exists=true;
        }
    }
    if (!exists) {
        savedEvents.add(ID);
        savedState.putStringArrayList("savedEvents", savedEvents);
    }
}
}


