package com.example.yunita.tradiogc.inventory;

import java.util.ArrayList;

/**
 * Created by dshin on 11/1/15.
 */
public class Categories {
    private ArrayList<String> categories = new ArrayList<>();

    public Categories() {
        categories.add("books");
        categories.add("electronics");
        categories.add("food");
        categories.add("sports");
        categories.add("music");
        categories.add("clothing");
        categories.add("hobby");
        categories.add("other");
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

}
