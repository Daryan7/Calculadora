package com.example.juan.theapp.UI.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.theapp.Domain.User;
import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.Comunication.OnFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ImageView imageView;
    private OnFragmentInteractionListener mListener;
    private View rootView;

    private List<Address> addressList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri selectedImageUri = data.getData();
                Picasso.with(getContext()).load(selectedImageUri).resize(600, 600).centerCrop().into(imageView);
                User.getCurrentUser().setProfileImage(selectedImageUri);
                new Runnable() {
                    @Override
                    public void run() {
                        mListener.updateUser();
                    }
                }.run();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setGPSLocation();
                }
            }).start();
        }
    }

    private void printLocation(Location location) {
        Geocoder gc = new Geocoder(getContext());
        try {
            addressList = gc.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 5);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String addressString = "";
        for (int i = 0; i < addressList.size(); ++i) {
            if (i != 0) addressString += "\n";
            addressString += addressList.get(i).getAddressLine(0);
        }
        final TextView t = (TextView) rootView.findViewById(R.id.currentPosition);
        final String finalAddressString = addressString;
        t.post(new Runnable() {
            @Override
            public void run() {
                t.setText(finalAddressString);
            }
        });
        Log.v("LOG", ((Double) location.getLatitude()).toString());
    }

    public void setGPSLocation() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            printLocation(location);
        }
        else {
            LocationListener lis = new LocationListener() {
                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }

                @Override
                public void onLocationChanged(Location location) {
                    printLocation(location);
                }
            };
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lis);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = User.getCurrentUser();

        imageView = (ImageView) rootView.findViewById(R.id.profilePic);
        if (user.hasProfilePic()) {
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    getActivity().getContentResolver().takePersistableUriPermission(user.getProfileImage(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                Picasso.with(getContext()).load(user.getProfileImage()).resize(600, 600).centerCrop().into(imageView);
            } catch (SecurityException exception) {
                user.setProfileImage(null);
                Toast.makeText(getContext(), "Unfortunately, your profile picture can't be displayed", Toast.LENGTH_LONG).show();
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setGPSLocation();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                thread.start();
            }
        } else thread.start();

        TextView userName = (TextView) rootView.findViewById(R.id.nickName);
        userName.setText(user.getNickName());

        TextView pointsView = (TextView) rootView.findViewById(R.id.points);
        if (user.getPoints() >= 0) {
            pointsView.setText(Integer.toString(user.getPoints()));
        } else {
            pointsView.setText(R.string.has_not_played);
            rootView.findViewById(R.id.movesWord).setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (android.os.Build.VERSION.SDK_INT >= 19) {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                } else intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
