package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

import com.example.yunita.tradiogc.user.User;

public class ConfigurationUseCaseTest extends ActivityInstrumentationTestCase2 {

    public ConfigurationUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testEditProfile(){
        User ann = new User();
        ann.setUsername("ann");
        ann.setLocation("edmonton");
        ann.setPhone("7809998881");
        ann.setEmail("ann@yahoo.com");

        // modify location
        ann.setLocation("calgary");

        assertFalse(ann.getLocation().equals("edmonton"));
    }

    public void testViewPersonalProfile(){
        User ann = new User();
        ann.setUsername("ann");
        ann.setLocation("edmonton");
        ann.setPhone("7809998881");
        ann.setEmail("ann@yahoo.com");

        assertTrue(ann.getUsername().equals("ann"));
        assertTrue(ann.getLocation().equals("edmonton"));
        assertTrue(ann.getPhone().equals("7809998881"));
        assertTrue(ann.getEmail().equals("ann@yahoo.com"));
    }

    public void testDisablePhotographDownload(){

    }

    public void testEnablePhotographDownload(){

    }

}
