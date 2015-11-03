package com.example.yunita.tradiogc.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.SearchController;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {
    public static String USERNAME;
    private SearchController searchController;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



    }

    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            TextView userName = (TextView) findViewById(R.id.profileName);
            TextView location = (TextView) findViewById(R.id.profileLocation);
            TextView email = (TextView) findViewById(R.id.profileEmail);
            TextView phone = (TextView) findViewById(R.id.profilePhone);

            userName.setText(user.getUsername());
            location.setText(user.getLocation());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        searchController = new SearchController(this);
        Intent intent = getIntent();



        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                String username = extras.getString(USERNAME);

                Thread thread = new GetThread(username);
                thread.start();
            }
        }


    }

    class GetThread extends Thread {
        private String username;

        public GetThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            user = searchController.getUser(username);
            runOnUiThread(doUpdateGUIDetails);
        }
    }
}
