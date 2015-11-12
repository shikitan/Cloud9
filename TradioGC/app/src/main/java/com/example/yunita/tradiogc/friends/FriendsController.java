package com.example.yunita.tradiogc.friends;

import android.content.Context;

import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.UserController;

public class FriendsController {

    private static final String TAG = "FriendsController";
    private UserController userController;
    private Context context;
    private Friends friends = LoginActivity.USERLOGIN.getFriends();

    /**
     * Class constructor specifying this controller class is a subclass of Context.
     *
     * @param context
     */
    public FriendsController(Context context) {
        super();
        this.context = context;
        userController = new UserController(context);
    }

    /**
     * Called when the user clicks "Add Friend" button.
     * <p>The method is used to add friend to the current login user's friend list
     * and update user information, which is now with updated friend list,
     * in the webserver.
     *
     * @param friendname new friend with this name.
     */
    public void addFriend(String friendname) {
        friends.add(friendname);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    /**
     * Called when "Delete Friend Thread" is running.
     * <p>The method is used to remove friend from the current login user's friend list
     * and update user information, which is now with updated friend list,
     * in the webserver.
     *
     * @param friendname friend with this name in user's friend list.
     */
    public void deleteFriend(String friendname) {
        friends.remove(friendname);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }


}
