package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

import com.example.yunita.tradiogc.inventory.Inventory;
import com.example.yunita.tradiogc.inventory.Item;
import com.example.yunita.tradiogc.user.User;

import java.security.acl.Owner;

public class OfflineUseCaseTest extends ActivityInstrumentationTestCase2 {

    public OfflineUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testMakeInventoryItemsOffline(){
        // when it is offline
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card",1, 50.00, "Unused", true);
        inventory.addItem(item);
        inventory.setIsOffline(true);
        assertTrue(inventory.getIsOffline());

        // when we get connectivity

    }

    public void testMakeTradeOffline(){
        User borrower = new User(username, password);
        User owner = new User(username, password);
        OfflineTrade trade = new OfflineTrade(trade_id, owner_item_id, borrower, owner);
        trade.addItem(borrower_item_id1);

        assertTrue(trade.getItemList.contains(borrower_item_id1));

    }

    public void testBrowseFriendInventoryOffline(){
        Inventory onlineInventory = new Inventory();
        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        onlineInventory.addItem(item);
        assertEquals(onlineInventory.getSize(), 1);

        User anne = new Owner("anne");
        anne.addInventory(onlineInventory);

        // cache inventory using key(user)-value pair(user inventory)
        OfflineInventory offlineInventory = new OfflineInventory();
        offlineInventory.setInventory(anne, anne.getInventory());

        assertNotNull(john.searchFriendInventoryOffline(anne));
    }

}