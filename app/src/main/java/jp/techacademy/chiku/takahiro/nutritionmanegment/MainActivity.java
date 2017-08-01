package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.OrientedBoundingBox;
import android.graphics.Color;
import android.graphics.RectF;
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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import android.content.res.AssetManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;


public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Toolbar mToolbar;


    /*private RealmChangeListener mRealmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            reloadListView();
        }
    };*/

    protected HorizontalBarChart mProteinChart;
    protected HorizontalBarChart mCalorieChart;
    protected HorizontalBarChart mFiverChart;
    protected HorizontalBarChart mCalcuimChart;

    public String Calorie_cal;
    public String Protein_g;
    public String Fiber_g;
    public String Calcium_mg;

    private Nutritiondata mNutritiondataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("トップ");

        mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart2);
        mProteinChart.setOnChartValueSelectedListener(this);
        mCalorieChart = (HorizontalBarChart) findViewById(R.id.calorie_chart2);
        mCalorieChart.setOnChartValueSelectedListener(this);
        mFiverChart = (HorizontalBarChart) findViewById(R.id.protein_chart2);
        mFiverChart.setOnChartValueSelectedListener(this);
        mCalcuimChart = (HorizontalBarChart) findViewById(R.id.protein_chart2);
        mCalcuimChart.setOnChartValueSelectedListener(this);

        // mChart.setHighlightEnabled(false);

        //意味を確認する
        mProteinChart.setDrawBarShadow(false);
        mProteinChart.setDrawValueAboveBar(true);
        mProteinChart.setPinchZoom(false);
        //mProteinChart.setMaxVisibleValueCount(60);
        mProteinChart.setDrawGridBackground(false);
        mProteinChart.getDescription().setEnabled(false);
        mProteinChart.setPinchZoom(false);
        mProteinChart.setDrawGridBackground(false);

        mCalorieChart.setDrawBarShadow(false);
        mCalorieChart.setDrawValueAboveBar(true);
        mCalorieChart.setPinchZoom(false);
        //mProteinChart.setMaxVisibleValueCount(60);
        mCalorieChart.setDrawGridBackground(false);
        mCalorieChart.getDescription().setEnabled(false);
        mCalorieChart.setPinchZoom(false);
        mCalorieChart.setDrawGridBackground(false);

        XAxis xl = mProteinChart.getXAxis();
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(true);
        xl.setDrawLabels(false);
        xl.setGranularity(30f);

        XAxis xm = mCalorieChart.getXAxis();
        //xl.setTypeface(mTfLight);
        xm.setDrawAxisLine(false);
        xm.setDrawGridLines(true);
        xm.setDrawLabels(false);
        xm.setGranularity(30f);


        YAxis yl = mProteinChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis ym = mCalorieChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        ym.setDrawAxisLine(false);
        ym.setDrawGridLines(false);
        ym.setDrawLabels(false);
        ym.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mProteinChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(true);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        YAxis ys = mCalorieChart.getAxisRight();
        ys.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //yr.setTypeface(mTfLight);
        ys.setDrawAxisLine(false);
        ys.setDrawGridLines(false);
        ys.setDrawLabels(true);
        ys.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        Legend l = mProteinChart.getLegend();
        //l.setFormSize(5f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //l.setTypeface(...);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        //l.setXEntrySpacxe(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        Legend m = mCalorieChart.getLegend();
        //l.setFormSize(5f); // set the size of the legend forms/shapes
        m.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        m.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //l.setTypeface(...);
        m.setTextSize(12f);
        m.setTextColor(Color.BLACK);
        //l.setXEntrySpacxe(5f); // set the space between the legend entries on the x-axis
        m.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        mProteinChart.setData(createHorizontalBarChartData1());
        mProteinChart.setFitBars(true);
        mProteinChart.invalidate();
        mProteinChart.animateY(2000);

        mCalorieChart.setData(createHorizontalBarChartData2());
        mCalorieChart.setFitBars(true);
        mCalorieChart.invalidate();
        mCalorieChart.animateY(2000);

        readNutritionData();

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

        // ナビゲーションドロワーの設定
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected (MenuItem item){
                int id = item.getItemId();

                if (id == R.id.nav_meals) {
                    mToolbar.setTitle("食事");
                    Log.d("TESTEST", "この機能は未だ作り途中です");
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

        //addTaskForTest();

        //reloadListView();

    }

    //予めモデルクラスを作成する事(NutritionData.class)
    private List<Nutritiondata> nutritionLists = new ArrayList<>();
    private void readNutritionData() {
        //Androidstudioはthisが必須,eclipceはいらないらしい
        InputStream is =  this.getResources().openRawResource(R.raw.recomendednutritiondata);
         //リーダーを設定
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
         //リーダー用の変数を宣言、Log.dでエラーにならないよう""を設定
        String line ="";
        try{
            //readLineで一行ずつでブレイクする
            while ((line = reader.readLine()) != null){
                //エラーが発生した際にどの行で止まったのか見るLog
                Log.d("TESTTEST","Line:"+line);
                //tokensとsplitで分割する
                String[] tokens =line.split(",");
                Nutritiondata standerdlist = new Nutritiondata();
                standerdlist.setAge(tokens[0]);
                standerdlist.setSex(tokens[1]);
                standerdlist.setNutrition(tokens[2]);
                standerdlist.setAmount(Integer.parseInt(tokens[3]));
                nutritionLists.add(standerdlist);

                Log.d("TESTTEST","Just created:"+standerdlist);
            }
        } catch (IOException e) {
            //エラーが発生した際にどの行が原因か見るLog
            Log.d("TESTTEST","Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }



    /*private void reloadListView() {
        // Realmデータベースから取得
        RealmResults<Nutritiondata> taskRealmResults1 = mRealm.where(Nutritiondata.class).filter("nutrition =='Calorie_cal'","age == '18～29'","sex == '男性'" );
        // 上記の結果を、Calorie_cal としてセットする
        Calorie_cal = mRealm.copyFromRealm(taskRealmResults1);

        RealmResults<Nutritiondata> taskRealmResults2 = mRealm.where(Nutritiondata.class).filter("nutrition =='Protein_g","age == '18～29'","sex == '男性'" );
        Protein_g = mRealm.copyFromRealm(taskRealmResults2);

        RealmResults<Nutritiondata> taskRealmResults3 = mRealm.where(Nutritiondata.class).filter("nutrition =='Fiber_g","age == '18～29'","sex == '男性'");
        Fiber_g = mRealm.copyFromRealm(taskRealmResults3);

        RealmResults<Nutritiondata> taskRealmResults4 = mRealm.where(Nutritiondata.class).filter("nutrition =='Calcium_mg","age == '18～29'","sex == '男性'" );
        Calcium_mg = mRealm.copyFromRealm(taskRealmResults4);
    }*/

    private BarData createHorizontalBarChartData1(){

        ArrayList<IBarDataSet> proteinchart_main = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        //ArrayList<String> labels = new ArrayList<>();

        //ラベル名
        //labels.add("A");
        //labels.add("B");

        //ゴール
        ArrayList<BarEntry> proteinchart1 = new ArrayList<>();
        proteinchart1.add(new BarEntry(3f, 100));
        proteinchart1.add(new BarEntry(10f, 200));

        BarDataSet DataSet1 = new BarDataSet(proteinchart1,"Protein");

        // 色の設定
        colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        DataSet1.setColors(colors);
        DataSet1.setDrawValues(true);



        proteinchart_main.add(DataSet1);

        BarData barData = new BarData(proteinchart_main);
        barData.setBarWidth(5f);


        return barData;

    }

    private BarData createHorizontalBarChartData2(){

        ArrayList<IBarDataSet> caloriechart_main = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        //ArrayList<String> labels = new ArrayList<>();

        //ラベル名
        //labels.add("A");
        //labels.add("B");

        //ゴール
        ArrayList<BarEntry> caloriechart1 = new ArrayList<>();
        caloriechart1.add(new BarEntry(3f, 100));
        caloriechart1.add(new BarEntry(10f, 200));

        BarDataSet DataSet1 = new BarDataSet(caloriechart1,"Calorie");

        // 色の設定
        colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        DataSet1.setColors(colors);
        DataSet1.setDrawValues(true);



        caloriechart_main.add(DataSet1);

        BarData barData = new BarData(caloriechart_main);
        barData.setBarWidth(5f);


        return barData;

    }
    protected RectF mOnValueSelectedRectF = new RectF();

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mProteinChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mProteinChart.getPosition(e, mProteinChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
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