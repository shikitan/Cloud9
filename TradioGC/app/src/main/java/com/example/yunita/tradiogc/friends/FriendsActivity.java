package com.example.yunita.tradiogc.friends;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.yunita.tradiogc.SearchController;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.login.LoginController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class FriendsActivity extends AppCompatActivity {
    private SearchController searchController;
    private FriendsController friendsController;
    private EditText add_friend_et;
    private Context context = this;

    private ArrayAdapter<String> friendsViewAdapter;
    private ListView friendList;

    //private Friends friends = LoginActivity.USERLOGIN.getFriends();

    private Friends friends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        searchController = new SearchController(context);
        friendsController = new FriendsController(context);
        add_friend_et = (EditText) findViewById(R.id.add_friend_et);
        friendList = (ListView) findViewById(R.id.friend_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        friends = new Friends();
        friendsViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_list_item, friends);
        friendList.setAdapter(friendsViewAdapter);

        // Delete movie on long click
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

    public void addFriend(View view) {
        String friendname = add_friend_et.getText().toString();
        Thread addThread = new AddThreat(friendname);
        addThread.start();
        Toast toast = Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT);
        toast.show();
    }


    class AddThreat extends Thread {
        private String friendname;
        public AddThreat(String friendname) {
            this.friendname = friendname;
        }
        @Override
        public void run() {
            friendsController.addFriend(friendname);
            Thread updateFrindListThread  = new UpdateFrindListThread();
            updateFrindListThread.start();
            friends.addNewFriend(friendname);
            notifyUpdated();
        }
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





    //ON RESUME----------NEED TEST!!!!!!!!
    @Override
    public void onResume() {
        super.onResume();
        Thread updateFrindListThread  = new UpdateFrindListThread();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }


    public class UpdateFrindListThread extends Thread {
        public UpdateFrindListThread() {}
        @Override
        public void run() {
            Thread refreshUserLoginThread = friendsController.new RefreshUserLoginThread();
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
