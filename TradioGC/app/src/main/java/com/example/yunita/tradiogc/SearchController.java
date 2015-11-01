package com.example.yunita.tradiogc;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.yunita.tradiogc.data.SearchHit;
import com.example.yunita.tradiogc.data.SearchResponse;
import com.example.yunita.tradiogc.data.SimpleSearchCommand;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class SearchController {
    private static final String TAG = "SearchController";
    private Gson gson;
    private WebServer webServer = new WebServer();
    private Users users = new Users();
    private Context context;

    public Users getUsers() {
        return users;
    }

    public SearchController(Context context) {
        gson = new Gson();
        this.context = context;
    }

//    public User getUser(String username) {
//        User result = null;
//        Users allUsers = getAllUsers(null);
//        for (User user : allUsers) {
//            if (user.getUsername().equals(username)) {
//                result = user;
//            }
//        }
//        return result;
//    }



        public User getUser(String username) {
        SearchHit<User> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(webServer.getResourceUrl() + username);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<User>>() {}.getType();

        try {
            sr = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchHitType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sr.getSource();

    }


    public Users getAllUsers(String field) {
        Users result = new Users();


        HttpPost searchRequest = new HttpPost(users.getSearchUrl());

        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand("*");

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


        Type searchResponseType = new TypeToken<SearchResponse<User>>() {
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


        return result;
    }

    public Users searchUsers(String searchString) {
        Users result = new Users();
        Users allUsers = getAllUsers(null);
        for (User user : allUsers) {
            if (user.getUsername().indexOf(searchString) != -1) {
                result.add(user);
            }
        }
        result.notifyObservers();
        return result;
    }

    private Runnable doFinishLogin = new Runnable() {
        public void run() {
            ((Activity) context).finish();
        }
    };

    public class GetUserLoginThread extends Thread {
        private String username;

        public GetUserLoginThread(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            LoginActivity.USERLOGIN = getUser(username);

            ((Activity) context).runOnUiThread(doFinishLogin);
        }
    }

}

