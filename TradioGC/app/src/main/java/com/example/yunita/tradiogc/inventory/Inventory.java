package com.example.yunita.tradiogc.inventory;

import com.example.yunita.tradiogc.Observer;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> implements com.example.yunita.tradiogc.Observable {

    private static final long serialVersionUID = 3199561696102797345L;
    private volatile ArrayList<Observer> observers = new ArrayList<Observer>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.notifyUpdated(this);
        }
    }

    public Inventory getPublicItems(Inventory inventory){
        Inventory pInventory = new Inventory();
        for(Item item : inventory){
            if(item.getVisibility() == true){
                pInventory.add(item);
            }
        }
        return pInventory;
    }

}
