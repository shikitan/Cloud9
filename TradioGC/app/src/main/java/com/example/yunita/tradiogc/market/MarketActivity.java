package com.example.yunita.tradiogc.market;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yunita.tradiogc.R;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
