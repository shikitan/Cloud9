package com.example.yunita.tradiogc.friends;

import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.user.UserController;
import com.example.yunita.tradiogc.user.User;
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
    private WebServer webServer = new WebServer();
    private UserController userController;
    private Friends friends =  LoginActivity.USERLOGIN.getFriends();

    public FriendsController(Context context) {
        super();
        userController = new UserController(context);
    }

    public void addFriend(String friendname) {
        friends.addNewFriend(friendname);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    public void deleteFriend(String friendname) {
        friends.deleteFriend(friendname);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

}
