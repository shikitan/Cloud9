package com.example.yunita.tradiogc;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by yunita on 27/10/15.
 */
public class LoginController {

    private Gson gson = new Gson();
    private Users users;
    private static final String TAG = "LoginController";

    public LoginController(){
        users = new Users();
    }

    public void addUser(User user) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(users.getResourceUrl() + user.getAccount());

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
