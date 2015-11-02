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
import com.example.yunita.tradiogc.SearchUserActivity;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.friends.FriendsController;
import com.example.yunita.tradiogc.login.LoginActivity;

public class FriendMainActivity extends AppCompatActivity {

    private ListView friendList;

    private FriendsController friendsController;
    private Context mContext = this;
    private ArrayAdapter<String> friendsViewAdapter;

    private Friends thisUserFriends = LoginActivity.USERLOGIN.getFriends();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(mContext);
        friendList = (ListView) findViewById(R.id.friend_list_view);


    }

    @Override
    protected void onStart(){
        super.onStart();
        
        friendsViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_list_item, thisUserFriends);
        friendList.setAdapter(friendsViewAdapter);

        // Delete movie on long click
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String removedUser = thisUserFriends.get(position);
                thisUserFriends.deleteFriend(removedUser);

                Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
                thread.start();

                friendsViewAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "Deleting " + removedUser, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

    public void addFriend(View view) {

        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);

    }
}
