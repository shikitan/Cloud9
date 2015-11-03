package com.example.yunita.tradiogc.login;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yunita.tradiogc.R;

public class ProfileLayoutTest extends AppCompatActivity {

    private TextView userName;
    private TextView location;
    private TextView email;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_tester);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        userName = (TextView) findViewById(R.id.profileName);
        location = (TextView) findViewById(R.id.profileLocation);
        email = (TextView) findViewById(R.id.profileEmail);
        phone = (TextView) findViewById(R.id.profilePhone);

    }

    @Override
    protected void onStart() {
        super.onStart();

        userName.setText(LoginActivity.USERLOGIN.getUsername());
        location.setText(LoginActivity.USERLOGIN.getLocation());
        email.setText(LoginActivity.USERLOGIN.getEmail());
        phone.setText(LoginActivity.USERLOGIN.getPhone());

    }



}
