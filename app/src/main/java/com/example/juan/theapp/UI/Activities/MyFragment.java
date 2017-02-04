package com.example.juan.theapp.UI.Activities;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.juan.theapp.UI.Comunication.OnFragmentInteractionListener;

public class MyFragment extends Fragment {
    OnFragmentInteractionListener mListener;

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
}
