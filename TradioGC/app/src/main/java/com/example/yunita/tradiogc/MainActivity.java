package com.example.yunita.tradiogc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private Context mContext = this;
    private LoginController loginController;

    private LinearLayout login_view;
    private LinearLayout signup_view;
    private EditText username_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginController = new LoginController(mContext);

        login_view = (LinearLayout) findViewById(R.id.login_view);
        signup_view = (LinearLayout) findViewById(R.id.signUp_view);

        username_et = (EditText) findViewById(R.id.usernameEditText);

    }

    public void goToSignUp(View view) {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.pull_down);
        login_view.setVisibility(View.GONE);
        signup_view.setVisibility(View.VISIBLE);
        signup_view.startAnimation(anim);
    }

    public void goToLogin(View view) {
        signup_view.setVisibility(View.GONE);
        login_view.setVisibility(View.VISIBLE);
    }

    public void signUp(View view) {
        String username = username_et.getText().toString();

        // Execute the thread
        Thread thread = loginController.new CreateAccountThread(username);
        thread.start();
    }


}
