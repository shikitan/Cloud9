package com.example.yunita.tradiogc.friends;

import android.content.Context;
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
import java.util.concurrent.CountDownLatch;

public class FriendsActivity extends AppCompatActivity {

    private FriendsController friendsController;
    private EditText add_friend_et;
    private Context mContext = this;
    private Context context;

    private ArrayAdapter<User> friendsViewAdapter;
    private ListView friendList;

    private Friends thisUserFriends = LoginActivity.USERLOGIN.getFriends();

    public User friendName;

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
    protected void onStart() {
        super.onStart();


        friendsViewAdapter = new ArrayAdapter<User>(this, R.layout.friend_list_item, thisUserFriends);
        friendList.setAdapter(friendsViewAdapter);

        // Delete movie on long click
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User removedUser = thisUserFriends.get(position);
                thisUserFriends.deleteFriend(removedUser);

                try {
                    Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
                    thread.start();
                } catch (Exception error) {
                    System.out.println(error);
                }

                Friends removedUserFriends = removedUser.getFriends();
                removedUserFriends.deleteFriend(LoginActivity.USERLOGIN);
                try {
                    Thread threadRemoved = friendsController.new UpdateFriendsThread(removedUser);
                    threadRemoved.start();
                } catch (Exception error) {
                    System.out.println(error);
                }

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
        String friendNameET = add_friend_et.getText().toString();
        final CountDownLatch latch = new CountDownLatch(1);

        //new GetUserNameThread().execute(friendNameET);


        //saveFriendList(LoginActivity.USERLOGIN, friendName);
        // need to be fixed, search user -> request -> accepted -> add friend

        // Start a new thread for searching and adding users


        Thread getNameThread = new GetUserNameThread(friendNameET);
        getNameThread.start();

        synchronized (getNameThread) {
            try {
                // Wait 500ms to search for the user name
                getNameThread.wait(500);

                // Add friend to the user's friend list
                thisUserFriends.addNewFriend(friendName);

                // Open the new friend's friend list and add user
                Friends newFriendList = friendName.getFriends();
                newFriendList.addNewFriend(LoginActivity.USERLOGIN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            //SearchController searchController = new SearchController(context);
            //User newFriend = searchController.getUser(friendName);
        //final CountDownLatch latch = new CountDownLatch(1);


        /*
        try {
            Thread.sleep(1000);
        } catch (Exception e){
            System.out.println(e);
        }
        System.out.println(friendName);
        */
        /*
        try {
            //latch.await();
            Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
            //thread.join(500);
            thread.start();
            //thread.join(500);
        } catch (Exception error) {
            System.out.println(error);
        }
        */
        /*
        try {
            Thread threadFriend = friendsController.new UpdateFriendsThread(friendName);
            threadFriend.start();
        } catch (Exception error) {
            System.out.println(error);
        }
        */
        friendsViewAdapter.notifyDataSetChanged();
        Toast toast = Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT);
        toast.show();
    }

    public class GetUserNameThread extends Thread {
        private String username;
        //private User user;

        public GetUserNameThread(String username) {
            this.username = username;
        }
        @Override
        public void run() {
            synchronized (this) {
                SearchController searchController = new SearchController(context);
                friendName = searchController.getUser(username);

            }
        }

    }


    /*
    public void saveFriendList(User user){
        try {
            //latch.await();
            System.out.println(user);
            Thread thread = friendsController.new UpdateFriendsThread(user);

            thread.start();
            //thread.join(500);
        } catch (Exception error) {
            System.out.println(error);
        }
        //friendsViewAdapter.notifyDataSetChanged();
        //Toast toast = Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT);
        //toast.show();
    }
    */
    /*
    public class GetUserNameThread extends AsyncTask<String, Void, User> {


        protected User doInBackground(String... params) {
            try {
                SearchController searchController = new SearchController(context);
                friendName = searchController.getUser(params[0]);
                thisUserFriends.addNewFriend(friendName);

                // Open the new friend's friend list and add user
                Friends newFriendList = friendName.getFriends();
                newFriendList.addNewFriend(LoginActivity.USERLOGIN);

                //Thread thread = friendsController.new UpdateFriendsThread(LoginActivity.USERLOGIN);
                //thread.start();

                //Thread threadFriend = friendsController.new UpdateFriendsThread(friendName);
                //threadFriend.start();

            } catch (Exception e) {
            }
            return friendName;
        }

        @Override
        protected void onPostExecute(User result) {
            friendName = result;
            //saveFriendList(LoginActivity.USERLOGIN);
            //friendsViewAdapter.notifyDataSetChanged();
        }
    }
    */

}
