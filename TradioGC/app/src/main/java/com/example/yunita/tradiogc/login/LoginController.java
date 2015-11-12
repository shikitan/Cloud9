package com.example.yunita.tradiogc.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.user.User;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LoginController {

    private static final String TAG = "LoginController";
    private Gson gson = new Gson();
    private Context context;
    private WebServer webServer = new WebServer();

    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            ((Activity) context).finish();
        }
    };

    public LoginController() {
    }

    public LoginController(Context context) {
        this.context = context;
    }

    public void addUser(User user) {
        HttpClient httpClient = new DefaultHttpClient();

        saveUserInFile(user);
        try {
            HttpPost addRequest = new HttpPost(webServer.getResourceUrl() + user.getUsername());
            // check http://cmput301.softwareprocess.es:8080/cmput301f15t09/user/[username]

            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUserInFile(User user) {
        try {
            FileOutputStream fos = context.openFileOutput(user.getUsername() + ".sav", 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(user, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class SignUpThread extends Thread {
        private User newUser;

        public SignUpThread(User newUser) {
            this.newUser = newUser;
        }

        @Override
        public void run() {
            synchronized (this) {
                addUser(newUser);
                ((Activity) context).runOnUiThread(doFinishAdd);
                notify();
            }
        }
    }
}
