package com.example.juan.theapp.UI.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ProfileFragment extends Fragment {

    private ImageView imageView;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri selectedImageUri = data.getData();
                Picasso.with(getContext()).load(selectedImageUri).resize(600, 500).centerCrop().into(imageView);
                User.getCurrentUser().setProfileImage(selectedImageUri);
                mListener.updateUser();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = User.getCurrentUser();

        imageView = (ImageView) rootView.findViewById(R.id.profilePic);
        if (user.hasProfilePic()) {
            Picasso.with(getContext()).load(user.getProfileImage()).resize(600, 500).centerCrop().into(imageView);
        }

        TextView userName = (TextView)rootView.findViewById(R.id.nickName);
        userName.setText(user.getNickName());

        TextView pointsView = (TextView)rootView.findViewById(R.id.points);
        pointsView.setText(Integer.toString(user.getPoints()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, 0);
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
