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
import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends MyFragment {

    private ImageView imageView;
    private View rootView;

    private List<Address> addressList;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            setProfilePic(selectedImageUri);
            User.getCurrentUser().setProfileImage(selectedImageUri);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mListener.updateUser();
                }
            }).start();
        }
    }

    private void printLocation(Location location) {
        Geocoder gc = new Geocoder(getContext());
        try {
            addressList = gc.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 5);
        } catch (IOException e) {
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
    }

    public void setGPSLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            Log.v("2", "Found last location");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    printLocation(location);
                }
            }).start();
        }
        else {
            Log.v("3", "Not found location");
            locationListener = new LocationListener() {
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
                public void onLocationChanged(final Location location) {
                    locationManager.removeUpdates(this);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {printLocation(location);
                        }
                    }).start();
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void setProfilePic(Uri picUri) {
        Picasso.with(getContext()).load(picUri).resize(600, 600).centerCrop().into(imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = User.getCurrentUser();
        boolean hasReadPermission = mListener.checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE);

        imageView = (ImageView) rootView.findViewById(R.id.profilePic);
        if (user.hasProfilePic() && hasReadPermission) {
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    getActivity().getContentResolver().takePersistableUriPermission(user.getProfileImage(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                setProfilePic(user.getProfileImage());
            }
            catch (SecurityException exception) {
                user.setProfileImage(null);
                Toast.makeText(getContext(), "Unfortunately, your profile picture can't be displayed", Toast.LENGTH_LONG).show();
            }
        }

        if (mListener.checkPermissions(Manifest.permission.ACCESS_FINE_LOCATION)) {
            setGPSLocation();
        }

        TextView userName = (TextView) rootView.findViewById(R.id.nickName);
        userName.setText(user.getNickName());

        TextView pointsView = (TextView) rootView.findViewById(R.id.points);
        if (user.getPoints() >= 0) {
            pointsView.setText(Integer.toString(user.getPoints()));
        }
        else {
            pointsView.setText(R.string.has_not_played);
            rootView.findViewById(R.id.movesWord).setVisibility(View.GONE);
        }
        if (hasReadPermission) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (Build.VERSION.SDK_INT >= 19) {
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    }
                    else intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, 0);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener.checkPermissions(Manifest.permission.ACCESS_FINE_LOCATION) && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
