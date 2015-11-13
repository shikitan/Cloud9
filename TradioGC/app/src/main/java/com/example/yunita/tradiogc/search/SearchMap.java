package com.example.yunita.tradiogc.search;

import com.example.yunita.tradiogc.inventory.Inventory;

import java.io.Serializable;
import java.util.HashMap;

public class SearchMap extends HashMap<String, Inventory> implements Serializable{

    public SearchMap(){
    }

    public Inventory getSearchInventory(HashMap<String, Inventory> map){
        Inventory inventory = new Inventory();
        for (HashMap.Entry<String, Inventory> entry : map.entrySet()) {
            inventory.addAll(entry.getValue());
        }
        return inventory;
    }
}
