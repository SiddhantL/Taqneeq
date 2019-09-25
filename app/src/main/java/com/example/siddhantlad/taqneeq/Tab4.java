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
import android.widget.Toast;

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
public class Tab4 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String winner1;
    private String winner2;
    private String winner3;
    private OnFragmentInteractionListener mListener;

    public Tab4() {
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
    public static Tab4 newInstance(String param1, String param2) {
        Tab4 fragment = new Tab4();
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
        winner1=new String();
        winner2=new String();
        winner3=new String();
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final ArrayList<WinnerModelClass> items = new ArrayList<>();
        final CustomWinnerAdapter adapter = new CustomWinnerAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);
        final DatabaseReference mData=FirebaseDatabase.getInstance().getReference("winners");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    items.clear();
                    final String eventname = postSnapshot.getKey().toString().trim();
                    final DatabaseReference dataref = mData.child(eventname);
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                           for (final DataSnapshot postSnapshot : dataSnapshot2.getChildren()) {
                               if (dataSnapshot2.getValue() != null) {
                                   String alert = postSnapshot.getValue().toString().trim();
                                  // Toast.makeText(getActivity(), postSnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                                   if (postSnapshot.getKey().toString().equals("Winner1")) {
                                       winner1=postSnapshot.getValue().toString();
                                       if (dataSnapshot2.getChildrenCount()==2) {
                                           items.add(new WinnerModelClass(R.drawable.testeventimage1, eventname, "CR-101", 20, winner1, "", ""));
                                           adapter.notifyDataSetChanged();
                                       }
                                   }else if (postSnapshot.getKey().toString().equals("Winner2")) {
                                       winner2=postSnapshot.getValue().toString();
                                       if (dataSnapshot2.getChildrenCount()==4) {
                                           items.add(new WinnerModelClass(R.drawable.testeventimage1, eventname, "CR-101", 20, winner1, winner2, ""));
                                           adapter.notifyDataSetChanged();
                                       }
                                   }else if (postSnapshot.getKey().toString().equals("Winner3")) {
                                       winner3=postSnapshot.getValue().toString();
                                       if (dataSnapshot2.getChildrenCount()==6) {
                                           items.add(new WinnerModelClass(R.drawable.testeventimage1, eventname, "CR-101", 20,winner1, winner2, winner3));
                                           adapter.notifyDataSetChanged();
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
