package com.example.yunita.tradiogc.friends;

import java.util.ArrayList;

public class Friends extends ArrayList<String> {

    public Friends() {

    }

    // Add newFriend to User's friend list
    public void addNewFriend(String friendname) {
        this.add(friendname);
    }

    // Remove otherUser from the User's friend list
    public void deleteFriend(String friendname) {
        this.remove(friendname);
    }

}
