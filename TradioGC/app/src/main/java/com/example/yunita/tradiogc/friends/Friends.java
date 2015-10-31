package com.example.yunita.tradiogc.friends;

import com.example.yunita.tradiogc.Observable;
import com.example.yunita.tradiogc.Observer;
import com.example.yunita.tradiogc.User;

import java.util.ArrayList;

public class Friends extends ArrayList<User>{

    public Friends(){

    }

    public void addNewFriend(User newFriend) {
        this.add(newFriend);
    }

}
