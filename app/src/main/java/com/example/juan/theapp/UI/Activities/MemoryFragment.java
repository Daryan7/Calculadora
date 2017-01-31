package com.example.juan.theapp.UI.Activities;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.juan.theapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MemoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_memory, container, false);
        GridLayout gridLayout = (GridLayout)rootView.findViewById(R.id.grid);
        int count = gridLayout.getChildCount();

        AssetManager manager = getActivity().getAssets();

        String[] files;
        try {
            files = manager.list("memory");
        } catch (IOException e) {
            e.printStackTrace();
            return rootView;
        }

        int numFiles = files.length;

        ArrayList<Integer> randomArray = new ArrayList<>(numFiles*2);

        for (int i = 0; i < numFiles*2; ++i) {
            randomArray.add(i%numFiles);
        }

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < count; ++i) {
            ImageView image = (ImageView) gridLayout.getChildAt(i);
            int randNum = random.nextInt(randomArray.size());
            try {
                // get input stream
                InputStream ims = manager.open(files[randomArray.get(randNum)]);
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                image.setImageDrawable(d);
            }
            catch(IOException exception) {
                exception.printStackTrace();
                return rootView;
            }
            randomArray.remove(randNum);
        }

        return rootView;
    }
}
