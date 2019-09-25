package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileDisplay extends AppCompatActivity {
    Button lgout;
FirebaseAuth mAuth;
int wins;
String phoneNum;
TextView names,coinss,num,won,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);
        Window window = ProfileDisplay.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(ProfileDisplay.this,R.color.colorBlue));

        lgout = findViewById(R.id.button8);
        mAuth= FirebaseAuth.getInstance();
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   mAuth.signOut();
                   startActivity(new Intent(ProfileDisplay.this,LoginActivity.class));
            finish();
            }
        });
        names=(findViewById(R.id.textView13));
        email=(findViewById(R.id.txt3));
        coinss=(findViewById(R.id.txt5));
        num=(findViewById(R.id.txt4));
        won=(findViewById(R.id.txt2));
        names.setText((mAuth.getCurrentUser().getDisplayName()).toUpperCase());
        email.setText("EMAIL: "+(mAuth.getCurrentUser().getEmail()).toUpperCase());
        final DatabaseReference userdata= FirebaseDatabase.getInstance().getReference("users");
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
                                                  phoneNum = "PHONE NUMBER:"+dataSnapshot.getValue().toString().trim();
                                                   num.setText(phoneNum);
                                                }else if(names.equals("Score")){
                                                    String score = "TQ CASH: "+dataSnapshot.getValue().toString().trim();
                                                    coinss.setText(score);
                                                }
                                            }
                                            final DatabaseReference mData=FirebaseDatabase.getInstance().getReference("winners");
                                            mData.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                        final String eventname = postSnapshot.getKey().toString().trim();
                                                        final DatabaseReference dataref = mData.child(eventname);
                                                        dataref.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                                for (final DataSnapshot postSnapshot : dataSnapshot2.getChildren()) {
                                                                    if (dataSnapshot2.getValue() != null) {
                                                                        String alert = postSnapshot.getValue().toString().trim();
                                                                        //Toast.makeText(ProfileDisplay.this, postSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                                                        if (postSnapshot.getKey().toString().equals("Winner1Phone")) {
                                                                            if (postSnapshot.getValue().toString().equals(phoneNum)) {
                                                                                wins++;
                                                                                won.setText("WINS: "+Integer.toString(wins));
                                                                            }
                                                                        }else  if (postSnapshot.getKey().toString().equals("Winner2Phone")) {
                                                                            if (postSnapshot.getValue().toString().equals(phoneNum)) {
                                                                             //   Toast.makeText(ProfileDisplay.this, phoneNum, Toast.LENGTH_SHORT).show();
                                                                                wins++;
                                                                                won.setText("WINS: "+Integer.toString(wins));
                                                                            }
                                                                        }else  if (postSnapshot.getKey().toString().equals("Winner3Phone")) {
                                                                            if (postSnapshot.getValue().toString().equals(phoneNum)) {
                                                                                wins++;
                                                                                won.setText("WINS: "+Integer.toString(wins));
                                                                            }
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
       /* coinss.setText(mAuth.getCurrentUser().getDisplayName());
        won.setText(mAuth.getCurrentUser().getDisplayName());
        num.setText(mAuth.getCurrentUser().getDisplayName());*/

    }
}
