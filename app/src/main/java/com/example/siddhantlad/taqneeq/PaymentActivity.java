package com.example.siddhantlad.taqneeq;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends Activity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    //https://github.com/razorpay/razorpay-android-sample-app
    String name;
    String IDtix;
    DatabaseReference tickets,userdata;
    FirebaseAuth mAuth;
    TextView costTV,entersTV,nameTixTV;
    CheckBox checkBoxtc;
    String id,date,venue,time,enters,cost,ticketsnum,nameOfTix;
    DatabaseReference ticketUser,mTicket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent mIntent=getIntent();
        mAuth= FirebaseAuth.getInstance();
        TextView orderID=findViewById(R.id.orderid);
        TextView nameEvent=findViewById(R.id.nameEvent);
        costTV=findViewById(R.id.inr);
        entersTV=findViewById(R.id.enters);
        nameTixTV=findViewById(R.id.nameoftix);
        checkBoxtc=findViewById(R.id.checkBox);
        name=mIntent.getStringExtra("Name");
        id=mIntent.getStringExtra("ID");
        date=mIntent.getStringExtra("Date");
        time=mIntent.getStringExtra("Time");
        enters=mIntent.getStringExtra("Enters");
        venue=mIntent.getStringExtra("Venue");
        cost=mIntent.getStringExtra("Cost");
        nameOfTix=mIntent.getStringExtra("nameOfTix");
        ticketsnum=mIntent.getStringExtra("Tickets");
        tickets= FirebaseDatabase.getInstance().getReference("Tickets");
        userdata= FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("Tickets");
        nameEvent.setText(name);
        orderID.setText("Order ID #"+id.substring(1));
        entersTV.setText("Enters "+enters);
        costTV.setText(cost+"₹");
        nameTixTV.setText(nameOfTix);
        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxtc.isChecked()) {
                    startPayment();
                }else{
                    Toast.makeText(PaymentActivity.this, "Agree To The Terms & Conditions to Purchase Tickets", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", name+" Tickets");
            options.put("description", ticketsnum+" Ticket/"+enters+" Entries");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://i.ibb.co/wJpX2Fw/squareeventry.png");
            options.put("currency", "INR");
            options.put("amount", Integer.toString(Integer.parseInt(cost)*100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in Payment", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Intent tix=new Intent(PaymentActivity.this,TicketActivity.class);
            mTicket=FirebaseDatabase.getInstance().getReference("Tickets").child(id);
            ticketUser=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("Tickets").child(id);
                String pushTicketID = mTicket.push().getKey();
                IDtix=pushTicketID;
                ticketUser.child(pushTicketID).child("Divided").setValue("No");
                ticketUser.child(pushTicketID).child("Enters").setValue(enters);
                ticketUser.child(pushTicketID).child("Name").setValue(name);
                ticketUser.child(pushTicketID).child("Payment ID").setValue(razorpayPaymentID);
                mTicket.child(pushTicketID).setValue(enters);
            tix.putExtra("Name",name);
            tix.putExtra("Date",date);
            tix.putExtra("Venue",venue);
            tix.putExtra("Time",time);
            tix.putExtra("Enters",enters);
            tix.putExtra("ID",id);
            tix.putExtra("ticketID",IDtix);
            startActivity(tix);
        } catch (Exception e) {
            Log.e(TAG, "An Error Occurred", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment Failed"/* + code + " " + response*/, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "An Error Occurred", e);
        }
    }
}