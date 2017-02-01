package com.example.juan.theapp.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.juan.theapp.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MemoryFragment extends Fragment {

    private SparseIntArray idMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_memory, container, false);

        final int[] resId = {R.drawable.borg, R.drawable.ferengi, R.drawable.klingon, R.drawable.maquis_emblem, R.drawable.reman, R.drawable.starfleet_tuc, R.drawable.ufp_2290c, R.drawable.romulan};
        final int[] imageId = {R.id._1, R.id._2, R.id._3, R.id._4, R.id._5, R.id._6, R.id._7, R.id._8, R.id._9, R.id._10, R.id._11, R.id._12, R.id._13, R.id._14, R.id._15, R.id._16};

        int numFiles = resId.length;

        ArrayList<Integer> randomArray = new ArrayList<>(numFiles * 2);

        for (int i = 0; i < numFiles * 2; ++i) {
            randomArray.add(i % numFiles);
        }

        idMap = new SparseIntArray(numFiles*2);

        Random random = new Random(System.currentTimeMillis());

        for (int anImageId : imageId) {
            int randNum = random.nextInt(randomArray.size());
            idMap.put(anImageId, resId[randomArray.get(randNum)]);
            rootView.findViewById(anImageId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView image = (ImageView) view;
                    image.setImageResource(idMap.get(view.getId()));
                }
            });
            randomArray.remove(randNum);
        }

        return rootView;
    }
}
