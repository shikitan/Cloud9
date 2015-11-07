package com.example.yunita.tradiogc.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;

public class InventoryActivity extends AppCompatActivity {
    private Context context = this;
    private Inventory inventory = LoginActivity.USERLOGIN.getInventory();
    private ListView inventoryList;
    private ArrayAdapter<Item> inventoryViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_inventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        inventoryList = (ListView) findViewById(R.id.inventory_list_view);
    }

    @Override
    protected void onStart(){
        super.onStart();
        inventoryViewAdapter = new ArrayAdapter<Item>(this, R.layout.inventory_list_item, inventory);
        inventoryList.setAdapter(inventoryViewAdapter);
    }

    public void goToAddItem(View view){
        startActivity(new Intent(InventoryActivity.this, NewItemActivity.class));
    }

}
