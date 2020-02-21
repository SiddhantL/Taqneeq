package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TicketSelector extends AppCompatActivity implements CustomTicketSelectAdapter.OnButtonClickUpdate {
    DatabaseReference tickets;
    CustomTicketSelectAdapter adapterTix;
    RecyclerView recyclerView2;
    String idS,dateS,venue,time,enterS,nameS,tixName;
    String ID;
    ArrayList<ModelClassTicket> itemsTix;
    ArrayList<String> name,cost,enters,id,status;
    Button paymentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selector);
        ID=getIntent().getStringExtra("ID");
        Intent mIntent=getIntent();
        nameS=mIntent.getStringExtra("Name");
        idS=mIntent.getStringExtra("ID");
        dateS=mIntent.getStringExtra("Date");
        time=mIntent.getStringExtra("Time");
        enterS=mIntent.getStringExtra("Enters");
        venue=mIntent.getStringExtra("Venue");
        paymentPage=findViewById(R.id.button10);
        name=new ArrayList<>();
        enters=new ArrayList<>();
        cost=new ArrayList<>();
        id=new ArrayList<>();
        status=new ArrayList<>();
        itemsTix=new ArrayList<>();
        adapterTix = new CustomTicketSelectAdapter(this, itemsTix);

        if (adapterTix.getSelectedID()!=null) {
            int idMatch = 0;
            for (int i = 0; i < id.size(); i++) {
                if (adapterTix.getSelectedID() == id.get(i)) {
                    idMatch = i;
                }
            }
        }
        recyclerView2 = findViewById(R.id.my_recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView2.setAdapter(adapterTix);
        tickets= FirebaseDatabase.getInstance().getReference("exhibitions").child(ID).child("Tickets");
        tickets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot ticketId:dataSnapshot.getChildren()){
                    String tixId=ticketId.getKey();
                    id.add(ticketId.getKey().toString());
                    tickets.child(tixId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot ticketElements:dataSnapshot.getChildren()){
                                if (ticketElements.getKey().equals("Name")){
                                    name.add(ticketElements.getValue().toString());
                                }else if (ticketElements.getKey().equals("Enters")){
                                    enters.add(ticketElements.getValue().toString());
                                }else if (ticketElements.getKey().equals("Cost")){
                                    cost.add(ticketElements.getValue().toString());
                                }else if (ticketElements.getKey().equals("Status")){
                                    status.add(ticketElements.getValue().toString());
                                }
                            }
                            if (name.size()==enters.size()&&enters.size()==id.size()&&id.size()==status.size()&&status.size()==cost.size()&&id.size()!=0){
                                itemsTix.clear();
                                for (int i=0;i<name.size();i++){
                                    itemsTix.add(new ModelClassTicket(MyData.informaldrawableArray[0],id.get(i),name.get(i),enters.get(i),cost.get(i),status.get(i),ID));
                                    adapterTix.notifyDataSetChanged();
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
    public void onButtonClick(String id, final int num, int cost, String name, int enters) {
        final int totalcost=num*cost;
        final int totalEnters=num*enters;
        final String tixName=name;
        paymentPage.setText("Pay "+Integer.toString(totalcost)+" ₹ For "+Integer.toString(totalEnters)+" Entries");
        paymentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pay = new Intent(TicketSelector.this, PaymentActivity.class);
                pay.putExtra("ID", ID);
                pay.putExtra("Name", nameS);
                pay.putExtra("Date", dateS);
                pay.putExtra("Venue", venue);
                pay.putExtra("Time", time);
                pay.putExtra("Enters", Integer.toString(totalEnters));
                pay.putExtra("Cost",Integer.toString(totalcost));
                pay.putExtra("Tickets",Integer.toString(num));
                pay.putExtra("nameOfTix",tixName);
                startActivity(pay);
            }
        });
    }
}
