package com.example.yunita.tradiogc.inventory;

import java.util.ArrayList;

/**
 * Created by dshin on 11/1/15.
 */
public class Categories {
    private ArrayList<String> categories = new ArrayList<>();

    public Categories() {
        categories.add("Books");
        categories.add("Electronics");
        categories.add("Sports");
        categories.add("Music");
        categories.add("Clothing");
        categories.add("Hobby");
        categories.add("Food");
        categories.add("Other");
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

}
