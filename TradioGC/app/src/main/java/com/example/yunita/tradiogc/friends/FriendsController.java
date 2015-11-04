package com.example.yunita.tradiogc.friends;

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

public class FriendsController {

    private static final String TAG = "FriendsController";
    private Gson gson = new Gson();
    private Context context;
    private WebServer webServer = new WebServer();
    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            ((Activity) context).finish();
        }
    };

    public FriendsController(Context context) {
        super();
        this.context = context;
    }

    public void updateFriend(User user) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(webServer.getResourceUrl() + user.getUsername());

            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class UpdateFriendsThread extends Thread {
        private User user;
        private User friend;

        public UpdateFriendsThread(User user) {
            this.user = user;
            //this.friend = friend;
        }

        @Override
        public void run() {
            updateFriend(user);
            //updateFriend(friend);
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            ((Activity) context).runOnUiThread(doFinishAdd);
        }
    }
}
