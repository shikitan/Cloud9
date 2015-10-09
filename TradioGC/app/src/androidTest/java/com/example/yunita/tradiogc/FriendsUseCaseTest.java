package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class FriendsUseCaseTest extends ActivityInstrumentationTestCase2 {

    public FriendsUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testSearchUserName() {
        User john = new User("john");
        User anne = new User("anne");

        UserList users = new UserList();
        users.addUser(john);
        users.addUser(anne);

        assertTrue(users.isExist("anne"));
    }

    public void testAddFriend() {
        User user = new User("username");
        User owner = new Owner("John");

        owner.addFriend(user);
        user.addFriend(owner);

        assertTrue(owner.getFriends().contains(user));
        assertTrue(user.getFriends().contains(owner));
    }

    public void testRemoveFriend() {
        User user = new User("username");
        User owner = new Owner("John");

        owner.addFriend(user);
        user.addFriend(owner);

        owner.removeFriend(user);
        user.removeFriend(owner);

        assertFalse(owner.getFriends().contains(user));
        assertFalse(user.getFriends().contains(owner));
    }

    public void testViewPersonalProfile(){
        User user = new User(username, password);
        Profile profile = new Profile();
        profile.setLocation("location");
        profile.setPhoneNumber("1001001000");
        user.addProfile(profile);

        assertTrue(user.getProfile().equals(profile));
    }

    public void testViewOtherProfile(){
        User user = new User(username, password);
        Profile profile = new Profile();
        profile.setLocation("location");
        profile.setPhoneNumber("1001001000");
        user.addProfile(profile);

        assertTrue(user.getProfile().equals(profile));
    }

}
