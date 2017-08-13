package jp.techacademy.chiku.takahiro.nutritionmanegment;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InputData extends RealmObject implements Serializable {
    private String Category; //Mealsのカテゴリ
    private String Meals; //摂取物
    private String Nutrition;
    private double Amount; //摂取量

    @PrimaryKey
    private int id;

    public String getNutrition() {
        return Nutrition;
    }

    public void setNutrition(String nutrition) {
        Nutrition = nutrition;
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

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Nutritiondata{" +
                "category='" + Category + '\'' +
                ", meals='" + Meals + '\'' +
                ", nutrition='" + Nutrition + '\'' +
                ", amount=" + Amount +
                '}';
    }
}