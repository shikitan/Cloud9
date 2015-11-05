package com.example.yunita.tradiogc.friends;

import com.example.yunita.tradiogc.Observable;
import com.example.yunita.tradiogc.Observer;
import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.login.LoginActivity;

import java.util.ArrayList;

public class Friends extends ArrayList<String>{

    public Friends(){

    }

    // Add newFriend to User's friend list
    public void addNewFriend(String newFriend) {
        this.add(newFriend);
    }

    // Remove otherUser from the User's friend list
    public void deleteFriend(String otherUser){
        this.remove(otherUser);
    }

    public boolean isFriend(String searchFriend){
        if (this.contains(new String(searchFriend))){
            return true;
        } else {
            return false;
        }
    }
}
