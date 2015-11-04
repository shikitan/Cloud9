package com.example.yunita.tradiogc.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.friends.FriendsController;
import com.example.yunita.tradiogc.login.LoginActivity;

/**
 * Created by Amber on 15/11/4.
 */
public class EditProfileActivity extends AppCompatActivity{
    private EditText location_et;
    private EditText email_et;
    private EditText phone_et;
    private TextView userName;

    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        userName = (TextView) findViewById(R.id.profileName);
        location_et = (EditText) findViewById(R.id.location_et);
        email_et = (EditText) findViewById(R.id.email_et);
        phone_et = (EditText) findViewById(R.id.phone_et);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userName.setText(LoginActivity.USERLOGIN.getUsername());
        location_et.setText(LoginActivity.USERLOGIN.getLocation());
        location_et.setSelection(LoginActivity.USERLOGIN.getLocation().length());
        email_et.setText(LoginActivity.USERLOGIN.getEmail());
        email_et.setSelection(LoginActivity.USERLOGIN.getEmail().length());
        phone_et.setText(LoginActivity.USERLOGIN.getPhone());
        phone_et.setSelection(LoginActivity.USERLOGIN.getPhone().length());
    }

    public void save(View view) {
        String location = location_et.getText().toString();
        String email = email_et.getText().toString();
        String phone = phone_et.getText().toString();

        LoginActivity.USERLOGIN.setLocation(location);
        LoginActivity.USERLOGIN.setEmail(email);
        LoginActivity.USERLOGIN.setPhone(phone);

        FriendsController friendsController = new FriendsController(context);
        Thread updateUserThread = friendsController.new UpdateUserThread(LoginActivity.USERLOGIN);
        updateUserThread.start();
        synchronized (updateUserThread) {
            try {
                updateUserThread.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
