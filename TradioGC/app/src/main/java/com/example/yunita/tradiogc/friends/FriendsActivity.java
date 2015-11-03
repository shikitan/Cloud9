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

    private ArrayAdapter<String> friendsViewAdapter;
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

        friendsViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_list_item, thisUserFriends);
        friendList.setAdapter(friendsViewAdapter);

        // Delete movie on long click
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

                // Need to implement this properly after getting add to work
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
                /*
                Friends removedUserFriends = removedUser.getFriends();

                removedUserFriends.deleteFriend(LoginActivity.USERLOGIN);
                */

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    //ON RESUME----------NEED TEST!!!!!!!!
    @Override
    public void onResume() {
        super.onResume();
        Thread updateUserLoginThread  = new UpdateUserLoginThread();
        updateUserLoginThread.start();
        thisUserFriends = LoginActivity.USERLOGIN.getFriends();
        friendsViewAdapter.notifyDataSetChanged();

    }

    public void addFriend(View view) {
        String friendNameET = add_friend_et.getText().toString();

        // Add friend to user's friend list
        ////COMPLETE UPDATE USERLOGIN PART ------NEED TEST!!!!!!!!!!!!!!!
        //TODO test for addFriend
        Thread updateUserLoginThread = new UpdateUserLoginThread();
        updateUserLoginThread.start();
        synchronized (updateUserLoginThread) {
            try {
                // Wait 500ms to search for the username
                updateUserLoginThread.wait(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the user's username to the new friend's friend list
            thisUserFriends = LoginActivity.USERLOGIN.getFriends();
            thisUserFriends.add(friendNameET);
        }

        // Start a thread for getting the User of the friend
        Thread getNameThread = new GetUserNameThread(friendNameET);
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

        System.out.println(friendName);

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

    public class GetUserNameThread extends Thread {
        private String username;

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

    public class UpdateUserLoginThread extends Thread {
        public UpdateUserLoginThread() {}
        @Override
        public void run() {
            synchronized (this) {
                SearchController searchController = new SearchController(context);
                LoginActivity.USERLOGIN = searchController.getUser(LoginActivity.USERLOGIN.getUsername());
                notify();
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
