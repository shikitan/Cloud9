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

        User test_john = search.getUser("john");
        assertEquals(john, test_john);
        User test_anne = search.getUser("anne");
        assertEquals(anne, test_anne);

    }

    public void testAddFriend() {

        // Define two users and get their friend lists
        User user = new User("username");
        User john = new User("john");
        String user_name = user.getUsername();
        String john_name = john.getUsername();
        Friends friend_list_user = user.getFriends();
        Friends friend_list_john = john.getFriends();

        // Have user add the other person
        friend_list_user.addNewFriend(john_name);
        friend_list_john.addNewFriend(user_name);

        // Assert that both users have each other on their friend lists
        assertTrue(user.getFriends().contains(john));
        assertTrue(john.getFriends().contains(user));
    }

    public void testRemoveFriend() {
        // Define two users and get one of their friend lists
        User user = new User("username");
        User john = new User("john");
        String john_name = john.getUsername();
        Friends friend_list_user = user.getFriends();

        // Have user add the other person
        friend_list_user.addNewFriend(john_name);

        // Assert that user has the other person in their friend list
        assertTrue(user.getFriends().contains(john));

        // Have user delete the other person
        friend_list_user.deleteFriend(john_name);

        // Assert that both users no longer have each other on their friend lists
        assertFalse(user.getFriends().contains(john));
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
