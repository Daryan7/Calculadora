package com.example.juan.theapp.UI.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.juan.theapp.Data.AppDB;
import com.example.juan.theapp.R;
import com.example.juan.theapp.Domain.Adapters.RankingAdapter;
import com.example.juan.theapp.UI.Comunication.OnFragmentInteractionListener;

public class RankingFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RankingAdapter adapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView list = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        list.setLayoutManager(linearLayoutManager);
        AppDB dbHelper = mListener.getDataBase();
        adapter = new RankingAdapter(dbHelper.getAllUsersWithPoints());
        list.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_ranking, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resetRanking:
                AppDB database = mListener.getDataBase();
                database.resetPoints();
                adapter.removeAllData();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
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
