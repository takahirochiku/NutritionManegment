package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import io.realm.Sort;

import static java.lang.Integer.parseInt;


public class RecipeRanking extends AppCompatActivity {

    /**private RealmChangeListener mRealmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            reloadListView();
        }
    };*/

    private Realm mRealm;
    private ListView mRecipeListView;
    private RecipeRankingAdapter mRecipeRankingAdapter;
    private String mTitle,mRecipeUrl,mImageUrl,mTime,mCost;
    private int mRank;

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

        //Realm realm = Realm.getDefaultInstance();
        //realm.beginTransaction();

        //mRecipeRankingAdapter = new RecipeRankingAdapter(RecipeRanking.this);
        mRecipeListView = (ListView) findViewById(R.id.listView1);

        mRecipeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Resipesite.class);
                startActivity(intent);

                return true;
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

        addRecipeRanking();

        reloadListView();

         mRecipeRankingAdapter =new RecipeRankingAdapter(RecipeRanking.this);
         mRecipeListView =(ListView) findViewById(R.id.listView1);
    }

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
                        mTitle = objectrDataJson.get("recipeTitle").toString();
                        mRecipeUrl = objectrDataJson.get("recipeUrl").toString();
                        mImageUrl = objectrDataJson.get("smallImageUrl").toString();
                        mTime = objectrDataJson.get("recipeIndication").toString();
                        mCost = objectrDataJson.get("recipeCost").toString();
                        String rank = objectrDataJson.get("rank").toString();
                        mRank = Integer.parseInt(rank);
                        Log.d("TEST","title:"+mTitle);
                        Log.d("TEST","recipeUrl:"+mRecipeUrl);
                        Log.d("TEST","imageUrl:"+mImageUrl);
                        Log.d("TEST","time:"+mTime);
                        Log.d("TEST","cost:"+mCost);
                    }
                }
            }
    };

    private void addRecipeRanking() {

        mRealm = Realm.getDefaultInstance();
        mRrealm.beginTransaction();

        RecipeRankingData reciperankingdata = new RecipeRankingData();
        reciperankingdata.setRecipeTitle(mTitle);
        reciperankingdata.setRecipeUrl(mRecipeUrl);
        reciperankingdata.setSmallImageUrl(mImageUrl);
        reciperankingdata.setRecipeIndication(mTime);
        reciperankingdata.setRecipeCost(mCost);
        reciperankingdata.setRank(mRank);


        mRrealm.copyToRealmOrUpdate(reciperankingdata);
        mRrealm.commitTransaction();

        realm.close();

    }

    private void reloadListView() {

        Realm mRealm = Realm.getDefaultInstance();

        // Realmデータベースから、「全てのデータを取得して新しい日時順に並べた結果」を取得
        RealmResults<RecipeRankingData> recipeRealmResults = mRealm.where(RecipeRankingData.class).findAllSorted("Rank", Sort.DESCENDING);
        mRecipeRankingAdapter.setRecipeRankingList(mRealm.copyFromRealm(recipeRealmResults));
        // TaskのListView用のアダプタに渡す
        mRecipeListView.setAdapter(mRecipeRankingAdapter);
        // 表示を更新するために、アダプターにデータが変更されたことを知らせる
        mRecipeRankingAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }
}




