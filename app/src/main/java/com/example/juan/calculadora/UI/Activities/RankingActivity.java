package com.example.juan.calculadora.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.juan.calculadora.Data.DBHelper;
import com.example.juan.calculadora.Data.User;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Adapters.RankingAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;

public class RankingActivity extends Fragment {
    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        dbHelper = new DBHelper(getContext());
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri selectedImageUri = data.getData();
            }
        }
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_ranking, container, false);

        /*Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, 0);*/

        RecyclerView listView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        listView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        listView.setLayoutManager(linearLayoutManager);
        RankingAdapter adapter = new RankingAdapter(dbHelper.getAllUsers());
        listView.setAdapter(adapter);
        return rootView;
    }
}
