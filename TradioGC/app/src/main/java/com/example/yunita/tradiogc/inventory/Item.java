package com.example.yunita.tradiogc.inventory;

import android.opengl.Visibility;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

/**
 * Created by dshin on 10/31/15.
 */
public class Item{
    String name;
    int category;
    double price;
    String desc;
    Boolean visibility;
    int quantity;

    public Item(String name,int category, double price, String desc, Boolean visibility) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.visibility = visibility;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }
}
