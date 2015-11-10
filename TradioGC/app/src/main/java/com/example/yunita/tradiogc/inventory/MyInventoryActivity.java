package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;


public class MyInventoryActivity extends AppCompatActivity {
    private Context context = this;
    private Inventory inventory = new Inventory();

    private ListView itemList;
    private ArrayAdapter<Item> inventoryViewAdapter;

    private InventoryController inventoryController;
    private UserController userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inventory);

        inventoryController = new InventoryController(context);
        userController = new UserController(context);

        itemList = (ListView) findViewById(R.id.inventory_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        inventoryViewAdapter = new ArrayAdapter<Item>(this, R.layout.inventory_list_item, inventory);
        itemList.setAdapter(inventoryViewAdapter);


        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = inventory.get(position);
                viewItemDetails(item, position);
            }
        });

        // !!!!!!!BUG HERE:DO NOT DELETE IT IN WEB SERVER
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item deletedItem = inventory.get(position);
                Thread deleteThread = inventoryController.new DeleteItemThread(deletedItem);
                deleteThread.start();
                inventory.remove(deletedItem);
                Toast.makeText(context, "Removing " + deletedItem.toString(), Toast.LENGTH_SHORT).show();
                inventoryViewAdapter.notifyDataSetChanged();
                return true;
            }
        }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        inventory.clear();
        inventory.addAll(LoginActivity.USERLOGIN.getInventory());
        inventoryViewAdapter.notifyDataSetChanged();
    }


    public void goToAddItem(View view) {
        startActivity(new Intent(MyInventoryActivity.this, AddItemActivity.class));
    }

    public void viewItemDetails(Item item, int position) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("owner", "login");
        intent.putExtra("index",position);

        startActivity(intent);
    }


}
