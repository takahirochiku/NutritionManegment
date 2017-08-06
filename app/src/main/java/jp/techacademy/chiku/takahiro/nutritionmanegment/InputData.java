package jp.techacademy.chiku.takahiro.nutritionmanegment;


import java.sql.Date;

import io.realm.annotations.PrimaryKey;

public class InputData {
    private Date Date; //登録日
    private String TimeZone; //摂取タイミング
    private String Category; //Mealsのカテゴリ
    private String Meals; //摂取物
    private int Amount; //摂取量
    @PrimaryKey
    private int id;

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public String getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(String timeZone) {
        TimeZone = timeZone;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getMeals() {
        return Meals;
    }

    public void setMeals(String meals) {
        Meals = meals;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


