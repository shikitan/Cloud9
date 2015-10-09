package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class InventoryUseCaseTest extends ActivityInstrumentationTestCase2 {

    public InventoryUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testAddItemToInventory() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);
    }

    public void testEditInventoryItem() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setName("new name");

        assertFalse((item.getName()).equals(modifiedItem.getName()));
    }

    public void testRemoveInventoryItem() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);

        inventory.removeItem(0);
        assertEquals(inventory.getSize(), 0);
    }

    public void testViewInventory() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);

        assertNotNull(inventory);
        assertTrue(inventory.contains(item));
    }

    public void testViewItem() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);

        assertEquals(inventory.getItem(1).equals(item));
    }

    public void testSetItemVisibility() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setVisibility(0); // 0 = private, 1 = public

        assertFalse((item.getVisibility()).equals(modifiedItem.getVisibility()));
    }

    public void testSetItemCategory() {
        Inventory inventory = new Inventory();
        Item item = new Item(1, "GAP Gift Card", "A", "Apparel", "Unused", 1, 1);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setCategory(9); // category starts from [0] - [9]

        assertFalse((item.getCategory()).equals(modifiedItem.getCategory()));
    }
}
