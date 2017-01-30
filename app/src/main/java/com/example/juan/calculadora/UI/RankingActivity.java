package com.example.juan.calculadora.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juan.calculadora.Data.AppDB;
import com.example.juan.calculadora.R;

public class RankingActivity extends Fragment {
    private AppDB appDB;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        appDB = new AppDB(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_ranking, container, false);

        return rootView;
    }
}
