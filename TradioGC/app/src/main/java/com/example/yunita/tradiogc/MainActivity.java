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
    private EditText password_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginController = new LoginController();

        login_view = (LinearLayout) findViewById(R.id.login_view);
        signup_view = (LinearLayout) findViewById(R.id.signUp_view);

        username_et = (EditText) findViewById(R.id.usernameEditText);
        password_et = (EditText) findViewById(R.id.passwordEditText);

    }

    public void goToSignUp(View view){
        Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.pull_down);
        login_view.setVisibility(View.GONE);
        signup_view.setVisibility(View.VISIBLE);
        signup_view.startAnimation(anim);
    }

    public void goToLogin(View view){
        signup_view.setVisibility(View.GONE);
        login_view.setVisibility(View.VISIBLE);
    }

    public void signUp(View view){
        String username = username_et.getText().toString();
        String password = password_et.getText().toString();

        // Execute the thread
        Thread thread = new CreateAccountThread(username, password);
        thread.start();
    }

    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    class CreateAccountThread extends Thread {
        private String username;
        private String password;

        public CreateAccountThread(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public void run() {
            loginController.addUser(username, password);

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            runOnUiThread(doFinishAdd);
        }
    }
}
