package com.example.yunita.tradiogc.inventory;

import android.content.Context;

import com.example.yunita.tradiogc.CheckNetwork;
import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class InventoryController {
    private static final String TAG = "InventoryController";
    private UserController userController;
    private Context context;
    private Gson gson = new Gson();
    private WebServer webServer = new WebServer();
    private CheckNetwork checkNetwork = new CheckNetwork(this.context);
    private Inventory inventory;

    /**
     * Class constructor specifying that this controller class is a subclass of Context.
     *
     * @param context
     */
    public InventoryController(Context context) {
        super();
        this.context = context;
        this.userController = new UserController(context);
        if (checkNetwork.isOnline() == true){
            inventory = LoginActivity.USERLOGIN.getInventory();
        }
        else{

        }
    }

    /**
     * Called after the new item is created.
     * This method is used to add the item to the user's inventory
     * and run the "Update User Thread".
     *
     * @param item new item
     */
    public void addItem(Item item) {
        inventory.add(item);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        saveInventoryInFile(inventory, LoginActivity.USERLOGIN);

        updateUserThread.start();
    }


    /**
     * Called when the user long presses on an item that exists in the inventory.
     * <p>This method is used to remove the item from the inventory and
     * run the "Update User Thread".
     *
     * @param item existing item in the inventory
     */
    public void removeExistingItem(Item item) {
        inventory.remove(item);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        saveInventoryInFile(inventory, LoginActivity.USERLOGIN);

        updateUserThread.start();
    }

    /**
     * Called when the user needs to update the items in their inventory.
     * <p>This method is used to update the user's inventory.
     *
     * @param item item
     */
    public void updateItem(Item item) {
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
        synchronized (updateUserThread) {
            try {
                updateUserThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread getUserLoginThread = userController.new GetUserLoginThread(LoginActivity.USERLOGIN.getUsername());
            saveInventoryInFile(inventory, LoginActivity.USERLOGIN);
            getUserLoginThread.start();
            synchronized (getUserLoginThread) {
                try {
                    getUserLoginThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Called when the user long presses on an existing item in their inventory.
     * <p>This class creates a thread and runs "Delete Item".
     * While it is running, it removes this item from the user's inventory
     * and updates the inventory.
     */
    class DeleteItemThread extends Thread {
        private Item item;

        public DeleteItemThread(Item item) {
            this.item = item;
        }

        @Override
        public void run() {
            synchronized (this) {
                removeExistingItem(item);
                inventory.remove(item);
                notify();
            }
        }
    }
    /**
     * Called when
     * This method saves the inventory to the local storage.
     *
     */
    private void saveInventoryInFile(Inventory inventory, User user){
        try{
            FileOutputStream fos = context.openFileOutput(user.getUsername() + "inventory.sav", 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(inventory, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
