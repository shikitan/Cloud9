package com.example.yunita.tradiogc.friends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.profile.ProfileActivity;


public class FriendsActivity extends AppCompatActivity {

    private Context context = this;
    private Friends friends = LoginActivity.USERLOGIN.getFriends();
    private ListView friendList;
    private FriendsController friendsController;
    private ArrayAdapter<String> friendsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(context);
        friendList = (ListView) findViewById(R.id.friend_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        friendsViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_list_item, friends);
        friendList.setAdapter(friendsViewAdapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = friends.get(position);
                viewFriendProfile(username);
            }
        });
        // Delete friends on long click
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String friendname = friends.get(position);
                Thread deleteThread = new DeleteFriendThread(friendname);
                deleteThread.start();
                Toast.makeText(context, "Deleting " + friendname, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        friends = LoginActivity.USERLOGIN.getFriends();
    }

    // Send to search friend page after clicking "Add Friend" button
    public void searchUser(View view) {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    // go to a friend's profile
    public void viewFriendProfile(String username) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USERNAME, username);
        startActivity(intent);
    }

    class DeleteFriendThread extends Thread {
        private String friendname;

        public DeleteFriendThread(String friendname) {
            this.friendname = friendname;
        }

        @Override
        public void run() {
            friendsController.deleteFriend(friendname);
            friends.remove(friendname);
            runOnUiThread(doUpdateGUIDetails);
        }
    }

    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            friendsViewAdapter.clear();
            friendsViewAdapter.addAll(friends);
            friendsViewAdapter.notifyDataSetChanged();
        }
    };

}
