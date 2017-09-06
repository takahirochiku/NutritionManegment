package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.devalon.stella.rakutenapiclient.ApiSelecter;
import biz.devalon.stella.rakutenapiclient.Rakuten;


public class RecipeRanking extends AppCompatActivity {

    /**
     * private final String mFormat = "json";
     * private final int mFormatVersion = 1;
     * private final String mAppicationId = "1093116609709872158";
     * private final String mCategoryId = "10";
     */

    JSONObject mJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Rakuten.initialize(this, "1093116609709872158");
        Rakuten.setLogging("test", Log.DEBUG);

        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", "テスト");

        Rakuten
                .create(ApiSelecter.RecipeCategoryRanking)
                .setCallback(callback)
                .get(params);
    }

    //private List<RecipeRankingData> recipeRankingList = new ArrayList<>();
        FutureCallback<JsonObject> callback = new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                Log.d("TEST","result:"+result);

                if (result.get("result") != null) {
                    JsonArray resultArray = result.getAsJsonArray("result");
                    for(int i = 0; i < resultArray.size(); i++){
                        JsonObject objectrDataJson=(JsonObject) resultArray.get(i);
                        //オブジェクト内のデータの取得
                        Log.d("TEST",objectrDataJson.get("recipeUrl").toString());

                        //System.out.println(objectrDataJson.get("c"));
                        //System.out.println(objectrDataJson.get("e"));
                    }
                }

                /**JsonArray resultArray = result.getAsJsonArray("result");
                int count = resultArray.size();
                Log.d("TEST","count:"+count);

                JSONObject[] recipeObject = new JSONObject[count];
                Log.d("TEST","recipeObject:"+recipeObject);

                for (int i=0; i<count; i++){
                    recipeObject[i] = resultArray.getAsJsonObject(i);
                }

                for (int i=0; i<recipeObject.length; i++){
                    String title =recipeObject[i].getString("recipeTitle");
                }

                //List<Map> items = (List<Map>) result.get("items");
                /**for (Map result: result) {
                    String title = (String) result.get("recipeTitle");
                    String recipeUrl = (String) result.get("recipeUrl");
                    String imageUrl = (String) result.get("smallImageUrl");
                    String time = (String) result.get("recipeIndication");
                    String cost = (String) result.get("recipeCost");
                    Log.d("TEST","title:"+title);
                }*/
            }
        };
}




