package com.example.yunita.tradiogc.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.User;
import com.example.yunita.tradiogc.Users;
import com.example.yunita.tradiogc.WebServer;
import com.example.yunita.tradiogc.data.SearchHit;
import com.example.yunita.tradiogc.data.SearchResponse;
import com.example.yunita.tradiogc.data.SimpleSearchCommand;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class LoginController {

    private static final String TAG = "LoginController";
    private Gson gson = new Gson();
    private User newUser;
    private Context context;
    private WebServer webServer = new WebServer();

    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            ((Activity) context).finish();
        }
    };

    public LoginController(Context context) {
        this.context = context;
        newUser = new User();
    }

    public void addUser(String username) {
        newUser.setUsername(username);
        HttpClient httpClient = new DefaultHttpClient();

        saveUserInFile(username);
        try {
            HttpPost addRequest = new HttpPost(webServer.getResourceUrl() + username);
            // check http://cmput301.softwareprocess.es:8080/cmput301f15t09/user/[username]

            StringEntity stringEntity = new StringEntity(gson.toJson(newUser));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Users searchUsers(String searchString, String field) {
        Users result = new Users();

        /**
         * Creates a search request from a search string and a field
         */

        HttpPost searchRequest = new HttpPost(webServer.getSearchUrl());

        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);
        Log.i(TAG, "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parses the response of a search
         */
        Type searchResponseType = new TypeToken<SearchResponse<Users>>() {
        }.getType();

        SearchResponse<User> esResponse;

        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (SearchHit<User> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }

        result.notifyObservers();

        return result;
    }

    private void saveUserInFile(String username) {
        try {
            FileOutputStream fos = context.openFileOutput( username + ".sav", 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(newUser, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class SignUpThread extends Thread {
        private String username;

        public SignUpThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            addUser(username);
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ((Activity) context).runOnUiThread(doFinishAdd);
        }
    }

}
