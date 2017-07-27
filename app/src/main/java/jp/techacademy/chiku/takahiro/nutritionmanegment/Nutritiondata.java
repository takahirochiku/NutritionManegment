package jp.techacademy.chiku.takahiro.nutritionmanegment;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by takahiro chiku on 2017/07/27.
 */

public class Nutritiondata extends RealmObject {
    private String age; // 年齢
    private String sex; // 性別
    private String nutrition; //栄養素
    private int amount; //推奨摂取量

    @PrimaryKey
    private int id;

    public String getAge() {
        return age;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}


