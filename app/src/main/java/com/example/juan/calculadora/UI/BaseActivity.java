package com.example.juan.calculadora.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Comunication.OnFragmentInteractionListener;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
    private Fragment actualFragment;
    private DrawerLayout navDrawer;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.calculator).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        if (savedInstaceState != null) {
            actualFragment = fragmentManager.getFragment(savedInstaceState, "actualFragment");
        }
        else if (actualFragment == null) actualFragment = new CalculatorActivity();

        fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "actualFragment", actualFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calculator: {
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = fragmentManager.findFragmentByTag("c");
                if (actualFragment == null) {
                   actualFragment = new CalculatorActivity();
                }
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment, "c");
                fragmentTransaction.commit();
                navDrawer.closeDrawers();
                return true;
            }
            case R.id.ranking: {
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = fragmentManager.findFragmentByTag("r");
                if (actualFragment == null) {
                    actualFragment = new RankingActivity();
                }
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment, "r");
                fragmentTransaction.commit();
                navDrawer.closeDrawers();
                return true;
            }
        }
        return false;
    }
}
