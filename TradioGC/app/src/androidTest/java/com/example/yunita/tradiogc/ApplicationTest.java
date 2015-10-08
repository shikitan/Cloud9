package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2 {
    public ApplicationTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    /****************
     INVENTORY
     ****************/

    public void testAddItemToInventory() {
        Inventory inventory = new Inventory();
        Item item = new Item(id, name, quality, category, comment, quantity, shared);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);
    }

    public void testEditInventoryItem() {
        Inventory inventory = new Inventory();
        Item item = new Item(id, name, quality, category, comment, quantity, shared);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setName("new name");

        assertFalse((item.getName()).equals(modifiedItem.getName()));
    }

    public void testRemoveInventoryItem() {
        Inventory inventory = new Inventory();
        Item item = new Item(id, name, quality, category, comment, quantity, shared);
        inventory.addItem(item);
        assertEquals(inventory.getSize(), 1);

        inventory.removeItem(0);
        assertEquals(inventory.getSize(), 0);
    }

    //EDIT
    public void testViewInventory() {

    }

    //EDIT
    public void testViewItem() {

    }

    public void testSetItemVisibility() {
        Inventory inventory = new Inventory();
        Item item = new Item(id, name, quality, category, comment, quantity, shared);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setVisibility(0); // 0 = private, 1 = public

        assertFalse((item.getVisibility()).equals(modifiedItem.getVisibility()));
    }

    public void testSetItemCategory() {
        Inventory inventory = new Inventory();
        Item item = new Item(id, name, quality, category, comment, quantity, shared);
        inventory.addItem(item);

        Item modifiedItem = inventory.getItem(0);
        modifiedItem.setCategory(9); // category starts from [0] - [9]

        assertFalse((item.getCategory()).equals(modifiedItem.getCategory()));
    }

    /****************
     INVENTORY
     ****************/

    public void testSearchUserName() {
        User john = new User("john");
        User anne = new User("anne");

        UserList users = new UserList();
        users.addUser(john);
        users.addUser(anne);

        users.searchUser("anne");
    }

    public void testAddFriend() {
        User user = new User("username");
        User owner = new Owner("John");

        owner.addFriend(user);
        user.addFriend(owner);

        assertTrue(owner.getFriends().contains(user));
        assertTrue(user.getFriends().contains(owner));
    }

    public void testRemoveFriend() {
        User user = new User("username");
        User owner = new Owner("John");

        owner.addFriend(user);
        user.addFriend(owner);

        owner.removeFriend(user);
        user.removeFriend(owner);

        assertFalse(owner.getFriends().contains(user));
        assertFalse(user.getFriends().contains(owner));
    }

    

}

