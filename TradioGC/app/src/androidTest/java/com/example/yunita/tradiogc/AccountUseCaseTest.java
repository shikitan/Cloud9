package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class AccountUseCaseTest extends ActivityInstrumentationTestCase2 {

    public AccountUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testCreateAccount(){
        User john = new User("john", "###", "john@yahoo.com");
        UserList users = new UserList();
        users.addUser(john);

        assertEquals(users.getSize(), 1);
    }

    public void testLogin(){
        User john = new User("john", "###", "john@yahoo.com");
        john.setIsLoggedIn(true);

        assertTrue(john.isLoggedIn());
    }

    public void testLogout(){
        User john = new User("john", "###", "john@yahoo.com");
        john.setIsLoggedIn(false);

        assertFalse(john.isLoggedIn());
    }

}
