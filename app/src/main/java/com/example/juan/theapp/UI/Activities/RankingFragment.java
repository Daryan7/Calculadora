package com.example.juan.theapp.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juan.theapp.Data.AppDB;
import com.example.juan.theapp.R;
import com.example.juan.theapp.Domain.Adapters.RankingAdapter;

public class RankingFragment extends Fragment {
    private AppDB dbHelper;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        dbHelper = new AppDB(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView listView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        listView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        listView.setLayoutManager(linearLayoutManager);
        RankingAdapter adapter = new RankingAdapter(dbHelper.getAllUsers());
        dbHelper.close();
        listView.setAdapter(adapter);
        return rootView;
    }
}
