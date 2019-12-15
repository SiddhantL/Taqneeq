package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class EventDisplay extends AppCompatActivity {
    private Button buttonScan;
    ImageView pic;
TextView content,title,cost,date,time,day,drinks,adults,musics,foods,venue;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);
        Window window = EventDisplay.this.getWindow();
        ViewPager viewPager = findViewById(R.id.viewpager);
        ImageAdapter adapter = new ImageAdapter(this);
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
        Intent mIntent = getIntent();
        String costing=mIntent.getStringExtra("costing");
        String drink=mIntent.getStringExtra("drinks");
        String adult=mIntent.getStringExtra("adult");
        String music=mIntent.getStringExtra("music");
        String food=mIntent.getStringExtra("food");
        String venues=mIntent.getStringExtra("venue");
        String dates=mIntent.getStringExtra("date");
        String titles=mIntent.getStringExtra("title");
        String times=mIntent.getStringExtra("time");
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
                qrScan.initiateScan();
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

}

