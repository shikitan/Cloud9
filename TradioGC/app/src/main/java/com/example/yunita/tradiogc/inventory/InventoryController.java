package com.example.yunita.tradiogc.inventory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.WebServer;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class InventoryController {
    private static final String TAG = "InventoryController";
    private Inventory inventory = LoginActivity.USERLOGIN.getInventory();
    private Gson gson = new Gson();
    private Context context;
    private WebServer webServer = new WebServer();
    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            ((Activity) context).finish();
        }
    };

    public InventoryController(Context context) {
        super();
        this.context = context;
    }

    public void addItem(Item item) {
        inventory.addNewItem(item);
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(webServer.getResourceUrl() + LoginActivity.USERLOGIN.getUsername());
            // check http://cmput301.softwareprocess.es:8080/cmput301f15t09/inventory/[username]

            StringEntity stringEntity = new StringEntity(gson.toJson(LoginActivity.USERLOGIN));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UpdateInventoryThread extends Thread {
        private Item item;

        public UpdateInventoryThread(Item item) {
            this.item = item;
        }

        @Override
        public void run() {
            addItem(item);
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
