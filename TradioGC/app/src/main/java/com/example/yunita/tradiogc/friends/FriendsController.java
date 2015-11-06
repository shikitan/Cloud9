package com.example.yunita.tradiogc.friends;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.SearchController;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class FriendsController {

    private static final String TAG = "FriendsController";
    private Gson gson = new Gson();
    private WebServer webServer = new WebServer();
    private SearchController searchController;
    private User friend;

    public FriendsController(Context context) {
        super();
        searchController = new SearchController(context);
    }


    public void updateUser(User user) {
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

    public void addFriend(String friendname) {
        Thread refreshUserLoginThread = new RefreshUserLoginThread();
        refreshUserLoginThread.start();
        synchronized (refreshUserLoginThread) {
            try {
                refreshUserLoginThread.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the user's username to the new friend's friend list
            LoginActivity.USERLOGIN.getFriends().addNewFriend(friendname);
        }
        Thread updateUserThread = new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();

        Thread getFriendThread = new GetFriendThread(friendname);
        getFriendThread.start();

        synchronized (getFriendThread) {
            try {
                getFriendThread.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the user's username to the new friend's friend list
            friend.getFriends().addNewFriend(LoginActivity.USERLOGIN.getUsername());
        }
        updateUserThread = new UpdateUserThread(friend);
        updateUserThread.start();
    }


    public void deleteFriend(String friendname) {
        LoginActivity.USERLOGIN.getFriends().deleteFriend(friendname);
        Thread updateUserThread = new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();


        // Start a new thread for finding the User of the removed friend
        Thread getFriendThread = new GetFriendThread(friendname);
        getFriendThread.start();

        synchronized (getFriendThread) {
            try {
                getFriendThread.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the user's username to the new friend's friend list
            friend.getFriends().deleteFriend(LoginActivity.USERLOGIN.getUsername());
        }
        updateUserThread = new UpdateUserThread(friend);
        updateUserThread.start();
    }


    public class UpdateUserThread extends Thread {
        private User user;
        public UpdateUserThread(User user) {
            this.user = user;
        }
        @Override
        public void run() {
            synchronized (this) {
                updateUser(user);
                notify();
            }
        }
    }


    public class GetFriendThread extends Thread {
        private String username;
        public GetFriendThread(String username) {
            this.username = username;
        }
        @Override
        public void run() {
            synchronized (this) {
                friend = searchController.getUser(username);
                notify();
            }
        }
    }

    public class RefreshUserLoginThread extends Thread {
        public RefreshUserLoginThread() {}
        @Override
        public void run() {
            synchronized (this) {
                String username = LoginActivity.USERLOGIN.getUsername();
                LoginActivity.USERLOGIN = searchController.getUser(username);
                notify();
            }
        }
    }



}
