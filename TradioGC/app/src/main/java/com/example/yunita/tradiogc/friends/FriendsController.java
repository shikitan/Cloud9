package com.example.yunita.tradiogc.friends;

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

    private Gson gson = new Gson();
    private Friends friends;
    private static final String TAG = "FriendsController";
    private WebServer webServer = new WebServer();

    public FriendsController(Friends friends) {
        super();
        this.friends = friends;
    }

    public void addFriend(User user) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost( webServer.getResourceUrl() + user.getUsername());

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


}
