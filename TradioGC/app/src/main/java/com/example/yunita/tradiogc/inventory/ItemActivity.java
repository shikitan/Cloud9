package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.User;

public class ItemActivity extends AppCompatActivity {

    private Inventory inventory = LoginActivity.USERLOGIN.getInventory();
    private Item item;
    private Context context = this;
    private String[] category_array = getResources().getStringArray(R.array.categories_array);

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

            // Hasn't been tested yet
            // Need to check if the item has a photo
            // If no photo, we need to set the visibility of itemImage to "gone"
            //photo.setImage... waiting for photo to be implemented
            name.setText(item.getName());
            category.setText(category_array[item.getCategory()]);
            price.setText("$"+Double.toString(item.getPrice()));
            description.setText(item.getDesc());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friend_panel = (LinearLayout) findViewById(R.id.friend_button_panel_item);
        edit_button = (ImageButton) findViewById(R.id.edit_button);

    }

    // Most of the onStart right now is for altering the layout depending if you're viewing as a
    // friend or as the owner of the item
    /*
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                targetUsername = extras.getString(USERNAME);
                Thread thread = new GetThread(targetUsername);
                thread.start();
            }
        }

        // Checks to see if we are getting a username from the intent
        if (!targetUsername.equals(LoginActivity.USERLOGIN.getUsername())) {

            // If the user is a friend of the person owning the item
            // Show trade button and remove edit profile button
            friend_panel.setVisibility(View.VISIBLE);

        }
    }
    */

    /*  To be added when editing item is implemented
    public void editItem(View view) {
        Intent intent = new Intent(context, EditItemActivity.class);
        startActivity(intent);
    }
    */

    /* Possibly added/edited when searching an item is implemented
    class GetThread extends Thread {
        private String itemName;

        public GetThread(String itemName) {
            this.itemName = itemName;
        }

        @Override
        public void run() {
            item = itemController.getItem(itemName);
            runOnUiThread(doUpdateGUIDetails);
        }
    }
    */

}
