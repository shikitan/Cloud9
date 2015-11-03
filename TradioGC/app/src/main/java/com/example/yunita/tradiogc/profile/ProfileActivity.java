package com.example.yunita.tradiogc.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.SearchController;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.friends.*;
import com.example.yunita.tradiogc.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {
    public static String USERNAME;
    private String targetUsername;
    private SearchController searchController;
    private User user;
    private Friends thisUserFriends;

    private LinearLayout myprofile_panel;
    private LinearLayout stranger_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        myprofile_panel = (LinearLayout) findViewById(R.id.myprofile_button_panel);
        stranger_panel = (LinearLayout) findViewById(R.id.stranger_button_panel);

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
                targetUsername = extras.getString(USERNAME);

                Thread thread = new GetThread(targetUsername);
                thread.start();
            }
        }

        if(!targetUsername.equals(LoginActivity.USERLOGIN.getUsername())){
            myprofile_panel.setVisibility(View.GONE);
            stranger_panel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    public void addFriend(View view) {
        // Add friend to user's friend list
        thisUserFriends.add(targetUsername);

        // Start a thread for getting the User of the friend
        Thread getNameThread = new GetUserNameThread(targetUsername);
        getNameThread.start();

        synchronized (getNameThread) {
            try {
                // Wait 500ms to search for the username
                getNameThread.wait(500);

                // Add the user's username to the new friend's friend list
                Friends friendsFriends = friendName.getFriends();
                friendsFriends.addNewFriend(LoginActivity.USERLOGIN.getUsername());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
            thread.start();
        } catch (Exception error) {
            System.out.println(error);
        }


        try {
            Thread threadFriend = friendsController.new UpdateFriendsThread(friendName);
            threadFriend.start();
        } catch (Exception error) {
            System.out.println(error);
        }

        friendsViewAdapter.notifyDataSetChanged();
        Toast toast = Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT);
        toast.show();

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

    // Class for searching for a username
    public class GetUserNameThread extends Thread {
        private String username;

        public GetUserNameThread(String username) {
            this.username = username;
        }
        @Override
        public void run() {
            synchronized (this) {
                SearchController searchController = new SearchController(mContext);
                friendName = searchController.getUser(username);
            }
        }
    }

}
