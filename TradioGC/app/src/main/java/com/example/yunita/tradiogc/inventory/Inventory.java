package com.example.yunita.tradiogc.inventory;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

    public Inventory(){

    }

    public void addNewItem(Item item){
        this.add(item);
    }

    public void removeItem(Item item){
        this.remove(item);
    }

    @Override
    public String toString(){
        return "";
    }
}
