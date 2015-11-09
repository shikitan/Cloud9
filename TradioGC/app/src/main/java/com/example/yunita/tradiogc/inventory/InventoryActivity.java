package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.profile.ProfileActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;

public class InventoryActivity extends AppCompatActivity {
    private Context context = this;
    private Inventory inventory = new Inventory();
    private String targetUsername;

    private ListView itemList;
    private ArrayAdapter<Item> inventoryViewAdapter;
    private Button add_item_button;

    private InventoryController inventoryController;
    private UserController userController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_inventory);
        inventoryController = new InventoryController(context);
        itemList = (ListView) findViewById(R.id.inventory_list_view);
        add_item_button = (Button) findViewById(R.id.add_item_button);

        userController = new UserController(context);
    }

    @Override
    protected void onStart() {
        super.onStart();

        inventoryViewAdapter = new ArrayAdapter<Item>(this, R.layout.inventory_list_item, inventory);
        itemList.setAdapter(inventoryViewAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                targetUsername = extras.getString("user_inventory");
            }
        }

        // check whether this is my profile or not
        if(!targetUsername.equals(LoginActivity.USERLOGIN.getUsername())){
            add_item_button.setVisibility(View.GONE);

        }

        Thread thread = new GetThread(targetUsername);
        thread.start();

        // See the item details on normal click
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = inventory.get(position);
                viewItemDetails(item);
            }
        });

        // Delete item on long click
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item deletedItem = inventory.get(position);
                Thread deleteThread = inventoryController.new DeleteItemThread(deletedItem);
                deleteThread.start();
                Toast.makeText(context, "Removing " + deletedItem.toString(), Toast.LENGTH_SHORT).show();
                inventoryViewAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void goToAddItem(View view) {
        startActivity(new Intent(InventoryActivity.this, AddItemActivity.class));
    }

    // Needs to be changed depending on what we want to send to the ItemActivity
    // We'll need to know whether we're viewing the description as a friend or as the owner
    // We'll probably also need to know what item was selected
    public void viewItemDetails(Item item) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra(ProfileActivity.USERNAME, targetUsername);
        startActivity(intent);
    }

    class GetThread extends Thread {
        private String username;

        public GetThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            inventory = userController.getUser(username).getInventory();
            runOnUiThread(doUpdateGUIDetails);
        }
    }

    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            inventoryViewAdapter.addAll(inventory);
            inventoryViewAdapter.notifyDataSetChanged();
        }
    };

}
