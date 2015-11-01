package com.example.yunita.tradiogc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yunita.tradiogc.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView profile_tv = (TextView) findViewById(R.id.profile_tv);
        profile_tv.setText(LoginActivity.USERLOGIN.getUsername());
    }

}
