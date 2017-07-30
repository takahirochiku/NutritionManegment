package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by takahiro chiku on 2017/07/27.
 */

public class NutritiondataApp extends Application {
    @Override
        public void onCreate() {
            super.onCreate();
            Realm.init(this);
    }
}