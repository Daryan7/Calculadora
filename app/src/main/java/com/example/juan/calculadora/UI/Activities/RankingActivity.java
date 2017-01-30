package com.example.juan.calculadora.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juan.calculadora.Data.DBHelper;
import com.example.juan.calculadora.Data.User;
import com.example.juan.calculadora.R;

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
        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(getContext(), android.R.layout.simple_list_item_1, dbHelper.getAllUsers());
        listView.setAdapter(adapter);
        return rootView;
    }
}
