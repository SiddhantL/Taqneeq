package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

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
        final Button b1=(Button)view.findViewById(R.id.button5);
        b1.setBackgroundResource(R.color.selectedButton);
        final Button b2=(Button)view.findViewById(R.id.button6);
        final Button b3=(Button)view.findViewById(R.id.button7);
        final ImageView img=(ImageView)view.findViewById(R.id.imageView5);
      /*  b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                // this will be called whenever user click anywhere in Fragment
                b1.setBackgroundResource(R.color.selectedButton);
                b2.setBackgroundResource(R.color.unselectedButton);
                b3.setBackgroundResource(R.color.unselectedButton);
                Drawable drawables=getResources().getDrawable(R.drawable.tt3);
                img.setImageDrawable(drawables);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                // this will be called whenever user click anywhere in Fragment
                b2.setBackgroundResource(R.color.selectedButton);
                b1.setBackgroundResource(R.color.unselectedButton);
                b3.setBackgroundResource(R.color.unselectedButton);
                Drawable drawables=getResources().getDrawable(R.drawable.tt2);
                img.setImageDrawable(drawables);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                // this will be called whenever user click anywhere in Fragment
                b3.setBackgroundResource(R.color.selectedButton);
                b1.setBackgroundResource(R.color.unselectedButton);
                b2.setBackgroundResource(R.color.unselectedButton);
                Drawable drawables=getResources().getDrawable(R.drawable.tt1);
                img.setImageDrawable(drawables);
            }
        });*/
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                // this will be called whenever user click anywhere in Fragment
                b1.setBackgroundResource(R.color.selectedButton);
                b2.setBackgroundResource(R.color.unselectedButton);
                b3.setBackgroundResource(R.color.unselectedButton);
                Drawable drawables=getResources().getDrawable(R.drawable.tt1);
                img.setImageDrawable(drawables);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                // this will be called whenever user click anywhere in Fragment
                b1.setBackgroundResource(R.color.unselectedButton);
                b2.setBackgroundResource(R.color.selectedButton);
                b3.setBackgroundResource(R.color.unselectedButton);
                Drawable drawables=getResources().getDrawable(R.drawable.tt2);
                img.setImageDrawable(drawables);
           //     Toast.makeText(getActivity(), "To Be Decided", Toast.LENGTH_SHORT).show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                // this will be called whenever user click anywhere in Fragment
                b1.setBackgroundResource(R.color.unselectedButton);
                b2.setBackgroundResource(R.color.unselectedButton);
                b3.setBackgroundResource(R.color.selectedButton);
                Drawable drawables=getResources().getDrawable(R.drawable.tt3);
                img.setImageDrawable(drawables);
            //    Toast.makeText(getActivity(), "To Be Decided", Toast.LENGTH_SHORT).show();
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
