package com.example.yunita.tradiogc.inventory;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.yunita.tradiogc.R;

public class NewItemActivity extends ActionBarActivity {
    public InventoryController controller;
    Inventory inventory;
    EditText nameEdit;
    EditText priceEdit;
    EditText descriptionEdit;
    Spinner categoriesChoice;
    RadioButton privateChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_inventory);
        controller.createItem(inventory, "ebgames", 1, 5.00, "hi", true);

        //onClickListeners();
        }

    public void onClickListeners() {
        Button addItem = (Button) findViewById(R.id.add_item_button);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privateChoice = (RadioButton) findViewById(R.id.private_radio_button);
                categoriesChoice = (Spinner) findViewById(R.id.categories_spinner);
                nameEdit = (EditText) findViewById(R.id.name_textEdit);
                priceEdit = (EditText) findViewById(R.id.price_edit_text);
                descriptionEdit = (EditText) findViewById(R.id.description_text_edit);

                String name = nameEdit.getText().toString();
                int category = categoriesChoice.getSelectedItemPosition();
                Boolean visibility = true;
                if (privateChoice.isChecked()) {
                    visibility = false;
                }
                double price = Double.parseDouble(priceEdit.getText().toString());
                String description = descriptionEdit.getText().toString();

                controller.createItem(inventory, name, category, price, description, visibility);
            }
        });

    }
}
