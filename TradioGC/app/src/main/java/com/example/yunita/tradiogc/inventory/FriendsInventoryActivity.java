package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.user.UserController;

public class FriendsInventoryActivity extends AppCompatActivity {

    private Context context = this;
    private Inventory inventory = new Inventory();
    private UserController userController;
    private String targetUsername;

    private ListView itemList;
    private ArrayAdapter<Item> inventoryViewAdapter;
    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            inventoryViewAdapter.clear();
            inventoryViewAdapter.addAll(inventory);
            inventoryViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_inventory);

        // remove page transition.
        overridePendingTransition(0, 0);

        itemList = (ListView) findViewById(R.id.friends_inventory_list_view);
        userController = new UserController(context);
    }

    /**
     * Gets the friend's name that is passed from previous activity.
     * <p>It runs get inventory thread and get the inventory of this
     * friend. In addition, while user clicks on item, it sends user
     * to Item Detail page.
     */
    @Override
    protected void onStart() {
        super.onStart();
        inventoryViewAdapter = new ArrayAdapter<Item>(this, R.layout.inventory_list_item, inventory);
        itemList.setAdapter(inventoryViewAdapter);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                targetUsername = extras.getString("friend_uname");
            }
        }

        // run the thread to retrieve friend's inventory
        Thread getInventoryThread = new GetPublicItemsThread(targetUsername);
        getInventoryThread.start();

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = inventory.get(position);
                viewItemDetails(item, position);
            }
        });
    }

    /**
     * Removes page transition.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    /**
     * Called when the user clicks on item.
     * This method is used to send user to Item Detail page and
     * pass item index position and tell Item Detail activity
     * to show the Item Detail page from friend's perspective.
     *
     * @param item     this item.
     * @param position this item's index in the inventory.
     */
    public void viewItemDetails(Item item, int position) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("item", item);
        // mark this as "friends" page
        intent.putExtra("owner", "friend");
        intent.putExtra("index", position);

        startActivity(intent);
    }

    /**
     * Called when the activity is started.
     * <p>This class creates a thread and runs "Get User".
     * While it is running, it gets the user inventory that only
     * contains public items and updates the view.
     */
    class GetPublicItemsThread extends Thread {
        private String username;

        public GetPublicItemsThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            inventory = userController.getUser(username).getInventory();
            // only show public items
            inventory = inventory.getPublicItems(inventory);
            runOnUiThread(doUpdateGUIDetails);
        }
    }

}
