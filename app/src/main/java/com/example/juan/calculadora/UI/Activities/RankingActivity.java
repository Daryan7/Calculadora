package com.example.juan.calculadora.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.juan.calculadora.Data.DBHelper;
import com.example.juan.calculadora.Data.User;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Adapters.RankingAdapter;

public class RankingActivity extends Fragment {
    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_ranking, container, false);

        RecyclerView listView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        listView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        listView.setLayoutManager(linearLayoutManager);
        RankingAdapter adapter = new RankingAdapter(dbHelper.getAllUsers());
        listView.setAdapter(adapter);
        return rootView;
    }
}
