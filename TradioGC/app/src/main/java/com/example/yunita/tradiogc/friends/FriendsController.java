package com.example.yunita.tradiogc.friends;

import android.content.Context;

import com.example.yunita.tradiogc.inventory.Item;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.user.User;
import com.example.yunita.tradiogc.user.UserController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class FriendsController {

    private static final String TAG = "FriendsController";
    private UserController userController;
    private Context context;
    private Gson gson;

    /**
     * Class constructor specifying that this controller class is a subclass of Context.
     *
     * @param context
     */
    public FriendsController(Context context) {
        super();
        this.context = context;
        userController = new UserController(context);
    }

    /**
     * Called when the user clicks the "Add Friend" button.
     * <p>This method is used to add a friend to the current login user's friend list
     * and update the user's new friend list to the webserver.
     *
     * @param friendname new friend's name
     */
    public void addFriend(String friendname) {
        LoginActivity.USERLOGIN.getFriends().add(friendname);
        Collections.sort(LoginActivity.USERLOGIN.getFriends(),String.CASE_INSENSITIVE_ORDER);
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    /**
     * Called when "Delete Friend Thread" is running
     * <p>This method is used to remove a friend from the current login user's friend list
     * and update the user's new friend list to the webserver.
     *
     * @param friendname friend with this name in the user's friend list
     */
    public void deleteFriend(String friendname) {
        LoginActivity.USERLOGIN.getFriends().remove(friendname);
        System.out.println("login friends = " + LoginActivity.USERLOGIN.getFriends().size());
        Thread updateUserThread = userController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
    }

    /**
     * Called when
     * This method saves the inventory to the local storage.
     *
     */
    private void saveFriendsInFile(Friends friends, User user){
        try{
            FileOutputStream fos = context.openFileOutput(user.getUsername() + "friends.sav", 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(friends, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Friends loadFriendsFromFile(Friends friends, User user){
        try{
            FileInputStream fis = context.openFileInput(user.getUsername() + "friends.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
            friends = gson.fromJson(in, listType);
            return friends;
        } catch (FileNotFoundException e) {
            friends = new Friends();
            return friends;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
