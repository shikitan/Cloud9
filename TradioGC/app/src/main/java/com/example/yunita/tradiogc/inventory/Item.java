package com.example.yunita.tradiogc.inventory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Item implements Serializable {
    private String name;
    private int category;
    private double price;
    private String desc;
    private Boolean visibility;
    private int quantity;
    private int quality;

    public Item() {

    }

    public Item(String name, int category, double price, String desc, Boolean visibility, int quantity, int quality) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.visibility = visibility;
        this.quality = quality;
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

    public String getPrice() {
        return Double.toString(round(price, 3));
    }

    //TODO: fix the price to print in two decimal points

    public void setPrice(double price) {
        this.price = round(price, 2);
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return this.name + "\n$" + round(this.price, 2);
    }

    //http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
