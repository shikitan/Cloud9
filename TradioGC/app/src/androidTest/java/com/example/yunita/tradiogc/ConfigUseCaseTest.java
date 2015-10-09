package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class ConfigUseCaseTest extends ActivityInstrumentationTestCase2 {

    public ConfigUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }
    
    public void testEditProfile(){
        User user = new User(username, password);
        Profile profile = new Profile();
        profile.setLocation("location");
        profile.setPhoneNumber("1001001000");
        user.addProfile(profile);


        assertTrue(user.getProfile().getLocation().equals("location"));
        assertTrue(user.getProfile().getPhoneNumber().equals("1001001000"));
    }

}
