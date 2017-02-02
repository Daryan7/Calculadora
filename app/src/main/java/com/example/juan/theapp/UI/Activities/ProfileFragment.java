package com.example.juan.theapp.UI.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juan.theapp.Data.AppDB;
import com.example.juan.theapp.Domain.User;
import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.Comunication.OnFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ProfileFragment extends Fragment {

    private ImageView imageView;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri selectedImageUri = data.getData();
                Picasso.with(getContext()).load(selectedImageUri).resize(600, 600).centerCrop().into(imageView);
                User.getCurrentUser().setProfileImage(selectedImageUri);
                mListener.updateUser();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = User.getCurrentUser();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWriteContactsPermission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

        imageView = (ImageView) rootView.findViewById(R.id.profilePic);
        if (user.hasProfilePic()) {
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                // Check for the freshest data.
                getActivity().getContentResolver().takePersistableUriPermission(user.getProfileImage(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            Picasso.with(getContext()).load(user.getProfileImage()).resize(600, 500).centerCrop().into(imageView);
        }

        TextView userName = (TextView)rootView.findViewById(R.id.nickName);
        userName.setText(user.getNickName());

        TextView pointsView = (TextView)rootView.findViewById(R.id.points);
        pointsView.setText(Integer.toString(user.getPoints()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (android.os.Build.VERSION.SDK_INT >= 19) {
                    // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
                    // browser.
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                }
                else {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                }
                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
                // To search for all documents available via installed storage providers,
                // it would be "*/*".
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
