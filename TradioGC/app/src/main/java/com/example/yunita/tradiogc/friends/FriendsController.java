package com.example.yunita.tradiogc.friends;

import android.content.Context;

import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;

public class FriendsController {

    private static final String TAG = "FriendsController";
    private UserController userController;
    private Friends friends = LoginActivity.USERLOGIN.getFriends();

    public FriendsController(Context context) {
        super();
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

    class DeleteFriendThread extends Thread {
        private String friendname;

        public DeleteFriendThread(String friendname) {
            this.friendname = friendname;
        }

        @Override
        public void run() {
            deleteFriend(friendname);
            friends.remove(friendname);
        }
    }


}
