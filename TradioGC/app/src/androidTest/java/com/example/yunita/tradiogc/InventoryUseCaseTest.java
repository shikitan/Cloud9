package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

import com.example.yunita.tradiogc.inventory.Inventory;
import com.example.yunita.tradiogc.inventory.Item;

public class InventoryUseCaseTest extends ActivityInstrumentationTestCase2 {

    public InventoryUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testAddItemToInventory() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);
    }

    public void testEditInventoryItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card",1, 50.00, "Unused", true);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setName("new name");

        assertFalse((item.getName()).equals(modifiedItem.getName()));
    }

    public void testRemoveInventoryItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card",1, 50.00, "Unused", true);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);

        //inventory.removeItem(0);
        assertEquals(inventory.getSize(), 0);
    }

    public void testViewInventory() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);

        assertNotNull(inventory);
        assertTrue(inventory.contains(item));
    }

    public void testViewItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card",1, 50.00, "Unused", true);
        inventory.addItem(item);

        assertEquals(inventory.getItem(1), item);
    }

    public void testSetItemVisibility() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setVisibility(true);

        assertFalse((item.getVisibility()).equals(modifiedItem.getVisibility()));
    }

    public void testSetItemCategory() {
        Inventory inventory = new Inventory();
        Item item = new Item("GAP Gift Card", 1, 50.00, "Unused", true);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setCategory(9); // category starts from [0] - [9]

        assertNotSame(item.getCategory(),modifiedItem.getCategory());
    }
}
