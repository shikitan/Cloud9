package com.example.yunita.tradiogc.friends;

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
import com.example.yunita.tradiogc.search.SearchUserActivity;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.profile.ProfileActivity;


public class FriendsActivity extends AppCompatActivity {
    private Context context = this;
    private Friends friends;
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
        friends = new Friends();
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
                Thread deleteThread = new DeleteThread(friendname);
                deleteThread.start();
                Toast.makeText(context, "Deleting " + friendname, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Thread updateFrindListThread = new UpdateFriendListThread();
        updateFrindListThread.start();
    }

    public void notifyUpdated() {
        // Thread to update adapter after an operation
        Runnable doUpdateGUIList = new Runnable() {
            public void run() {
                friendsViewAdapter.notifyDataSetChanged();
            }
        };
        runOnUiThread(doUpdateGUIList);
    }

    // Send to search friend page after clicking "Add Friend" button
    public void searchUser(View view) {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    // Class for going to a friend's profile
    public void viewFriendProfile(String username) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USERNAME, username);
        startActivity(intent);
    }

    class DeleteThread extends Thread {
        private String friendname;

        public DeleteThread(String friendname) {
            this.friendname = friendname;
        }

        @Override
        public void run() {
            friendsController.deleteFriend(friendname);
            friends.remove(friendname);
            notifyUpdated();
        }
    }


    public class UpdateFriendListThread extends Thread {
        public UpdateFriendListThread() {
        }

        @Override
        public void run() {
            Thread refreshUserLoginThread = friendsController.new RefreshFriendsThread();
            refreshUserLoginThread.start();
            synchronized (refreshUserLoginThread) {
                try {
                    refreshUserLoginThread.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                friends.clear();
                friends.addAll(LoginActivity.USERLOGIN.getFriends());
                notifyUpdated();
            }
        }
    }

}
