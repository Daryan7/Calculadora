package com.example.juan.theapp.UI.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.juan.theapp.Data.AppDB;
import com.example.juan.theapp.Domain.User;
import com.example.juan.theapp.R;
import com.example.juan.theapp.Services.MusicService;
import com.example.juan.theapp.UI.Comunication.OnFragmentInteractionListener;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
    private Fragment actualFragment;
    private DrawerLayout navDrawer;
    private MenuItem menuItem;
    private AppDB database;
    private static boolean tutorialDone = false;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_base);

        if (getIntent().getBooleanExtra("tutorial", false) && !tutorialDone) {
            final int[] i = {0};
            final String[] titles = getResources().getStringArray(R.array.tutorial_titles);
            final String[] content = getResources().getStringArray(R.array.tutorial_content);
            final int[] imageIds = {R.mipmap.ic_launcher, R.drawable.ic_profile, R.drawable.ic_calculator, R.drawable.ic_game, R.drawable.ic_ranking, R.drawable.ic_player, R.mipmap.ic_launcher};
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(titles[0]);
            if (imageIds[0] != -1) builder.setIcon(imageIds[0]);
            builder.setMessage(content[0]);
            builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ++i[0];
                    if (i[0] < titles.length) {
                        builder.setTitle(titles[i[0]]);
                        builder.setMessage(content[i[0]]);
                        builder.setIcon(imageIds[i[0]]);
                        builder.show();
                    }
                }
            });

            builder.show();
            tutorialDone = true;
        }

        database = new AppDB(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.nickView)).setText(User.getCurrentUser().getNickName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        int menuId;
        if (savedInstaceState != null) {
            actualFragment = fragmentManager.getFragment(savedInstaceState, "actualFragment");
            menuId = savedInstaceState.getInt("menuId");
            menuItem = navigationView.getMenu().findItem(menuId);
        }
        else if (actualFragment == null) {
            Intent intent = getIntent();
            if (intent.getBooleanExtra("musicPlayer", false)) {
                menuId = R.id.musicPlayer;
                actualFragment = new SongPlayerFragment();
            }
            else {
                menuId = R.id.profile;
                actualFragment = new ProfileFragment();
            }
            menuItem = navigationView.getMenu().findItem(menuId);
        }
        menuItem.setChecked(true);
        fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "actualFragment", actualFragment);
        outState.putInt("menuId", menuItem.getItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calculator: {
                navDrawer.closeDrawers();
                if (actualFragment instanceof CalculatorFragment) return false;
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = new CalculatorFragment();
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
                fragmentTransaction.commit();
                return true;
            }
            case R.id.ranking: {
                navDrawer.closeDrawers();
                if (actualFragment instanceof RankingFragment) return false;
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = new RankingFragment();
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
                fragmentTransaction.commit();
                return true;
            }
            case R.id.profile: {
                navDrawer.closeDrawers();
                if (actualFragment instanceof ProfileFragment) return false;
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
                fragmentTransaction.commit();
                return true;
            }
            case R.id.game: {
                navDrawer.closeDrawers();
                if (actualFragment instanceof MemoryFragment) return false;
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = new MemoryFragment();
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
                fragmentTransaction.commit();
                return true;
            }
            case R.id.musicPlayer: {
                navDrawer.closeDrawers();
                if (actualFragment instanceof SongPlayerFragment) return false;
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                actualFragment = new SongPlayerFragment();
                fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
                fragmentTransaction.commit();
                return true;
            }
            case R.id.logOut: {
                User.logOut();
                SharedPreferences notificationSettings = getSharedPreferences("users", 0);
                SharedPreferences.Editor editor = notificationSettings.edit();
                editor.putBoolean("login", false);
                editor.apply();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                if (actualFragment instanceof SongPlayerFragment)
                    ((SongPlayerFragment) actualFragment).unBind();
                Intent intent1 = new Intent(this, MusicService.class);
                stopService(intent1);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!(actualFragment instanceof ProfileFragment)) {
            menuItem = ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.profile);
            menuItem.setChecked(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            actualFragment = new ProfileFragment();
            fragmentTransaction.replace(R.id.frame_layout_base, actualFragment);
            fragmentTransaction.commit();
        }
        else super.onBackPressed();
    }

    @Override
    public void updateUser() {
        database.updateUser(User.getCurrentUser());
    }

    @Override
    public AppDB getDataBase() {
        return database;
    }

    @Override
    public boolean checkPermissions(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = checkSelfPermission(permission);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
