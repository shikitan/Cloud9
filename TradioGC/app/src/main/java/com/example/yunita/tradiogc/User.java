package com.example.yunita.tradiogc;

import com.example.yunita.tradiogc.friends.Friends;

public class User {

    private String username;
    private String location;
    private String email;
    private String phone;
    private Friends friends;

//    private volatile ArrayList<Observer> observers = new ArrayList<Observer>();

    public User() {
        friends = new Friends();
    }

    public User(String username) {
        this.username = username;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return username;
    }

}
