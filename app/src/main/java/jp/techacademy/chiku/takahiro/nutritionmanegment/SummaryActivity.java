package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.formatter.IFillFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mCalciumAmount2;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mCalciumSum;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mCalorieAmount2;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mCalorieSum;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mFiberAmount2;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mFiberSum;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mProteinAmount2;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.MainActivity.mProteinSum;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.LessProtein;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.MuchProtein;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.LessCalorie;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.MuchCalorie;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.LessFiber;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.MuchFiber;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.LessCalcium;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.MuchCalcium;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.today_text2;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.id.today_text3;

public class SummaryActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    double mVolume;
    TextView ProteinLess,ProteinMuch,ProteinLessWords,ProteinMuchWords;
    TextView CalorieLess,CalorieMuch,CalorieLessWords,CalorieMuchWords;
    TextView FiberLess,FiberMuch,FiberLessWords,FiberMuchWords;
    TextView CalciumLess,CalciumMuch,CalciumLessWords,CalciumMuchWords;
    private ListView mListView;
    private ArrayAdapter<CharSequence> adapter;
    private SummaryAdapter mSummaryAdapter;
    private AutoCompleteTextView mMealsResearch;
    public static String mMealsText;
    private Button mSearchButton,mSiteRecipeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Summary");

        mSummaryAdapter = new SummaryAdapter(SummaryActivity.this);
        mListView = (ListView) findViewById(R.id.listView1);

        adapter = ArrayAdapter.createFromResource(this, R.array.list4, android.R.layout.simple_dropdown_item_1line);
        mMealsResearch = (AutoCompleteTextView)findViewById(R.id.wishingmeals_edittext);
        mMealsResearch.setAdapter(adapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected (MenuItem item){
                int id = item.getItemId();

                if (id == R.id.nav_meals) {
                    mToolbar.setTitle("Today");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_nuetrition) {
                    mToolbar.setTitle("栄養");
                    Log.d("TESTEST", "この機能は未だ作り途中です");
                } else if (id == R.id.nav_saumary) {
                    mToolbar.setTitle("Summary");
                    Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        mSearchButton = (Button) findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                                 im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                                 mMealsText = mMealsResearch.getText().toString();

                                                 if (v.getId() == R.id.search_button) {
                                                     if (mMealsResearch.length() != 0) {
                                                         Realm mRealm = Realm.getDefaultInstance();
                                                         RealmQuery<InputData> query = mRealm.where(InputData.class);
                                                         query.equalTo("Meals", mMealsText);
                                                         RealmResults<InputData> result1 = query.findAll();
                                                         mSummaryAdapter.setSummaryList(mRealm.copyFromRealm(result1));
                                                         mListView.setAdapter(mSummaryAdapter);
                                                         mSummaryAdapter.notifyDataSetChanged();
                                                     }else{
                                                         View view = findViewById(android.R.id.content);
                                                         Snackbar.make(view, "入力されていません", Snackbar.LENGTH_LONG).show();
                                                     }
                                                 }
                                             }
                                         });

        mSiteRecipeButton = (Button) findViewById(R.id.siterecipe_button);
        mSiteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Resipesite.class);
                startActivity(intent);
            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ジャンルを選択していない場合（mGenre == 0）はエラーを表示するだけ
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

        summaryviewLess("Protein",mProteinSum,mProteinAmount2,ProteinLess,ProteinLessWords,LessProtein,today_text2);
        summaryviewLess("Calorie",mCalorieSum,mCalorieAmount2,CalorieLess,CalorieLessWords,LessCalorie,today_text2);
        summaryviewLess("Fiber",mFiberSum,mFiberAmount2,FiberLess,FiberLessWords,LessFiber,today_text2);
        summaryviewLess("Calcium",mCalciumSum,mCalciumAmount2,CalciumLess,CalciumLessWords,LessCalcium,today_text2);
        summaryviewMuch("Protein",mProteinSum,mProteinAmount2,ProteinMuch,ProteinMuchWords,MuchProtein,today_text3);
        summaryviewMuch("Calorie",mCalorieSum,mCalorieAmount2,CalorieMuch,CalorieMuchWords,MuchCalorie,today_text3);
        summaryviewMuch("Fiber",mFiberSum,mFiberAmount2,FiberMuch,FiberMuchWords,MuchFiber,today_text3);
        summaryviewMuch("Calcium",mCalciumSum,mCalciumAmount2,CalciumMuch,CalciumMuchWords,MuchCalcium,today_text3);

        TextView dateText = (TextView)findViewById(R.id.today_text1) ;
        InputActivity.calendar();
        String today = InputActivity.year+"年"+InputActivity.month+"月"+InputActivity.day+"月";
        dateText.setText(today);

        // ナビゲーションドロワーの設定
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_meals) {
                    mToolbar.setTitle("Today");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_nuetrition) {
                    mToolbar.setTitle("栄養");
                    Log.d("TESTEST", "この機能は未だ作り途中です");
                } else if (id == R.id.nav_saumary) {
                    mToolbar.setTitle("サマリー");
                    Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                    startActivity(intent);

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //getRecipeRanking();
    }

    /**private void getRecipeRanking() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("format", json);
        params.put("formatVersiont",1);
        params.put("hits", 30);
        params.put("applicationId", applicationId);
        if (!affiliateId.isEmpty())
            params.put("affiliateId", affiliateId);



    }*/

    private void summaryviewLess(String NutritionName, int actualSum, String recommendedAmount, TextView textLess,TextView textLessWords,int textViewLessid,int textViewLessWordsid) {

        int recommendedAmount2 =Integer.parseInt(recommendedAmount);
        mVolume =(double)actualSum/recommendedAmount2;

        textLess =(TextView)findViewById(textViewLessid);
        textLessWords =(TextView)findViewById(textViewLessWordsid);
        if (mVolume <=0.75){
            textLess.setText(NutritionName+" ");
        }else if(mVolume >= 1.50){
            textLess.setVisibility(View.INVISIBLE);
            textLessWords.setVisibility(View.INVISIBLE);
        }
        Log.d("TEST","actualSum:"+actualSum);
        Log.d("TEST","recommendedAmount2:"+recommendedAmount2);
        Log.d("TEST","mVolume:"+mVolume);
    }

    private void summaryviewMuch(String NutritionName, int actualSum, String recommendedAmount, TextView textMuch,TextView textMuchWords,int textViewMuchid,int textViewMuchWordsid) {

        int recommendedAmount2 =Integer.parseInt(recommendedAmount);
        mVolume =(double)actualSum/recommendedAmount2;

        textMuch =(TextView)findViewById(textViewMuchid);
        textMuchWords =(TextView)findViewById(textViewMuchWordsid);
        if (mVolume <0.75){
            textMuch.setVisibility(View.INVISIBLE);
            textMuchWords.setVisibility(View.INVISIBLE);
        }else if(mVolume > 1.50){
            textMuch.setText(NutritionName);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

