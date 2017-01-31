package com.example.juan.theapp.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.juan.theapp.Domain.User;
import com.example.juan.theapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText loginText;
    private EditText passwordText;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_login);

        loginText = (EditText)findViewById(R.id.loginText);
        passwordText = (EditText)findViewById(R.id.passwordText);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logIn(getApplicationContext(), loginText.getText().toString(), passwordText.getText().toString());
                Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
