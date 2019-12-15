/*
package com.example.siddhantlad.taqneeq;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
*/
/*import android.support.design.widget.FloatingActionButton;*//*

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.oguzdev.circularfloatingactionmenu.library.animation.MenuAnimationHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    FloatingActionMenu actionMenu;
    ArrayList<String> customEventName,customEventVenue,customEventCost,customEventDate,customEventTime,customEventDrinks,customEventAdult,customEventFood,customEventMusic,customEventIntro;
    ArrayList<String> customExhibitionName,customExhibitionVenue,customExhibitionCost,customExhibitionDate,customExhibitionTime,customExhibitionDrinks,customExhibitionAdult,customExhibitionFood,customExhibitionMusic,customExhibitionIntro;
    android.support.design.widget.FloatingActionButton fab, fab2;
    ImageView img;
    String livecontent;
    int score,deduct,eventno,workshopno,informalno,s,l;
    TextView live,coins;
    Button scanbtn,backscan;
    DatabaseReference mDatabase,info,mDataList,mDataExhibition,mDataWorkshop;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        deduct=0;
        backscan=(Button)findViewById(R.id.button9);
        backscan.setEnabled(false);
        live=findViewById(R.id.textlive);
        customEventName=new ArrayList<>();
        customEventVenue=new ArrayList<>();
        customEventCost=new ArrayList<>();
        customEventDate=new ArrayList<>();
        customEventTime=new ArrayList<>();
        customEventAdult=new ArrayList<>();
        customEventDrinks=new ArrayList<>();
        customEventFood=new ArrayList<>();
        customEventMusic=new ArrayList<>();
        customEventIntro=new ArrayList<>();
        customExhibitionName=new ArrayList<>();
        customExhibitionVenue=new ArrayList<>();
        customExhibitionCost=new ArrayList<>();
        customExhibitionDate=new ArrayList<>();
        customExhibitionTime=new ArrayList<>();
        customExhibitionAdult=new ArrayList<>();
        customExhibitionDrinks=new ArrayList<>();
        customExhibitionFood=new ArrayList<>();
        customExhibitionMusic=new ArrayList<>();
        customExhibitionIntro=new ArrayList<>();
        coins=(TextView)findViewById(R.id.textView11);
        livecontent=new String();
        mDatabase=FirebaseDatabase.getInstance().getReference("Live");
        mDataList=FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                livecontent="";
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String keyss= postSnapshot.getKey().toString().trim();
                    final DatabaseReference dataref = mDatabase.child(keyss);
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            livecontent=livecontent+" ⚪"+ dataSnapshot.getValue().toString().trim();
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
                                        //    Toast.makeText(MainActivity.this, postSnapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
                                        coins.setText(postSnapshot1.getValue().toString());
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
        Window window = MainActivity.this.getWindow();
        findViewById(R.id.textlive).setSelected(true);

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.colorBlue));
        img = findViewById(R.id.overlay);
        scanbtn=findViewById(R.id.button3);
        qrScan = new IntentIntegrator(this);
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
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
//
        ImageView icon = new ImageView(this); // Create an icon
        Drawable myDrawable1 = getResources().getDrawable(R.drawable.helplef);
        Drawable myDrawableUser = getResources().getDrawable(R.drawable.userlef);
        Drawable myDrawable2 = getResources().getDrawable(R.drawable.belllef);
        Drawable myDrawable3 = getResources().getDrawable(R.drawable.ttlef);
        Drawable myDrawable4 = getResources().getDrawable(R.drawable.trophylef);
        Drawable myDrawables = getResources().getDrawable(R.drawable.tqlogolef);
        icon.setImageDrawable(myDrawables);
*/
/*
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();*//*

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
                            },600);

                        }catch (Exception E){
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
        final Intent helpsl=new Intent(MainActivity.this,HelpSlide.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpsl.putExtra("val",2);
                startActivity(helpsl);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpsl.putExtra("val",3);
                startActivity(helpsl);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpsl.putExtra("val",0);
                startActivity(helpsl);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpsl.putExtra("val",1);
                startActivity(helpsl);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
*/
/*                                       actionMenu.close(true);
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
                                       });*//*

                startActivity(new Intent(MainActivity.this,ProfileDisplay.class));
            }
        });
        user=mAuth.getCurrentUser();
        final ArrayList<ModelClass> items = new ArrayList<>();
        final ArrayList<ModelClass> items2 = new ArrayList<>();
        final ArrayList<ModelClass> items3 = new ArrayList<>();
        final CustomAdapter adapter = new CustomAdapter(this, items);
        final CustomAdapter2 adapter2 = new CustomAdapter2(this, items2);
        final CustomAdapter3 adapter3 = new CustomAdapter3(this, items3);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView recyclerView2 = findViewById(R.id.my_recycler_view2);
        RecyclerView recyclerView3 = findViewById(R.id.my_recycler_view3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);

// let's create 10 random items
        mDataList=FirebaseDatabase.getInstance().getReference("events");
        mDataList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long x=dataSnapshot.getChildrenCount();
                s=(int)x;
        */
