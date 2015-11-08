package com.example.yunita.tradiogc.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.friends.FriendsController;
import com.example.yunita.tradiogc.inventory.InventoryActivity;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;

public class ProfileActivity extends AppCompatActivity {

    public static String USERNAME;
    private String targetUsername;
    private User user;
    private Friends friends = LoginActivity.USERLOGIN.getFriends();

    private UserController userController;
    private FriendsController friendsController;
    private Context context = this;

    private LinearLayout myprofile_panel;
    private LinearLayout stranger_panel;
    private LinearLayout friend_panel;
    private ImageButton edit_button;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(context);
        myprofile_panel = (LinearLayout) findViewById(R.id.myprofile_button_panel);
        stranger_panel = (LinearLayout) findViewById(R.id.stranger_button_panel);
        friend_panel = (LinearLayout) findViewById(R.id.friend_button_panel);
        edit_button = (ImageButton) findViewById(R.id.edit_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        friendsController = new FriendsController(context);
        userController = new UserController(context);
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
        if (!targetUsername.equals(LoginActivity.USERLOGIN.getUsername())) {

            Friends friends = LoginActivity.USERLOGIN.getFriends();

            // If the username is in the user's friend list, show friend profile view
            if (friends.contains(targetUsername)) {
                myprofile_panel.setVisibility(View.GONE);
                edit_button.setVisibility(View.GONE);
                friend_panel.setVisibility(View.VISIBLE);

                // If not, then show the stranger's profile view
            } else {
                myprofile_panel.setVisibility(View.GONE);
                edit_button.setVisibility(View.GONE);
                stranger_panel.setVisibility(View.VISIBLE);
            }
        }
    }

    public void goToInventory(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("user_inventory", targetUsername);
        startActivity(intent);
    }

    public void addFriend(View view) {
        friendsController.addFriend(targetUsername);
        finish();
    }

    public void editProfile(View view) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        startActivity(intent);
    }


    class GetThread extends Thread {
        private String username;

        public GetThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            user = userController.getUser(username);
            runOnUiThread(doUpdateGUIDetails);
        }
    }
}
