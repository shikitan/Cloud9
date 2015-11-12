package com.example.yunita.tradiogc.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.yunita.tradiogc.R;

public class ItemSearchActivity extends AppCompatActivity {

    private LinearLayout query_panel;
    private LinearLayout category_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_search);

        query_panel = (LinearLayout) findViewById(R.id.search_query_panel);
        category_panel = (LinearLayout) findViewById(R.id.search_category_panel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras.getString("search").equals("query")) {
            query_panel.setVisibility(View.VISIBLE);
        } else {
            category_panel.setVisibility(View.VISIBLE);
        }


    }

}
