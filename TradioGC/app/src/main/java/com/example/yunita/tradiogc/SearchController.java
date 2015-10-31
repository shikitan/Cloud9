package com.example.yunita.tradiogc;

import android.content.Context;
import android.util.Log;

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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by shuming2 on 10/31/15.
 */
public class SearchController {
    private static final String TAG = "SearchController";
    private Gson gson = new Gson();
    private WebServer webServer = new WebServer();
    private Users users = new Users();

    public Users getUsers() {
        return users;
    }
    public SearchController() {
        gson = new Gson();
    }

    /*public Users searchUsers(String searchString, String field) {
        User user = new User();
        user.setUsername("john");
        users.add(user);
        return users;

    }
    */


    public Users searchUsers(String searchString, String field) {
        Users result = new Users();


        HttpPost searchRequest = new HttpPost(users.getSearchUrl());

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

        result.notifyObservers();

        return result;
    }

}
