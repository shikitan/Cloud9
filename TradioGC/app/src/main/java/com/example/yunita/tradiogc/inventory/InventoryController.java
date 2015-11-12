package com.example.yunita.tradiogc.inventory;

import android.content.Context;

import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;

public class InventoryController {
    private static final String TAG = "InventoryController";
    private Inventory inventory = LoginActivity.USERLOGIN.getInventory();
    private UserController userController;
    private WebServer webServer = new WebServer();
    // Thread that close the activity after finishing add

    public InventoryController(Context context) {
        super();
        this.userController = new UserController(context);
    }

    public void addItem(Item item) {
        inventory.add(item);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    public void removeExistingItem(Item item) {
        inventory.remove(item);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

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

}
