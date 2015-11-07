package com.example.yunita.tradiogc.inventory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.user.UserController;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

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
        inventory.addNewItem(item);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    public void removeExistingItem(Item item) {
        inventory.removeItem(item);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

}
