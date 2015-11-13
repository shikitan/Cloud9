package com.example.yunita.tradiogc.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.user.UserController;

public class SearchFriendInventoryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }
}
