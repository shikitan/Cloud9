package com.example.yunita.tradiogc.friends;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.profile.ProfileActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;
import com.example.yunita.tradiogc.user.Users;

public class SearchUserActivity extends AppCompatActivity {
    private Users users;
    private ListView userList;
    private ArrayAdapter<User> usersViewAdapter;
    private UserController userController;
    private EditText editText1;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends_search_results);

        userList = (ListView) findViewById(R.id.friendsSearchList);
        editText1 = (EditText) findViewById(R.id.search_by_username_et);
    }

    /**
     * Sets up the users view adapter and manipulate the list view
     * according to user query.
     * <p>This method runs search thread. While the thread is running, it
     * search for all or specific user in webserver and return the list of user.
     * While the user clicks on the username, it sends the user to that username's
     * profile page.
     */
    @Override
    protected void onStart() {
        super.onStart();

        users = new Users();
        usersViewAdapter = new ArrayAdapter<User>(this, R.layout.friend_list_item, users);
        userList.setAdapter(usersViewAdapter);
        userController = new UserController(mContext);
        SearchThread thread = new SearchThread("");
        thread.start();
        editText1.addTextChangedListener(new DelayedTextWatcher(500) {

            @Override
            public void afterTextChangedDelayed(Editable s) {
                users.clear();
                SearchThread thread = new SearchThread(s.toString());
                thread.start();

            }
        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String username = users.get(pos).getUsername();
                startProfileActivity(username);
                finish();
            }

        });

    }

    /**
     * Called when the user clicks username in search list view.
     * <p>This method is used to send user to that username's profile page.
     *
     * @param username the person name that this user want to track.
     */
    public void startProfileActivity(String username) {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USERNAME, username);
        startActivity(intent);
    }

    /**
     * Called after the user search list is updated and while search
     * thread is running.
     * <p>this method notifies the View if there is a change in user search list.
     */
    public void notifyUpdated() {
        // Thread to update adapter after an operation
        Runnable doUpdateGUIList = new Runnable() {
            public void run() {
                usersViewAdapter.notifyDataSetChanged();
            }
        };
        runOnUiThread(doUpdateGUIList);
    }

    /**
     * Called when the user attempts to search for a specific user.
     * <p>This class creates a thread and runs "Search User" in webserver.
     * While it is running, it updates the user search list and notifies
     * the view for the change.
     */
    class SearchThread extends Thread {
        private String search;

        public SearchThread(String search) {
            this.search = search;
        }

        @Override
        public void run() {
            users.clear();
            users.addAll(userController.searchUsers(search));
            notifyUpdated();
        }

    }

    /**
     * This class sets up the accuracy of the search list view
     * while doing partial search.
     */
    // taken from http://stackoverflow.com/questions/5730609/is-it-possible-to-slowdown-reaction-of-edittext-listener
    // (C) 2015 user1338795
    abstract class DelayedTextWatcher implements TextWatcher {

        private long delayTime;
        private WaitTask lastWaitTask;

        public DelayedTextWatcher(long delayTime) {
            super();
            this.delayTime = delayTime;
        }

        @Override
        public void afterTextChanged(Editable s) {
            synchronized (this) {
                if (lastWaitTask != null) {
                    lastWaitTask.cancel(true);
                }
                lastWaitTask = new WaitTask();
                lastWaitTask.execute(s);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public abstract void afterTextChangedDelayed(Editable s);

        private class WaitTask extends AsyncTask<Editable, Void, Editable> {

            @Override
            protected Editable doInBackground(Editable... params) {
                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e) {
                }
                return params[0];
            }

            @Override
            protected void onPostExecute(Editable result) {
                super.onPostExecute(result);
                afterTextChangedDelayed(result);
            }
        }

    }

}
