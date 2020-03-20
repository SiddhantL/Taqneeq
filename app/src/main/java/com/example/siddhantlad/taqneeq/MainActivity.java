package com.example.siddhantlad.taqneeq;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
/*import android.support.design.widget.FloatingActionButton;*/
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.oguzdev.circularfloatingactionmenu.library.animation.MenuAnimationHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    SearchView searchView;
    ScrollView scrollView;
    FloatingActionMenu actionMenu;
    ArrayList<ModelClass2> items;
    ArrayList<ModelClass2> items2;
    RecyclerView recyclerView2;
    CustomAdapter3 adapter2;
    ArrayList<String> saved;
    DatabaseReference mDataEvent;
    View divider;
    android.support.design.widget.FloatingActionButton fab, fab2;
    ImageView img;
    String livecontent;
    int score, deduct,s, s1;
    TextView live, coins;
    Button scanbtn, backscan;
    ImageView profileBut;
    DatabaseReference mDatabase, info, mDataList, mDataExhibition;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        deduct = 0;
        searchView = findViewById(R.id.search);
        scrollView = findViewById(R.id.scrollview);
        backscan = findViewById(R.id.button9);
        backscan.setEnabled(false);
        profileBut = findViewById(R.id.imageView10);
        mAuth = FirebaseAuth.getInstance();
//Display Profile of User
        profileBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, ProfileDisplay.class));
                }else{
                    Toast.makeText(MainActivity.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
            }
        });

        live = findViewById(R.id.textlive);
        divider = findViewById(R.id.divider);
        coins = (TextView) findViewById(R.id.textView11);
        SharedPreferences settings = getSharedPreferences("coinVal", 0);
        String coinVals = settings.getString("coinVal", "0");
        coins.setText(coinVals);
        livecontent = new String();
        mDatabase = FirebaseDatabase.getInstance().getReference("Live");
        mDataList = FirebaseDatabase.getInstance().getReference();
