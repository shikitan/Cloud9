package com.example.yunita.tradiogc;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunita.tradiogc.profile.ProfileActivity;

public class SearchUserActivity extends Activity {
    private Users users;
    private ListView userList;
    private ArrayAdapter<User> usersViewAdapter;
    private SearchController searchController;
    private EditText editText1;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends_search_results);
        userList = (ListView) findViewById(R.id.friendsSearchList);
        editText1 = (EditText) findViewById(R.id.editText1);
    }


    @Override
    protected void onStart() {
        super.onStart();

        users = new Users();
        usersViewAdapter = new ArrayAdapter<User>(this, R.layout.friend_list_item, users);
        userList.setAdapter(usersViewAdapter);
        searchController = new SearchController(mContext);
        //SearchThread thread = new SearchThread("");
        //thread.start();
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
                startDetailsActivity(username);
            }

        });

    }

    public void startDetailsActivity(String username) {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USERNAME, username);

        startActivity(intent);
    }

    public void search(View view) {
        users.clear();
        TextView query = (TextView) findViewById(R.id.editText1);
        SearchThread thread = new SearchThread(query.getText().toString());
        thread.start();
    }


    public void notifyUpdated() {
        // Thread to update adapter after an operation
        Runnable doUpdateGUIList = new Runnable() {
            public void run() {
                usersViewAdapter.notifyDataSetChanged();
            }
        };
        runOnUiThread(doUpdateGUIList);
    }


    class SearchThread extends Thread {
        private String search;

        public SearchThread(String search) {
            this.search = search;
        }

        @Override
        public void run() {
            users.clear();
            users.addAll(searchController.searchUsers(search));
            notifyUpdated();
        }

    }

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
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

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
