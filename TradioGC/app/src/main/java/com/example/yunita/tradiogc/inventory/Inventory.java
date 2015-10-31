package com.example.yunita.tradiogc.inventory;

import android.support.v4.os.ParcelableCompat;

import java.util.ArrayList;

/**
 * Created by dshin on 10/31/15.
 */
public class Inventory{
    public ArrayList<Item> inventory;
    public Item item;
    public Boolean IsOffline;

    public Boolean getIsOffline() {
        return IsOffline;
    }

    public void setIsOffline(Boolean IsOffline) {
        this.IsOffline = IsOffline;
    }

    public Item getItem(int i) {
        return inventory.get(i);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Inventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }
    public int getSize() {
        return inventory.size();
    }
    public void removeItem(int i){
        inventory.remove(i);
    }
    public Boolean contains(Item item){
        return inventory.contains(item);
    }
}
