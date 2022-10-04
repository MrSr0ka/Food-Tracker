package com.example.n0rchdesign;

public class Data {
    private String item, date, id, productName;
    private int calories;


    public Data(String item, String date, String id, String productName, int calories) {
        this.item = item;
        this.date = date;
        this.id = id;
        this.productName = productName;
        this.calories = calories;
    }
    public Data() {}

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
