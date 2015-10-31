package com.example.yunita.tradiogc.inventory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.WebServer;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by dshin on 10/31/15.
 */
public class InventoryController {
    private static final String TAG = "InventoryController";
    private Gson gson = new Gson();
    private Inventory newInventory;
    private User newUser;
    private Context context;
    private WebServer webServer = new WebServer();
    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            ((Activity) context).finish();
        }
    };


    public void createItem(Inventory inventory, String name,int category,double price,String description,Boolean visibility){
        //Item item = new Item(name, category, price, description, visibility);
        Item item = new Item("ebgames", 1, 20.00, "hi", true);
        inventory.addItem(item);
    }

    public void addItem(String username, Inventory inventory) {
        newInventory = inventory;
        HttpClient httpClient = new DefaultHttpClient();

        saveUserInFile(username);
        try {
            HttpPost addRequest = new HttpPost(webServer.getResourceUrl() + username + username);
            // check http://cmput301.softwareprocess.es:8080/cmput301f15t09/user/[username]/inventory

            StringEntity stringEntity = new StringEntity(gson.toJson(newInventory));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUserInFile(String username) {
        try {
            FileOutputStream fos = context.openFileOutput(username + ".sav", 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(newUser, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class SignUpThread extends Thread {
        private String username;
        private Inventory inventory;

        public SignUpThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            addItem(username, inventory);
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ((Activity) context).runOnUiThread(doFinishAdd);
        }
    }
}
