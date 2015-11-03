package com.example.yunita.tradiogc.friends;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.SearchController;
import com.example.yunita.tradiogc.SearchUserActivity;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.friends.FriendsController;
import com.example.yunita.tradiogc.login.LoginActivity;

public class FriendMainActivity extends AppCompatActivity {

    private ListView friendList;
    private FriendsController friendsController;
    private SearchController searchController;
    private Context mContext = this;
    private ArrayAdapter<String> friendsViewAdapter;

    private Friends thisUserFriends;

    public User friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(mContext);
        searchController = new SearchController(mContext);

        friendList = (ListView) findViewById(R.id.friend_list_view);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Execute the thread
        Thread thread = searchController.new GetUserLoginThread( LoginActivity.USERLOGIN.getUsername());
        thread.start();

        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thisUserFriends = LoginActivity.USERLOGIN.getFriends();

        // Show list of friends
        friendsViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_list_item, thisUserFriends);
        friendList.setAdapter(friendsViewAdapter);

        // Delete friends on long click
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String removedUser = thisUserFriends.get(position);
                thisUserFriends.deleteFriend(removedUser);

                try {
                    Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
                    thread.start();
                } catch (Exception error) {
                    System.out.println(error);
                }

                // Start a new thread for finding the User of the removed friend
                Thread getNameThread = new GetUserNameThread(removedUser);
                getNameThread.start();

                synchronized (getNameThread) {
                    try {
                        // Wait 500ms to search for the username
                        getNameThread.wait(500);

                        // Add the user's username to the new friend's friend list
                        Friends friendsFriends = friendName.getFriends();
                        friendsFriends.deleteFriend(LoginActivity.USERLOGIN.getUsername());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread threadRemoved = friendsController.new UpdateFriendsThread(friendName);
                    threadRemoved.start();
                } catch (Exception error) {
                    System.out.println(error);
                }

                friendsViewAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "Deleting " + removedUser, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

    // Send to search friend page after clicking "Add Friend" button
    public void addFriend(View view) {

        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);

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
