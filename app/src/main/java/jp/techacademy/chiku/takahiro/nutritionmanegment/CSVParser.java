package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.wifi.WifiConfiguration;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by takahiro chiku on 2017/07/27.
 */

public class CSVParser {

    public static void parse(Context context) {
        // AssetManagerの呼び出し
        AssetManager assetManager = context.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream recomendeddata = assetManager.open("");
            InputStreamReader inputStreamReader = new InputStreamReader(recomendeddata);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                // 各行が","で区切られていて4つの項目があるとする
                StringTokenizer st = new StringTokenizer(line, ",");
                String age = st.nextToken();
                String sex = st.nextToken();
                String nutrition = st.nextToken();
                String fourth = st.nextToken();
                int amount = Integer.parseInt(fourth);
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
