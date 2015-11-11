package com.example.yunita.tradiogc.friends;

import android.app.Activity;
import android.content.Context;

import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;

public class FriendsController {

    private static final String TAG = "FriendsController";
    private UserController userController;
    private Context context;
    private Friends friends = LoginActivity.USERLOGIN.getFriends();

    public FriendsController(Context context) {
        super();
        this.context = context;
        userController = new UserController(context);
    }

    public void addFriend(String friendname) {
        friends.add(friendname);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    public void deleteFriend(String friendname) {
        friends.remove(friendname);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }


}
