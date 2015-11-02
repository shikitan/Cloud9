package com.example.yunita.tradiogc.friends;

import com.example.yunita.tradiogc.Observable;
import com.example.yunita.tradiogc.Observer;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.login.LoginActivity;

import java.util.ArrayList;

public class Friends extends ArrayList<User>{

    public Friends(){

    }

    public void addNewFriend(User newFriend) {

        // Add newFriend to User's friend list
        this.add(newFriend);

        // Add User to newFriend's friend list
        Friends otherUserFriends = newFriend.getFriends();
        otherUserFriends.add(LoginActivity.USERLOGIN);

    }

    public void deleteFriend(User otherUser){

        // Remove otherUser from the User's friend list
        this.remove(otherUser);

        // Remove User from otherUser's friend list
        Friends otherUserFriends = otherUser.getFriends();
        otherUserFriends.remove(LoginActivity.USERLOGIN);
    }

}
