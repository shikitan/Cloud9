package com.example.yunita.tradiogc.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.inventory.Inventory;
import com.example.yunita.tradiogc.inventory.Item;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;
import com.example.yunita.tradiogc.user.Users;

public class MarketActivity extends AppCompatActivity {

    private Context context = this;
    private User borrower;
    private Friends friends;
    private Users users = new Users();
    private UserController userController;
    private Inventory friendsItems = new Inventory();
    private ListView friendsItemList;
    private ArrayAdapter<Item> friendsItemViewAdapter;
    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {

            // initialize new inv to avoid an "adding item twice" bug
            Inventory inv = new Inventory();
            for (User user : users) {
                Inventory pItems;
                if (friends.contains(user.getUsername())) {
                    pItems = new Inventory().getPublicItems(user.getInventory());
                    inv.addAll(pItems);
                }
            }

            friendsItemViewAdapter.addAll(inv);
            friendsItemViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        userController = new UserController(context);
        friendsItemList = (ListView) findViewById(R.id.all_search_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        friendsItemViewAdapter = new ArrayAdapter<Item>(this, R.layout.friend_list_item, friendsItems);
        friendsItemList.setAdapter(friendsItemViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        friendsItems.clear();

        // update user information
        Thread getUserLoginThread = userController.new GetUserLoginThread(LoginActivity.USERLOGIN.getUsername());
        getUserLoginThread.start();
        synchronized (getUserLoginThread) {
            try {
                getUserLoginThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        friends = LoginActivity.USERLOGIN.getFriends();

        // get all users in db
        Thread getUsersThread = new GetUsersThread("");
        getUsersThread.start();
    }

    public void goToSearchByCategory(View view) {
        Intent intent = new Intent(context, ItemSearchActivity.class);
        intent.putExtra("search", "category");
        startActivity(intent);
    }

    public void goToSearchByQuery(View view) {
        Intent intent = new Intent(context, ItemSearchActivity.class);
        intent.putExtra("search", "query");
        startActivity(intent);
    }

    class GetUsersThread extends Thread {
        private String search;

        public GetUsersThread(String search) {
            this.search = search;
        }

        @Override
        public void run() {
            users.clear();
            users.addAll(userController.searchUsers(search));
            runOnUiThread(doUpdateGUIDetails);
        }

    }

}
