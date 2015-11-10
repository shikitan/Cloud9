package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;

public class FriendsInventoryActivity extends AppCompatActivity {

    private Context context = this;
    private Inventory inventory = new Inventory();
    private UserController userController;
    private String targetUsername;

    private ListView itemList;
    private ArrayAdapter<Item> inventoryViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_inventory);

        overridePendingTransition(0, 0);

        itemList = (ListView) findViewById(R.id.friends_inventory_list_view);
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
                targetUsername = extras.getString("friend_uname");
            }
        }

        Thread getInventoryThread = new GetThread(targetUsername);
        getInventoryThread.start();

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = inventory.get(position);
                viewItemDetails(item, position);
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
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
            inventoryViewAdapter.clear();
            inventoryViewAdapter.addAll(inventory);
            inventoryViewAdapter.notifyDataSetChanged();
        }
    };

    public void viewItemDetails(Item item, int position) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("owner", "friend");
        intent.putExtra("index",position);

        startActivity(intent);
    }

}