/* for(final  DataSnapshot p:dataSnapshot.getChildren()){
s=p.getChildrenCount();
         }*//*

                l=0;
                mDataList.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot pd:dataSnapshot.getChildren()){
                            final String name=pd.getKey().toString();
                            mDataList.child(name).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //    Toast.makeText(MainActivity.this, dataSnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                                    for (final DataSnapshot pd : dataSnapshot.getChildren()) {
                                        if (pd.getKey().toString().equals("Date")) {
                                            customEventDate.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Venue")) {
                                            customEventVenue.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Name")) {
                                            customEventName.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Time")) {
                                            customEventTime.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Cost")) {
                                            customEventCost.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Drinks")) {
                                            customEventDrinks.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Adult")) {
                                            customEventAdult.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Food")) {
                                            customEventFood.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Music")) {
                                            customEventMusic.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Intro")) {
                                            customEventIntro.add(pd.getValue().toString());
                                        }
                                        //    Toast.makeText(MainActivity.this, Integer.toString(customEvent.size()), Toast.LENGTH_SHORT).show();

                                        if ( customEventVenue.size()==s&&customEventName.size()==s && customEventCost.size()==s&& customEventTime.size()==s&& customEventDate.size()==s && customEventVenue.size()!=0) {
                                            items.clear();
                                            for (int i = 0; i < customEventVenue.size(); i++) {
                                                //        Toast.makeText(MainActivity.this, customEvent.get(s-2), Toast.LENGTH_SHORT).show();
                                                items.add(new ModelClass(MyData.drawableArray[i], customEventName.get(i), customEventVenue.get(i), Integer.parseInt(customEventCost.get(i)),customEventDate.get(i),customEventTime.get(i),customEventAdult.get(i),customEventDrinks.get(i),customEventMusic.get(i),customEventFood.get(i),customEventIntro.get(i)));
                                                adapter.notifyDataSetChanged();
                                                //Toast.makeText(MainActivity.this, customEventName.get(i), Toast.LENGTH_SHORT).show();
                                            }
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDataExhibition=FirebaseDatabase.getInstance().getReference("exhibitions");
        mDataExhibition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long x=dataSnapshot.getChildrenCount();
                s=(int)x;
        */
/* for(final  DataSnapshot p:dataSnapshot.getChildren()){
s=p.getChildrenCount();
         }*//*

                l=0;
                mDataExhibition.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot pd:dataSnapshot.getChildren()){
                            final String name=pd.getKey().toString();
                            mDataExhibition.child(name).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //    Toast.makeText(MainActivity.this, dataSnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                                    for (final DataSnapshot pd : dataSnapshot.getChildren()) {
                                        if (pd.getKey().toString().equals("Date")) {
                                            customExhibitionDate.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Venue")) {
                                            customExhibitionVenue.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Name")) {
                                            customExhibitionName.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Time")) {
                                            customExhibitionTime.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Cost")) {
                                            customExhibitionCost.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Drinks")) {
                                            customExhibitionDrinks.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Adult")) {
                                            customExhibitionAdult.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Food")) {
                                            customExhibitionFood.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Music")) {
                                            customExhibitionMusic.add(pd.getValue().toString());
                                        }else if (pd.getKey().toString().equals("Intro")) {
                                            customExhibitionIntro.add(pd.getValue().toString());
                                        }
                                        //    Toast.makeText(MainActivity.this, Integer.toString(customExhibition.size()), Toast.LENGTH_SHORT).show();

                                        if ( customExhibitionVenue.size()==s&&customExhibitionName.size()==s && customExhibitionCost.size()==s&& customExhibitionTime.size()==s&& customExhibitionDate.size()==s && customExhibitionVenue.size()!=0) {
                                            items.clear();
                                            for (int i = 0; i < customExhibitionVenue.size(); i++) {
                                                //        Toast.makeText(MainActivity.this, customExhibition.get(s-2), Toast.LENGTH_SHORT).show();
                                                items2.add(new ModelClass(MyData.drawableArray[i], customExhibitionName.get(i), customExhibitionVenue.get(i), Integer.parseInt(customExhibitionCost.get(i)),customExhibitionDate.get(i),customExhibitionTime.get(i),customExhibitionAdult.get(i),customExhibitionDrinks.get(i),customExhibitionMusic.get(i),customExhibitionFood.get(i),customExhibitionIntro.get(i)));
                                                adapter2.notifyDataSetChanged();
                                                //Toast.makeText(MainActivity.this, customExhibitionName.get(i), Toast.LENGTH_SHORT).show();
                                            }
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       */
/* for (int j=0;j<5;j++){
            items2.add(new ModelClass(MyData.informaldrawableArray[j], MyData.informalArray[j],MyData.roomArray2[j],MyData.scoreArray2[j],"","","Yes","Yes","No","No",""));
            adapter2.notifyDataSetChanged();
        }*//*

        for (int k=0;k<5;k++){
            items3.add(new ModelClass(MyData.workshopdrawableArray[k], MyData.workshopArray[k],MyData.roomArray3[k],MyData.scoreArray3[k],"","","No","No","No","No",""));
            adapter3.notifyDataSetChanged();

        }
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
                    //if (result.getContents().equals("Taqneeq-SNL-50")){

                    //         Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
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
                                                    //    Toast.makeText(MainActivity.this, postSnapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
                                                    //  coins.setText(postSnapshot1.getValue().toString());
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


}*/
