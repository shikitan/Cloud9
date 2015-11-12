package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;

/*
Comments for 09/11/2015 update:
-The layout for viewing an item is mostly done as well as some coding on this activity.
-Modifications were made in MyInventoryActivity to start this activity
-Unfortunately, I'm a bit stuck on finding an effective way to send the item information from the
 MyInventoryActivity to this activity so it doesn't work yet. We could possibly wait until we finish
 the code for searching for an item to get the item information for this page...? Or I'm just
 missing a really obvious option for getting it to work ||OTL
-Since we haven't added photos to the item class yet, I haven't done much with the ImageView on this
 part and on the layout
-Since we haven't implemented trades, the Trade button does not do anything on this page. Or..
 how do we plan to start/add items to a trade? From the inventory list page or the item detail page?
-According to US01.01.01, item must have "a name, quantity, quality, category, if I want to share it
 with others, and comments". So.. technically our item should have quantity and quality as well..?
-The location for the edit item button is subject to change
-A lot of stuff on this class is commented out for now since I'm not sure about some parts
-A few more comments found on: ItemActivity (scattered), MyInventoryActivity(viewItemDetails)
*/
public class ItemActivity extends AppCompatActivity {

    private Item item;
    private Context context = this;
    private Categories categories;
    private String owner;
    private int index;
    private UserController userController;

    private LinearLayout friend_panel;  // Shown when wanting to make a trade with an item
    // Not sure if that's how we want to start a trade with an item?
    private ImageButton edit_button;    // Shown when the item is part of the user's inventory


    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            //ImageView photo = (ImageView) findViewById(R.id.itemImage);
            TextView name = (TextView) findViewById(R.id.itemName);
            TextView category = (TextView) findViewById(R.id.itemCategory);
            TextView price = (TextView) findViewById(R.id.itemPrice);
            TextView description = (TextView) findViewById(R.id.itemDescription);
            TextView quantity = (TextView) findViewById(R.id.itemQuantity);
            TextView quality = (TextView) findViewById(R.id.itemQuality);

            // Hasn't been tested yet
            // Need to check if the item has a photo
            // If no photo, we need to set the visibility of itemImage to "gone"
            //photo.setImage... waiting for photo to be implemented
            name.setText(item.getName());
            category.setText(categories.getCategories().get(item.getCategory()));
            price.setText("$" + item.getPrice());
            description.setText(item.getDesc());
            quantity.setText(Integer.toString(item.getQuantity()));
            if(item.getQuality() == 0){
                quality.setText("New");
            } else {
                quality.setText("Used");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        overridePendingTransition(0, 0);

        friend_panel = (LinearLayout) findViewById(R.id.friend_button_panel_item);
        edit_button = (ImageButton) findViewById(R.id.edit_button);
        userController = new UserController(context);

    }

    // Most of the onStart right now is for altering the layout depending if you're viewing as a
    // friend or as the owner of the item

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        categories = new Categories();

        index = intent.getExtras().getInt("index");
        owner = intent.getExtras().getString("owner");
        // Checks to see if we are getting a username from the intent
        if (owner.equals("friend")) {
            edit_button.setVisibility(View.GONE);
        }

        runOnUiThread(doUpdateGUIDetails);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (owner.equals("owner")) {
            Thread getUserLoginThread = userController.new GetUserLoginThread(LoginActivity.USERLOGIN.getUsername());
            getUserLoginThread.start();
            synchronized (getUserLoginThread) {
                try {
                    getUserLoginThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Inventory inventory = LoginActivity.USERLOGIN.getInventory();
                item = inventory.get(index);
                runOnUiThread(doUpdateGUIDetails);
            }
        }
    }


    @Override
    public void onBackPressed() {
        // fix remove inventory bug
        if (owner.equals("owner")) {
            Intent intent = new Intent(context, MyInventoryActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void editItem(View view) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

}
