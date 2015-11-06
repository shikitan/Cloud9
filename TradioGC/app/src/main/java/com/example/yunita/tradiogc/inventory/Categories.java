package com.example.yunita.tradiogc.inventory;

import java.util.ArrayList;

/**
 * Created by dshin on 11/1/15.
 */
public class Categories {
    private ArrayList<String> categories;

    public ArrayList<String> getCategories() {
        categories.add("books");
        categories.add("electronics");
        categories.add("food");
        categories.add("sports");
        categories.add("music");
        categories.add("clothing");
        categories.add("hobby");
        categories.add("other");
        return categories;
    }

    public Categories(ArrayList<String> categories) {
        this.categories = categories;
    }

}
