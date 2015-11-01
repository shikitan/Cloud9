package com.example.yunita.tradiogc.friends;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.login.LoginController;

public class FriendsActivity extends AppCompatActivity {

    private FriendsController friendsController;
    private EditText add_friend_et;
    private Context mContext = this;

    private ArrayAdapter<User> friendsViewAdapter;
    private ListView friendList;

    private Friends thisUserFriends = LoginActivity.USERLOGIN.getFriends();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(mContext);

        add_friend_et = (EditText) findViewById(R.id.add_friend_et);

        friendList = (ListView) findViewById(R.id.friend_list_view);
    }

    @Override
    protected void onStart(){
        super.onStart();


        friendsViewAdapter = new ArrayAdapter<User>(this, R.layout.friend_list_item, thisUserFriends);
        friendList.setAdapter(friendsViewAdapter);

        // Delete movie on long click
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User removedUser = thisUserFriends.get(position);
                thisUserFriends.deleteFriend(removedUser);

                Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
                thread.start();

                friendsViewAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "Deleting " + removedUser.getUsername(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    public void addFriend(View view) {
        String friendName = add_friend_et.getText().toString();

        // need to be fixed, search user -> request -> accepted -> add friend
        User newFriend = new User();
        newFriend.setUsername(friendName);

        thisUserFriends.add(newFriend);

        Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
        thread.start();

        Toast toast = Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT);
        toast.show();

        friendsViewAdapter.notifyDataSetChanged();
    }

}
