package com.example.yunita.tradiogc.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.SearchController;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.friends.FriendsController;

public class ProfileActivity extends AppCompatActivity {

    public static String USERNAME;
    private String targetUsername;
    private User user;
    private Friends thisUserFriends = LoginActivity.USERLOGIN.getFriends();

    private SearchController searchController;
    private FriendsController friendsController;
    private Context context = this;

    private LinearLayout myprofile_panel;
    private LinearLayout stranger_panel;
    private LinearLayout friend_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(context);

        myprofile_panel = (LinearLayout) findViewById(R.id.myprofile_button_panel);
        stranger_panel = (LinearLayout) findViewById(R.id.stranger_button_panel);
        friend_panel = (LinearLayout) findViewById(R.id.friend_button_panel);

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
        friendsController = new FriendsController(context);
        searchController = new SearchController(context);
        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                targetUsername = extras.getString(USERNAME);
                Thread thread = new GetThread(targetUsername);
                thread.start();
            }
        }

        // Checks to see if we are getting a username from the intent
        if(!targetUsername.equals(LoginActivity.USERLOGIN.getUsername())){

            Friends thisUserFriends = LoginActivity.USERLOGIN.getFriends();

            // If the username is in the user's friend list, show friend profile view
            if (thisUserFriends.contains(targetUsername)){
                myprofile_panel.setVisibility(View.GONE);
                friend_panel.setVisibility(View.VISIBLE);

                // If not, then show the stranger's profile view
            } else {
                myprofile_panel.setVisibility(View.GONE);
                stranger_panel.setVisibility(View.VISIBLE);
            }
        }
    }




    public void addFriend(View view) {

        // Add friend to user's friend list
        thisUserFriends.add(targetUsername);

        // Start a thread for getting the User of the friend
        Thread getNameThread = new GetThread(targetUsername);
        getNameThread.start();

        synchronized (getNameThread) {
            try {
                // Wait 500ms to search for the username
                getNameThread.wait(500);

                // Add the user's username to the new friend's friend list
                Friends friendsFriends = user.getFriends();
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
            Thread threadFriend = friendsController.new UpdateFriendsThread(user);
            threadFriend.start();
        } catch (Exception error) {
            System.out.println(error);
        }

        Toast toast = Toast.makeText(this, "Friend has been added", Toast.LENGTH_SHORT);
        toast.show();

        finish();

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
