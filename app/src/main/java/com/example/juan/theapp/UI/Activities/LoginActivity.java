package com.example.juan.theapp.UI.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.juan.theapp.Domain.User;
import com.example.juan.theapp.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "p5Sz8ohkbDBDbnNaV0OvqV0DO";
    private static final String TWITTER_SECRET = "grGai5S2wkTOcZaAieKWWf6T4Xh2YQdw51p71CgkojWn7LyNEY";

    private TwitterLoginButton loginButton;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        final SharedPreferences notificationSettings = getSharedPreferences("users", 0);
        if (notificationSettings.getBoolean("login", false)) {
            long id = notificationSettings.getLong("id", -1);
            User.logIn(getApplicationContext(), id);
            Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {"", ""};
            int hasPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                permissions[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
            }
            hasPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                permissions[1] = Manifest.permission.ACCESS_FINE_LOCATION;
            }
            requestPermissions(permissions, 0);
        }

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                SharedPreferences.Editor editor = notificationSettings.edit();
                editor.putLong("id", session.getUserId());
                editor.putBoolean("login", true);
                editor.apply();
                boolean newUser = User.registerIfNotExist(getApplicationContext(), session.getUserName(), session.getUserId());
                Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                if (newUser) intent.putExtra("tutorial", true);
                startActivity(intent);
                finish();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logIn(getApplicationContext(), 0);
                Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


}
