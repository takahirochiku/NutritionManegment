package jp.techacademy.chiku.takahiro.nutritionmanegment;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Nutritiondata extends RealmObject implements Serializable {
    private String age; // 年齢
    private String sex; // 性別
    private String nutrition; //栄養素
    private int amount; //推奨摂取量
    @PrimaryKey
    private int id;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.age = sex;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.age = nutrition;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
                "age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", nutrition='" + nutrition + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }
}


