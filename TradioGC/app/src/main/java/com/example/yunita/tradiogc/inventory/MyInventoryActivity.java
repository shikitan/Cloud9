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

        overridePendingTransition(0, 0);

        inventoryController = new InventoryController(context);
        userController = new UserController(context);

        itemList = (ListView) findViewById(R.id.inventory_list_view);
    }

    /**
     * Sets up the "Inventory View Adapter" and manipulates the list view.
     * When an item is pressed, it sends the user to the Item Detail page.
     * When an item is long pressed, it removes the item from
     * the user's inventory and calls the "Delete Item Thread".
     */
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

    /**
     * Refreshes the list view.
     */
    @Override
    protected void onResume() {
        super.onResume();
        inventory.clear();
        inventory.addAll(LoginActivity.USERLOGIN.getInventory());
        inventoryViewAdapter.notifyDataSetChanged();
    }

    /**
     * Called when the user presses the "+" button in the Inventory page.
     * <p>This method is used to send the user to the Add Item page.
     *
     * @param view: "+" Button in the user's Inventory page
     */
    public void goToAddItem(View view) {
        startActivity(new Intent(MyInventoryActivity.this, AddItemActivity.class));
    }

    /**
     * Called when the user presses on an item.
     * This method is used to send the user to the Item Detail page,
     * pass the item index position, and tell the Item Detail activity
     * to show the Item Detail page from the user's perspective.
     *
     * @param item:     this item
     * @param position: this item's index in the inventory
     */
    public void viewItemDetails(Item item, int position) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("owner", "owner");
        intent.putExtra("index", position);

        startActivity(intent);
        finish();
    }


}
