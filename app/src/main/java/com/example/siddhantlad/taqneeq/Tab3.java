package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
 * {@link Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<ModelClass2> items2;
    RecyclerView recyclerView;
int s1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CustomAdapter3 adapter2;
    DatabaseReference mDataExhibition,mSaved;
    private OnFragmentInteractionListener mListener;
    ArrayList<String> saved;
    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
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
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        //--------------------------------------------------------------------------------
        saved = new ArrayList<String>();
        mDataExhibition = FirebaseDatabase.getInstance().getReference("exhibitions");
        items2 = new ArrayList<>();
        recyclerView = view.findViewById(R.id.my_recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter2 = new CustomAdapter3(getActivity(), items2);
        recyclerView.setAdapter(adapter2);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mSaved = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("Saved");
        mSaved.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot pd : dataSnapshot.getChildren()) {
                    saved.add(pd.getKey().toString());
                    s1 = saved.size();
                }
                    if (!saved.isEmpty()) {
                        for (int i = 0; i < saved.size(); i++) {
                            mDataExhibition.child(saved.get(i)).addValueEventListener(new ValueEventListener() {
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
                                    items2.add(new ModelClass2(data));
                                    adapter2.notifyDataSetChanged();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
