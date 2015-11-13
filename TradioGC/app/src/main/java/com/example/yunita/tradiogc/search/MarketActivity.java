package com.example.yunita.tradiogc.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.inventory.Inventory;
import com.example.yunita.tradiogc.inventory.Item;
import com.example.yunita.tradiogc.inventory.ItemActivity;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;
import com.example.yunita.tradiogc.user.Users;

public class MarketActivity extends AppCompatActivity {

    private Context context = this;
    private Friends friends;
    private Users users = new Users();
    private UserController userController;
    private Inventory friendsItems = new Inventory();
    private ListView friendsItemListView;
    private ArrayAdapter<Item> friendsItemViewAdapter;

    // hash map, in case search for all friends inventory
    private SearchMap searchMap = new SearchMap();

    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            friendsItems.clear();
            searchMap.clear();
            for (User user : users) {
                Inventory pItems;
                if (friends.contains(user.getUsername())) {
                    pItems = new Inventory().getPublicItems(user.getInventory());
                    friendsItems.addAll(pItems);

                    // in case search for all friends inventory, remove if necessary
                    searchMap.put(user.getUsername(), pItems);
                }
            }
            friendsItemViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        userController = new UserController(context);
        friendsItemListView = (ListView) findViewById(R.id.all_search_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        friendsItemViewAdapter = new ArrayAdapter<Item>(this, R.layout.friend_list_item, friendsItems);
        friendsItemListView.setAdapter(friendsItemViewAdapter);

        friendsItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = friendsItems.get(position);
                viewItemDetails(item, position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        intent.putExtra("searchMap", searchMap);
//
        startActivity(intent);
    }

    public void goToSearchByQuery(View view) {
        Intent intent = new Intent(context, ItemSearchActivity.class);
        intent.putExtra("search", "query");
        intent.putExtra("searchMap", searchMap);
//        intent.putExtra("friendsItems", friendsItems);
        startActivity(intent);
    }

    /**
     * Called when the user clicks on item.
     * This method is used to send user to Item Detail page and
     * pass item index position and tell Item Detail activity
     * to show the Item Detail page from borrower(friend)'s perspective.
     *
     * @param item     this item.
     * @param position this item's index in friends' item list.
     */
    public void viewItemDetails(Item item, int position) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("owner", "friend");
        intent.putExtra("index", position);

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
