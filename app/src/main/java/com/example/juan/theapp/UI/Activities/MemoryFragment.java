package com.example.juan.theapp.UI.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juan.theapp.Domain.User;
import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.CoolImageFlipper;

import java.util.ArrayList;
import java.util.Random;

public class MemoryFragment extends MyFragment {

    private SparseIntArray idMap;
    private int flippedImage;
    private int correctImages;
    private int moves;

    private CoolImageFlipper flipper;
    private View rootView;
    private ImageView image1, image2;
    private TextView moveView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_memory, container, false);
        moveView = (TextView) rootView.findViewById(R.id.moves);
        flippedImage = 0;
        image1 = image2 = null;
        correctImages = 0;
        moves = 0;
        flipper = new CoolImageFlipper(getContext());

        final int[] resId = {R.drawable.borg, R.drawable.ferengi, R.drawable.klingon, R.drawable.maquis_emblem, R.drawable.reman, R.drawable.starfleet_tuc, R.drawable.ufp_2290c, R.drawable.romulan};
        final int[] imageId = {R.id._1, R.id._2, R.id._3, R.id._4, R.id._5, R.id._6, R.id._7, R.id._8, R.id._9, R.id._10, R.id._11, R.id._12, R.id._13, R.id._14, R.id._15, R.id._16};

        int numFiles = resId.length;

        ArrayList<Integer> randomArray = new ArrayList<>(numFiles * 2);

        for (int i = 0; i < numFiles * 2; ++i) {
            randomArray.add(i % numFiles);
        }

        idMap = new SparseIntArray(numFiles * 2);

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = (ImageView) view;
                if (!(boolean) image.getTag() && flippedImage < 2) {
                    if (image1 == null) image1 = image;
                    else image2 = image;
                    int id = idMap.get(view.getId());
                    image.setTag(true);
                    Drawable drawable;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        drawable = getActivity().getResources().getDrawable(id, null);
                    }
                    else drawable = getActivity().getResources().getDrawable(id);
                    flipper.flipImage(drawable, image);
                    if (++flippedImage == 2) {
                        onMove();
                        if (id == idMap.get(image1.getId())) {
                            flippedImage = 0;
                            image1 = image2 = null;
                            ++correctImages;
                            if (correctImages == 8) onWin();
                        } else {
                            Handler handler = new Handler();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        drawable = getActivity().getResources().getDrawable(R.drawable.star_trek_logo, null);
                                    }
                                    else drawable = getActivity().getResources().getDrawable(R.drawable.star_trek_logo);
                                    flipper.flipImage(drawable, image1);
                                    flipper.flipImage(drawable, image2);
                                    image1.setTag(false);
                                    image2.setTag(false);
                                    image1 = image2 = null;
                                    flippedImage = 0;
                                }
                            };
                            handler.postDelayed(runnable, 1000);
                        }
                    }
                }
            }
        };

        Random random = new Random(System.currentTimeMillis());

        for (int anImageId : imageId) {
            int randNum = random.nextInt(randomArray.size());
            idMap.put(anImageId, resId[randomArray.get(randNum)]);
            ImageView image = (ImageView) rootView.findViewById(anImageId);
            image.setOnClickListener(listener);
            image.setTag(false);
            randomArray.remove(randNum);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.memory, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.memory:
                resetMemory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onMove() {
        ++moves;
        moveView.setText(Integer.toString(moves));
    }

    public void resetMemory() {
        flippedImage = 0;
        image1 = image2 = null;
        correctImages = 0;
        moves = 0;
        moveView.setText(R.string._0);

        final int[] resId = {R.drawable.borg, R.drawable.ferengi, R.drawable.klingon, R.drawable.maquis_emblem, R.drawable.reman, R.drawable.starfleet_tuc, R.drawable.ufp_2290c, R.drawable.romulan};
        final int[] imageId = {R.id._1, R.id._2, R.id._3, R.id._4, R.id._5, R.id._6, R.id._7, R.id._8, R.id._9, R.id._10, R.id._11, R.id._12, R.id._13, R.id._14, R.id._15, R.id._16};

        int numFiles = resId.length;

        ArrayList<Integer> randomArray = new ArrayList<>(numFiles * 2);

        for (int i = 0; i < numFiles * 2; ++i) {
            randomArray.add(i % numFiles);
        }

        idMap = new SparseIntArray(numFiles * 2);

        Random random = new Random(System.currentTimeMillis());

        for (int anImageId : imageId) {
            int randNum = random.nextInt(randomArray.size());
            idMap.put(anImageId, resId[randomArray.get(randNum)]);
            ImageView image = (ImageView) rootView.findViewById(anImageId);
            if ((boolean) image.getTag()) {
                image.setImageResource(R.drawable.star_trek_logo);
                image.setTag(false);
            }
            randomArray.remove(randNum);
        }
    }

    private void onWin() {
        String message = "Points: " + Integer.toString(moves);
        User user = User.getCurrentUser();
        if (moves < user.getPoints() || user.getPoints() < 0) {
            user.setPoints(moves);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mListener.updateUser();
                }
            }).start();
            message += "\nNew Record!";
        }
        new AlertDialog.Builder(getContext())
                .setTitle("You won!")
                .setMessage(message)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetMemory();
                    }
                })
                .show();
    }
}
