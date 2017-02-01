package com.example.juan.theapp.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.juan.theapp.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Random;

public class MemoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_memory, container, false);

        final int[] resId = {R.drawable.borg, R.drawable.ferengi, R.drawable.klingon, R.drawable.maquis_emblem, R.drawable.reman, R.drawable.starfleet_tuc, R.drawable.ufp_2290c, R.drawable.romulan};

        int numFiles = resId.length;

        ArrayList<Integer> randomArray = new ArrayList<>(numFiles * 2);

        for (int i = 0; i < numFiles * 2; ++i) {
            randomArray.add(i % numFiles);
        }

        Random random = new Random(System.currentTimeMillis());

        /*for (int i = 0; i < count; ++i) {
            ImageView image = (ImageView) gridLayout.getChildAt(i);
            int randNum = random.nextInt(randomArray.size());
            //Picasso.with(getContext()).load(resId[randomArray.get(randNum)]).into(image);
            image.setBackgroundResource(resId[randomArray.get(randNum)]);
            randomArray.remove(randNum);
        }*/
        ImageView image = (ImageView) rootView.findViewById(R.id._1);
        int randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._2);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._3);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._4);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._5);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._6);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._7);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._8);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._9);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._10);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._11);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._12);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._13);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._14);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._15);
        randNum = random.nextInt(randomArray.size());
        image.setImageResource(resId[randomArray.get(randNum)]);
        randomArray.remove(randNum);
        image = (ImageView) rootView.findViewById(R.id._16);
        image.setImageResource(resId[randomArray.get(0)]);

        return rootView;
    }
}
