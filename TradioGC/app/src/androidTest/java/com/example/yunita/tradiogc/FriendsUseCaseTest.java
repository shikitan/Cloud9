package com.example.yunita.tradiogc;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.example.yunita.tradiogc.friends.Friends;
import com.example.yunita.tradiogc.login.LoginActivity;

public class FriendsUseCaseTest extends ActivityInstrumentationTestCase2 {

    private Context context;
    private Friends thisUserFriends = LoginActivity.USERLOGIN.getFriends();

    public FriendsUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSearchUserName() {
        User john = new User("john");
        User anne = new User("anne");

        SearchController search = new SearchController(context);
        Users user_list = search.getUser("john");
        Users user_list2 = search.getAllUsers("an");
        Users user_list3 = search.getAllUsers("123");

        assertTrue(user_list.contains("john"));
        assertTrue(user_list2.contains("anne"));
        assertFalse(user_list3.contains("123"));
    }

    public void testAddFriend() {

        // Define two users and get one of their friend lists
        User user = new User("username");
        User john = new User("john");
        Friends friend_list_user = user.getFriends();
        Friends friend_list_john = john.getFriends();

        // Have user add the other person
        friend_list_user.addNewFriend(john);
        friend_list_john.addNewFriend(user);

        System.out.println(friend_list_user);
        // Assert that both users have each other on their friend lists
        assertTrue(user.getFriends().contains(john));
        assertTrue(john.getFriends().contains(user));
    }

    public void testRemoveFriend() {
        // Define two users and get one of their friend lists
        User user = new User("username");
        User john = new User("john");
        Friends friend_list_user = user.getFriends();

        // Have user add the other person
        friend_list_user.addNewFriend(john);

        // Assert that both users have each other on their friend lists
        assertTrue(user.getFriends().contains(john));
        assertTrue(john.getFriends().contains(user));

        // Have user delete the other person
        friend_list_user.deleteFriend(john);

        // Assert that both users no longer have each other on their friend lists
        assertFalse(user.getFriends().contains(john));
        assertFalse(john.getFriends().contains(user));
    }

    //public void testViewPersonalProfile(){
        //User user = new User(username, password);
        //Profile profile = new Profile();
        //profile.setLocation("location");
        //profile.setPhoneNumber("1001001000");
        //user.addProfile(profile);

        //assertTrue(user.getProfile().equals(profile));
    //}

    //public void testViewOtherProfile(){
        //User user = new User(username, password);
        //Profile profile = new Profile();
        //profile.setLocation("location");
        //profile.setPhoneNumber("1001001000");
        //user.addProfile(profile);

        //assertTrue(user.getProfile().equals(profile));
    //}

}