//Show Live Indicator
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                livecontent = "";
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String keyss = postSnapshot.getKey().toString().trim();
                    final DatabaseReference dataref = mDatabase.child(keyss);
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            livecontent = livecontent + " ⚪" + dataSnapshot.getValue().toString().trim();
                            live.setText(livecontent.toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        info = FirebaseDatabase.getInstance().getReference("users");
        Window window = MainActivity.this.getWindow();
        //Start Movement of Indicator
        findViewById(R.id.textlive).setSelected(true);

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorBlue));
        img = findViewById(R.id.overlay);
        scanbtn = findViewById(R.id.button3);
        qrScan = new IntentIntegrator(this);
        //Scan Tickets
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
        //Menu Scroll Animation
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionMenu.close(true);
                fab2.show();
                fab.hide();
                fab2.animate().translationY(-10).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        actionMenu.close(true);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        img.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
        mAuth = FirebaseAuth.getInstance();
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);

        ImageView icon = new ImageView(this); // Create an icon
        Drawable myDrawable1 = getResources().getDrawable(R.drawable.helplef);
        Drawable myDrawableUser = getResources().getDrawable(R.drawable.userlef);
        Drawable myDrawable2 = getResources().getDrawable(R.drawable.belllef);
        Drawable myDrawable3 = getResources().getDrawable(R.drawable.ttlef);
        Drawable myDrawable4 = getResources().getDrawable(R.drawable.trophylef);
        Drawable myDrawables = getResources().getDrawable(R.drawable.tqlogolef);
        icon.setImageDrawable(myDrawables);

        fab = findViewById(R.id.fabs);
        fab2 = findViewById(R.id.fabs2);
        fab.setImageDrawable(myDrawableUser);
        fab2.setImageDrawable(myDrawables);
        fab2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                float parentCenterX = relativeLayout.getX() + relativeLayout.getWidth() / 2;
                float parentCenterY = relativeLayout.getY() + relativeLayout.getHeight() / 2;
                fab2.animate().translationY(parentCenterY - fab2.getHeight() / 2 - 10).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        img.setVisibility(View.VISIBLE);
                        fab2.hide();
                        fab.show();
                        try {

                            actionMenu.open(true);
                            fab.setEnabled(false);
                            img.setEnabled(false);

                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    fab.setEnabled(true);
                                    img.setEnabled(true);
                                }
                            }, 600);

                        } catch (Exception E) {
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
// repeat many times:
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(myDrawable4);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).setLayoutParams(new FrameLayout.LayoutParams(200, 200)).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(myDrawable2);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(myDrawable3);
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        ImageView itemIcon4 = new ImageView(this);
        itemIcon4.setImageDrawable(myDrawable3);
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();
        ImageView itemIcon5 = new ImageView(this);
        itemIcon.setImageDrawable(myDrawable3);
        SubActionButton button5 = itemBuilder2.setContentView(itemIcon5).setLayoutParams(new FrameLayout.LayoutParams(1, 1)).build();
        ImageView itemIcon6 = new ImageView(this);
        itemIcon2.setImageDrawable(myDrawable4);
        SubActionButton button6 = itemBuilder2.setContentView(itemIcon6).build();
        ImageView itemIcon7 = new ImageView(this);
        itemIcon3.setImageDrawable(myDrawable1);
        SubActionButton button7 = itemBuilder2.setContentView(itemIcon7).build();
        ImageView itemIcon8 = new ImageView(this);
        itemIcon4.setImageDrawable(myDrawable2);
        SubActionButton button8 = itemBuilder2.setContentView(itemIcon8).build();
        final Intent helpsl = new Intent(MainActivity.this, HelpSlide.class);

        //FragmentActivity Display
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    helpsl.putExtra("val", 2);
                    startActivity(helpsl);
                } else {
                    Toast.makeText(MainActivity.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    helpsl.putExtra("val", 3);
                    startActivity(helpsl);
                } else {
                    Toast.makeText(MainActivity.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    helpsl.putExtra("val", 0);
                    startActivity(helpsl);
                } else {
                    Toast.makeText(MainActivity.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    helpsl.putExtra("val", 1);
                    startActivity(helpsl);
                } else {
                    Toast.makeText(MainActivity.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
            }
        });
        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button5)
                .addSubActionView(button6)
                .addSubActionView(button7)
                .addSubActionView(button8)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .setRadius(400)
                .setAnimationHandler(new CustomFabAnim())
                .setStartAngle(0)
                .setEndAngle(360)
                .attachTo(fab)
                .build();

//Display Profile
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(MainActivity.this, ProfileDisplay.class));
                }else {
                    Toast.makeText(MainActivity.this, "Register to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
            }});
        user=mAuth.getCurrentUser();
        items = new ArrayList<>();
        items2 = new ArrayList<>();
        final ArrayList<ModelClass> items3 = new ArrayList<>();
        final CustomAdapter4 adapter = new CustomAdapter4(this, items);
        adapter2 = new CustomAdapter3(this, items2);
        // final CustomAdapter3 adapter3 = new CustomAdapter3(this, items3);
        //Placeholder display
        for (int i = 0; i < 5; i++) {
            EventData data=new EventData();
            data.setAdult("Yes");
            data.setCost("0");
            data.setDate("00/00/0000");
            data.setDrinks("Yes");
            data.setFood("Yes");
            data.setID("");
            data.setIntro("This is a Loading Message...");
            data.setMusic("Yes");
            data.setName("Loading...");
            data.setTime("00:00");
            data.setVenue("Venue: India");
            items.add(new ModelClass2(data));
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < 10; i++) {
            EventData data=new EventData();
            data.setAdult("Yes");
            data.setCost("0");
            data.setDate("00/00/0000");
            data.setDrinks("Yes");
            data.setFood("Yes");
            data.setID("");
            data.setIntro("This is a Loading Message...");
            data.setMusic("Yes");
            data.setName("Loading...");
            data.setTime("00:00");
            data.setVenue("Venue: India");
           items2.add(new ModelClass2(data));
           //items2.add(new ModelClass2(MyData.drawableArray[i], "Loading...", "Venue: India", 0,"00/00/0000","00:00","Yes","Yes","Yes","Yes","This is a Loading Message...",""));
            adapter2.notifyDataSetChanged();
        }
        for (int i = 0; i < 5; i++) {
            items3.add(new ModelClass(MyData.drawableArray[i], "Loading...", "Venue: India", 0,"00/00/0000","00:00","Yes","Yes","Yes","Yes","This is a Loading Message...",""));
          //  adapter3.notifyDataSetChanged();
        }
        final RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView2 = findViewById(R.id.my_recycler_view2);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        mDataEvent=FirebaseDatabase.getInstance().getReference("exhibitions");
        mDataList = FirebaseDatabase.getInstance().getReference("Featured");
        saved=new ArrayList<>();
        mDataList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot pd : dataSnapshot.getChildren()) {
                    saved.add(pd.getKey().toString());
                    s = saved.size();
                }
                if (!saved.isEmpty()) {
                    items.clear();
                    for (int i = 0; i < saved.size(); i++) {
                        mDataEvent.child(saved.get(i)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String name=dataSnapshot.getKey().toString();
                                EventData data=new EventData();
                                data.setAdult(dataSnapshot.child("Adult").getValue(String.class));
                                data.setCost(dataSnapshot.child("Cost").getValue().toString());
                                data.setDate(dataSnapshot.child("Date").getValue(String.class));
                                data.setDrinks(dataSnapshot.child("Drinks").getValue(String.class));
                                data.setFood(dataSnapshot.child("Food").getValue(String.class));
                                data.setID(name);
                                data.setIntro(dataSnapshot.child("Intro").getValue(String.class));
                                data.setMusic(dataSnapshot.child("Music").getValue(String.class));
                                data.setName(dataSnapshot.child("Name").getValue(String.class));
                                data.setTime(dataSnapshot.child("Time").getValue(String.class));
                                data.setVenue(dataSnapshot.child("Venue").getValue(String.class));
                                items.add(new ModelClass2(data));
                                adapter.notifyDataSetChanged();
                                }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Adding Events to RecyclerView
        mDataExhibition=FirebaseDatabase.getInstance().getReference("exhibitions");
        mDataExhibition.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Save Data Packets of Each Event in Separate ArrayLists
                        items2.clear();
                        for (final DataSnapshot pd1:dataSnapshot.getChildren()){
                            final String name=pd1.getKey().toString();
                            EventData data=new EventData();
                            data.setAdult(pd1.child("Adult").getValue(String.class));
                            data.setCost(pd1.child("Cost").getValue().toString());
                            data.setDate(pd1.child("Date").getValue(String.class));
                            data.setDrinks(pd1.child("Drinks").getValue(String.class));
                            data.setFood(pd1.child("Food").getValue(String.class));
                            data.setID(name);
                            data.setIntro(pd1.child("Intro").getValue(String.class));
                            data.setMusic(pd1.child("Music").getValue(String.class));
                            data.setName(pd1.child("Name").getValue(String.class));
                            data.setTime(pd1.child("Time").getValue(String.class));
                            data.setVenue(pd1.child("Venue").getValue(String.class));
                            items2.add(new ModelClass2(data));
                            adapter2.notifyDataSetChanged();}
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    final DatabaseReference eventdata=FirebaseDatabase.getInstance().getReference("participants");
                    final DatabaseReference scandata=FirebaseDatabase.getInstance().getReference("Scan");
                    scandata.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                if (postSnapshot.getKey().toString().equals(result.getContents())){
                                    scandata.child(postSnapshot.getKey().toString()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (final DataSnapshot postSnapshot2 : dataSnapshot.getChildren()) {
                                                if (Integer.parseInt(postSnapshot2.getValue().toString())<=Integer.parseInt(coins.getText().toString())){
                                                    deduct=Integer.parseInt(postSnapshot2.getValue().toString());
                                                    score=Integer.parseInt(coins.getText().toString())-deduct;
                                                    info.child(mAuth.getCurrentUser().getUid()).child("Score").setValue(Integer.toString(score));
                                                    deduct=0;
                                                    final String eventcode = postSnapshot2.getValue().toString();
                                                    final String eventName = postSnapshot2.getKey().toString();
                                                    final String id = eventdata.push().getKey();
                                                    eventdata.child(eventName).child(mAuth.getCurrentUser().getUid()).child("Name").setValue(mAuth.getCurrentUser().getDisplayName());
                                                    final int scorechanged = Integer.parseInt(coins.getText().toString());
                                                    eventdata.child(eventName).child(mAuth.getCurrentUser().getUid()).child("Score").setValue(Integer.toString(scorechanged));
                                                    final DatabaseReference userdata = FirebaseDatabase.getInstance().getReference("users");
                                                    userdata.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                final String name = postSnapshot.getKey().toString().trim();
                                                                if (name.equals(mAuth.getCurrentUser().getUid())) {
                                                                    final DatabaseReference dataref = userdata.child(name);
                                                                    dataref.addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            for (DataSnapshot postSnapshot1 : dataSnapshot.getChildren()) {
                                                                                final String names = postSnapshot1.getKey().toString().trim();
                                                                                dataref.child(names).addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                        if (dataSnapshot.getValue() != null) {
                                                                                            if (names.equals("Phone")) {
                                                                                                String phoneNum = dataSnapshot.getValue().toString().trim();
                                                                                                eventdata.child(eventName).child(mAuth.getCurrentUser().getUid()).child("Phone").setValue(phoneNum);

                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                    }
                                                                                });
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }else{
                                                    Toast.makeText(MainActivity.this, "Invalid Cash", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    //

                                    //

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    info=FirebaseDatabase.getInstance().getReference("users");
                    info.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //     Toast.makeText(MainActivity.this, postSnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();//ID
                                if (postSnapshot.getKey().toString().equals(mAuth.getCurrentUser().getUid())){
                                    //   Toast.makeText(MainActivity.this, "Yay", Toast.LENGTH_SHORT).show();
                                    info.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                            for (final DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                                                //     Toast.makeText(MainActivity.this, postSnapshot1.getKey().toString(), Toast.LENGTH_SHORT).show();
                                                if (postSnapshot1.getKey().toString().equals("Score")){
                                                    score=Integer.parseInt(postSnapshot1.getValue().toString());

                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, divider.getBottom());
                    }
                });

            }
        });
        if (searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }
    private void search(String str){
        ArrayList<ModelClass2> filterList = new ArrayList<>();
        for (ModelClass2 object:items2){
            if (object.getData().getName().toLowerCase().contains(str.toLowerCase())){
                filterList.add(object);
            }
        }
        CustomAdapter3 filteredAdapter = new CustomAdapter3(this, filterList);
        recyclerView2.setAdapter(filteredAdapter);
    }
    public String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("" + (int) dayCount + " Days");
    }
}