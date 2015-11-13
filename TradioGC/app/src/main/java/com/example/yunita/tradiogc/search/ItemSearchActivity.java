package com.example.yunita.tradiogc.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.yunita.tradiogc.R;

import java.util.ArrayList;

public class ItemSearchActivity extends AppCompatActivity {

    private LinearLayout query_panel;
    private LinearLayout category_panel;

    private Spinner categoriesChoice;
    private ListView queryListView;
    private ListView categoryListView;

    private ArrayAdapter<SearchItem> queryViewAdapter;
    private ArrayAdapter<SearchItem> categoryViewAdapter;

    private SearchInventory friendsItems = new SearchInventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_search);

        query_panel = (LinearLayout) findViewById(R.id.search_query_panel);
        category_panel = (LinearLayout) findViewById(R.id.search_category_panel);

        categoriesChoice = (Spinner) findViewById(R.id.item_by_category_spinner);
        queryListView = (ListView) findViewById(R.id.item_by_query_list_view);
        categoryListView = (ListView) findViewById(R.id.item_by_category_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //this is how to handle class that extends ArrayList<> and implements Serializable
        ArrayList<SearchItem> tempInventory = (ArrayList<SearchItem>) extras.getSerializable("friendsItems");
        friendsItems.addAll(tempInventory);

        if (extras.getString("search").equals("query")) {
            query_panel.setVisibility(View.VISIBLE);

            queryViewAdapter = new ArrayAdapter<>(this, R.layout.friend_list_item, friendsItems);
            queryListView.setAdapter(queryViewAdapter);

        } else {
            category_panel.setVisibility(View.VISIBLE);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoriesChoice.setAdapter(adapter);

            categoryViewAdapter = new ArrayAdapter<>(this, R.layout.friend_list_item, friendsItems);
            categoryListView.setAdapter(categoryViewAdapter);
        }


    }

}
