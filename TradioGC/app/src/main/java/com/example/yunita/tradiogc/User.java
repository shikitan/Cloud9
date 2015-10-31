package com.example.yunita.tradiogc;

import com.example.yunita.tradiogc.friends.Friends;

import java.util.ArrayList;

public class User{

    private String username;
    private Friends friends;

    public User() {
        friends = new Friends();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

}
