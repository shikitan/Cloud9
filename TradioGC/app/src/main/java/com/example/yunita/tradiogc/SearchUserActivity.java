package com.example.yunita.tradiogc;

import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunita.tradiogc.login.LoginController;

public class SearchUserActivity extends Activity {
    private Users users;
    private ListView userList;
    private ArrayAdapter<User> usersViewAdapter;
    private Context mContext = this;
    private SearchController searchController;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends_search_results);
        userList = (ListView) findViewById(R.id.friendsSearchList);

    }

    @Override
    protected void onStart() {
        super.onStart();

        users = new Users();
        usersViewAdapter = new ArrayAdapter<User>(this, R.layout.friend_list_item, users);
        userList.setAdapter(usersViewAdapter);
        searchController = new SearchController();
    }

    @Override
    protected void onResume() {
        super.onResume();


        SearchThread thread = new SearchThread("*");

        thread.start();
    }

    public void search(View view) {
        users.clear();

        // TODO: Extract search query from text view
        TextView query = (TextView) findViewById(R.id.editText1);


        // TODO: Run the search thread
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
            users.addAll(searchController.searchUsers(search, null));
            notifyUpdated();
        }

    }
}
