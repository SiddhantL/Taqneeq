package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    ArrayList<String> ticketsEvent;
    ArrayList<String> ticketType,entering,divided,customExhibitionDate,customExhibitionName,customExhibitionVenue,customExhibitionTime;
    DatabaseReference mTicket,mDataExhibition;
    ArrayList<TicketModelClass> items;
    CustomTicketAdapter ticketAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    String idName;
    public Tab2() {
        // Required empty public constructor
    }


/**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment Tab2.
 */

    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        ticketsEvent=new ArrayList<>();
        ticketType=new ArrayList<>();
        customExhibitionDate = new ArrayList<>();
        customExhibitionVenue=new ArrayList<>();
        customExhibitionTime=new ArrayList<>();
        customExhibitionName=new ArrayList<>();
        entering=new ArrayList<>();
        divided=new ArrayList<>();
        items=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        ticketAdapter=new CustomTicketAdapter(getActivity(),items);
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mTicket = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("Tickets");
        mDataExhibition = FirebaseDatabase.getInstance().getReference("exhibitions");
        mTicket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(final DataSnapshot pd:dataSnapshot.getChildren()){
                    //Toast.makeText(getActivity(), pd.getKey().toString(), Toast.LENGTH_SHORT).show();
                    ticketsEvent.add(pd.getKey());
                }
                if (!ticketsEvent.isEmpty()) {
                    for (int i = 0; i < ticketsEvent.size(); i++) {
                        mDataExhibition.child(ticketsEvent.get(i)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (final DataSnapshot pd1 : dataSnapshot.getChildren()) {
                                    if (pd1.getKey().toString().equals("Date")) {
                                        customExhibitionDate.add(pd1.getValue().toString());
                                        //   Toast.makeText(getActivity(), Integer.toString(customExhibitionDate.size()), Toast.LENGTH_SHORT).show();
                                    } else if (pd1.getKey().toString().equals("Name")) {
                                        customExhibitionName.add(pd1.getValue().toString());
                                    } else if (pd1.getKey().toString().equals("Venue")) {
                                        customExhibitionVenue.add(pd1.getValue().toString());
                                    } else if (pd1.getKey().toString().equals("Time")) {
                                        customExhibitionTime.add(pd1.getValue().toString());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        for (int j = 0; j < ticketsEvent.size(); j++) {
                            final int finalJ = j;
                            mTicket.child(ticketsEvent.get(j)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    for (final DataSnapshot pd1 : dataSnapshot1.getChildren()) {
                                        mTicket.child(ticketsEvent.get(finalJ)).child(pd1.getKey()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (final DataSnapshot pd : dataSnapshot.getChildren()) {
                                                    if (pd.getKey().equals("Name")) {
                                                        ticketType.add(pd.getValue().toString());
                                                    } else if (pd.getKey().equals("Enters")) {
                                                        entering.add(pd.getValue().toString());
                                                    } else if (pd.getKey().equals("Divided")) {
                                                        divided.add(pd.getValue().toString());
                                                    }
                                                }
                                                // Toast.makeText(getActivity(), Integer.toString(ticketsEvent.size())+Integer.toString(customExhibitionName.size())+Integer.toString(ticketType.size()), Toast.LENGTH_SHORT).show();

                                                if (customExhibitionName.size() == customExhibitionDate.size() && customExhibitionName.size() == ticketType.size() && customExhibitionDate.size() == entering.size() && customExhibitionName.size() == divided.size() && customExhibitionVenue.size() == customExhibitionTime.size() && customExhibitionVenue.size() == divided.size()) {
                                                    items.clear();
                                                    recyclerView.removeAllViews();
                                                    for (int i = 0; i < ticketsEvent.size(); i++) {
                                                        items.add(new TicketModelClass(customExhibitionName.get(i), customExhibitionDate.get(i), entering.get(i), ticketType.get(i), divided.get(i), ticketsEvent.get(i), customExhibitionVenue.get(i), customExhibitionTime.get(i)));
                                                        //         Toast.makeText(getActivity(), customExhibitionName.get(i)+ customExhibitionDate.get(i)+ entering.get(i)+ ticketType.get(i)+ divided.get(i)+ ticketsEvent.get(i), Toast.LENGTH_SHORT).show();
                                                        ticketAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
   final ArrayList<AlertModelClass> items = new ArrayList<>();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final ArrayList<String> tickets=new ArrayList<String>();
        DatabaseReference mTicket=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("Tickets");
        mTicket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    tickets.add(postSnapshot.getKey().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}