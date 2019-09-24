package com.example.siddhantlad.taqneeq;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    android.support.design.widget.FloatingActionButton fab, fab2;
    ImageView img;
    String livecontent;
    TextView live,coins;
    Button scanbtn,backscan;
    DatabaseReference mDatabase,info;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        backscan=(Button)findViewById(R.id.button9);
        backscan.setEnabled(false);
        live=findViewById(R.id.textlive);
        coins=(TextView)findViewById(R.id.textView11);
        info=FirebaseDatabase.getInstance().getReference("users");
           info.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            final String name = postSnapshot.getKey().toString().trim();
                            final DatabaseReference dataref = info.child(name);
                            dataref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot1) {
                                    for (DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                                        final String names = postSnapshot1.getKey().toString().trim();
                                        dataref.child(names).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                if (dataSnapshot2.getValue() != null) {
                                                    if(names.equals("Score")) {
                                                        coins.setText(dataSnapshot2.getValue().toString());
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                                                        livecontent=new String();
        mDatabase=FirebaseDatabase.getInstance().getReference("Live");
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
        Drawable myDrawable = getResources().getDrawable(R.drawable.tqlogo);
        Drawable myDrawable1 = getResources().getDrawable(R.drawable.tqlogo);
        icon.setImageDrawable(myDrawable);
/*
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();*/
        fab = findViewById(R.id.fabs);
        fab2 = findViewById(R.id.fabs2);
        fab.setImageDrawable(myDrawable);
        fab2.setImageDrawable(myDrawable);
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
        itemIcon.setImageDrawable(myDrawable);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).setLayoutParams(new FrameLayout.LayoutParams(200, 200)).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(myDrawable);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(myDrawable);
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        ImageView itemIcon4 = new ImageView(this);
        itemIcon4.setImageDrawable(myDrawable1);
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();
        ImageView itemIcon5 = new ImageView(this);
        itemIcon.setImageDrawable(myDrawable);
        SubActionButton button5 = itemBuilder2.setContentView(itemIcon5).setLayoutParams(new FrameLayout.LayoutParams(1, 1)).build();
        ImageView itemIcon6 = new ImageView(this);
        itemIcon2.setImageDrawable(myDrawable);
        SubActionButton button6 = itemBuilder2.setContentView(itemIcon6).build();
        ImageView itemIcon7 = new ImageView(this);
        itemIcon3.setImageDrawable(myDrawable);
        SubActionButton button7 = itemBuilder2.setContentView(itemIcon7).build();
        ImageView itemIcon8 = new ImageView(this);
        itemIcon4.setImageDrawable(myDrawable1);
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
        user=mAuth.getCurrentUser();
        ArrayList<ModelClass> items = new ArrayList<>();
        ArrayList<ModelClass> items2 = new ArrayList<>();
        ArrayList<ModelClass> items3 = new ArrayList<>();
        CustomAdapter adapter = new CustomAdapter(this, items);
        CustomAdapter adapter2 = new CustomAdapter(this, items2);
        CustomAdapter adapter3 = new CustomAdapter(this, items3);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView recyclerView2 = findViewById(R.id.my_recycler_view2);
        RecyclerView recyclerView3 = findViewById(R.id.my_recycler_view3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
        recyclerView3.setAdapter(adapter);

// let's create 10 random items
        for (int i = 0; i < 10; i++) {
            items.add(new ModelClass(MyData.drawableArray[i], MyData.nameArray[i],MyData.roomArray[i],MyData.scoreArray[i]));
            adapter.notifyDataSetChanged();
            items2.add(new ModelClass(MyData.drawableArray[i], MyData.nameArray[i],MyData.roomArray[i],MyData.scoreArray[i]));
            adapter2.notifyDataSetChanged();
            items3.add(new ModelClass(MyData.drawableArray[i], MyData.nameArray[i],MyData.roomArray[i],MyData.scoreArray[i]));
            adapter3.notifyDataSetChanged();
        }
    }
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
                 //   Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    if (result.getContents().equals("Taqneeq-SNL-50")){
                    //    Toast.makeText(this, "Game Entered- 50 Deducted", Toast.LENGTH_SHORT).show();
                        int val=Integer.parseInt(coins.getText().toString())-50;
                        coins.setText(Integer.toString(val));
                        info.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    final String name = postSnapshot.getKey().toString().trim();
                                    final DatabaseReference dataref = info.child(name);
                                    dataref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot1) {
                                            for (DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                                                final String names = postSnapshot1.getKey().toString().trim();
                                                Toast.makeText(MainActivity.this, postSnapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
                                               // Toast.makeText(MainActivity.this, names, Toast.LENGTH_SHORT).show();
                                              //  info.child("eI5liFhtRsSNOuXpbovLran7VK93").child("Score").setValue("234");
                                               final DatabaseReference dataref2 = info.child(name);
                                                Toast.makeText(MainActivity.this, names, Toast.LENGTH_SHORT).show();
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
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}