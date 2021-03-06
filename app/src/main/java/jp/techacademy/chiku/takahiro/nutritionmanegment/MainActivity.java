package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.gesture.OrientedBoundingBox;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.AppLaunchChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static android.icu.util.Calendar.getInstance;
import java.util.Date;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static jp.techacademy.chiku.takahiro.nutritionmanegment.InputActivity.day;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.InputActivity.month;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.InputActivity.year;


public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Toolbar mToolbar;
    protected HorizontalBarChart mProteinChart,mCalorieChart,mFiberChart,mCalciumChart;
    String mAmount,mData,mData2,mDate;
    public static int mCalorieSum;
    public static int mProteinSum;
    public static int mFiberSum;
    public static int mCalciumSum;
    int mDataid,mDataid2;
    int mInputAmount;
    String NutritionName;
    public static String mProteinAmount2,mCalorieAmount2,mFiberAmount2,mCalciumAmount2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Today");

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
                    mToolbar.setTitle("Today");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                /**else if (id == R.id.nav_nuetrition) {
                 mToolbar.setTitle("栄養");
                 Log.d("TESTEST", "この機能は未だ作り途中です");
                 }*/
                else if (id == R.id.nav_saumary) {
                    mToolbar.setTitle("Summary");
                    Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        checkRecDataid();

        if (mDataid ==0){
            readNutritionData();
            updateRecDataid();
        }

        checkRecDataid2();

        if (mDataid2 ==0) {
            readInputNutritionData();
            updateRecDataid2();
        }

        ConstGet();
        Log.d("TEST","Protein値;"+mProteinAmount2);
        Log.d("TEST","Calorie値;"+mCalorieAmount2);
        Log.d("TEST","Fiber値;"+mFiberAmount2);
        Log.d("TEST","Calcium値;"+mCalciumAmount2);

        InputDataGet();

        mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart2);
        mProteinChart.setOnChartValueSelectedListener(this);
        mCalorieChart = (HorizontalBarChart) findViewById(R.id.calorie_chart2);
        mCalorieChart.setOnChartValueSelectedListener(this);
        mFiberChart = (HorizontalBarChart) findViewById(R.id.fiber_chart2);
        mFiberChart.setOnChartValueSelectedListener(this);
        mCalciumChart = (HorizontalBarChart) findViewById(R.id.calcuim_chart2);
        mCalciumChart.setOnChartValueSelectedListener(this);

        ArrayList<HorizontalBarChart>list = new ArrayList<>();
        list.add(mProteinChart);
        list.add(mCalorieChart);
        list.add(mFiberChart);
        list.add(mCalciumChart);

        for (HorizontalBarChart chart:list) {
            setChart(chart);
            getXAxis(chart);
            getAxisLeft(chart);
            getAxisRight(chart);
            getLegend(chart);
            barData(chart);
        }

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        InputDataGet();
        Log.d("TEST","onResume");
    }


    private void checkRecDataid() {

        SharedPreferences sharedDataid = getSharedPreferences("dataInfo", Context.MODE_PRIVATE);
        mData = sharedDataid.getString(Const.DataidPATH, "0");
        mDataid = Integer.parseInt(mData);
        Log.d("TESTmData", "mDataid:" + mDataid);
    }

    //予めモデルクラスを作成する事(NutritionData.class)
    private List<Nutritiondata> nutritionLists = new ArrayList<>();

    private void readNutritionData() {
        Realm realm = Realm.getDefaultInstance();
        InputStream is =  this.getResources().openRawResource(R.raw.recomendednutritiondata);
        //リーダーを設定
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        //リーダー用の変数を宣言、Log.dでエラーにならないよう""を設定
        String line ="";
        try{
            int count = 1;
            //Realmインスタンスを取得

            //readLineで一行ずつでブレイクする
            while ((line = reader.readLine()) != null){
                //エラーが発生した際にどの行で止まったのか見るLog
                Log.d("TESTRealm1","Line:"+line);
                //tokensとsplitで分割する
                String[] tokens =line.split(",");
                Nutritiondata standerdlist = new Nutritiondata();
                standerdlist.setAge(tokens[0]);
                standerdlist.setSex(tokens[1]);
                standerdlist.setNutrition(tokens[2]);
                standerdlist.setAmount(Integer.parseInt(tokens[3]));
                standerdlist.setDataid(Integer.parseInt(tokens[4]));
                standerdlist.setId(count);
                nutritionLists.add(standerdlist);

                //オブジェクトの追加や更新
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(standerdlist);
                realm.commitTransaction();
                count++;
                Log.d("TESTRealm1","Just created:"+standerdlist);
            }
            //realm.close();
        } catch (IOException e) {
            //エラーが発生した際にどの行が原因か見るLog
            Log.d("TESTRealm1","Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }

    private void updateRecDataid(){
        mDataid++;
        SharedPreferences sharedDataid2 =getSharedPreferences("dataInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedDataid2.edit();
        editor.putString(Const.DataidPATH, String.valueOf(mDataid));
        editor.commit();
    }

    private void checkRecDataid2() {
        SharedPreferences sharedDataid = getSharedPreferences("dataInfo", Context.MODE_PRIVATE);
        mData2 = sharedDataid.getString(Const.Dataid2PATH, "0");
        mDataid2 = Integer.parseInt(mData2);
        Log.d("TESTmData", "mDataid2:" + mDataid2);
    }


    private List<InputData> InputLists = new ArrayList<>();

    private void readInputNutritionData() {
        Realm realm = Realm.getDefaultInstance();
        InputStream is =  this.getResources().openRawResource(R.raw.inputnutritiondata);
        //リーダーを設定
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        //リーダー用の変数を宣言、Log.dでエラーにならないよう""を設定
        String line ="";
        try{
            int count = 1;
            //Realmインスタンスを取得
            //Realm realm = Realm.getDefaultInstance();

            //readLineで一行ずつでブレイクする
            while ((line = reader.readLine()) != null){
                //エラーが発生した際にどの行で止まったのか見るLog
                Log.d("TESTRealm2","Line:"+line);
                //tokensとsplitで分割する
                String[] tokens =line.split(",");
                InputData inputlist = new InputData();
                inputlist.setCategory(tokens[0]);
                inputlist.setMeals(tokens[1]);
                inputlist.setNutrition(tokens[2]);
                inputlist.setAmount(Double.parseDouble(tokens[3]));
                inputlist.setId(count);
                InputLists.add(inputlist);

                //オブジェクトの追加や更新
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(inputlist);
                realm.commitTransaction();
                count++;
                Log.d("TESTRealm2","Just created:"+inputlist);
            }
            realm.close();
        } catch (IOException e) {
            //エラーが発生した際にどの行が原因か見るLog
            Log.d("TESTRealm2","Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }

    private void updateRecDataid2() {
        mDataid2++;
        SharedPreferences sharedDataid2 =getSharedPreferences("dataInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedDataid2.edit();
        editor.putString(Const.Dataid2PATH, String.valueOf(mDataid2));
        editor.commit();
    }

    private void InputDataGet() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        mDate = year + "/" + String.format("%02d",(month + 1)) + "/" + String.format("%02d", day);
        Log.d("TEST","mDate:"+mDate);

        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<RegisterData> query = mRealm.where(RegisterData.class)
                .equalTo("Date", mDate).findAll();

        HashMap<String, Integer> sumData = new HashMap<String, Integer>();

        mCalorieSum = 0;
        mProteinSum = 0;
        mFiberSum = 0;
        mCalciumSum = 0;

        for (RegisterData data : query) {

            String mMeals = data.getMeals();

            Log.d("TEST","mMeals:"+mMeals);

            RealmResults<InputData> query2 = mRealm.where(InputData.class)
                  .equalTo("Meals", mMeals).findAll();
            Log.d("TEST","query2のサイズ:"+query2.size());

            for (InputData nData : query2) {
                String mNutrition = nData.getNutrition();
                Log.d("TEST","mNutrition:"+mNutrition);
                double mAmount = nData.getAmount();
                Log.d("TEST","mNutrition:"+mAmount);

                /**if(sumData.containsKey(mNutrition)){
                    if (mNutrition.equals("Calorie_cal")) {
                        mCalorieSum += mAmount;
                    }
                intger sum = sumData.get(mNutrition);
                    sumData.put(mNutrition, new Integer(mAmount));

                }else{}*/

                if (mNutrition.equals("Protein_g")) {
                    mProteinSum += mAmount;
                }
                if (mNutrition.equals("Fiber_g")) {
                    mFiberSum += mAmount;
                }
                if (mNutrition.equals("Calcium_mg")) {
                    mCalciumSum += mAmount;
                }
            }
        }
        Log.d("TESTInputData","CalorieSum:"+mCalorieSum);
        Log.d("TESTInputData","ProteinSum:"+mProteinSum);
        Log.d("TESTInputData","FiberSum:"+mFiberSum);
        Log.d("TESTInputData","CalciumSum:"+mCalciumSum);
    }


    public void setChart(HorizontalBarChart chart) {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setPinchZoom(false);
        //chart.setMaxVisibleValueCount(60);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
    }

    public void getXAxis(HorizontalBarChart chart) {
        XAxis xl = chart.getXAxis();
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(true);
        xl.setDrawLabels(false);
        xl.setGranularity(30f);
    }

    public void getAxisLeft(HorizontalBarChart chart) {
        int mMaxium =0;

        if (chart == mProteinChart){
            mMaxium = 70;
        }else if(chart == mCalorieChart){
            mMaxium = 3000;
        }else if(chart == mFiberChart){
            mMaxium = 25;
        }else if(chart == mCalciumChart){
            mMaxium = 1000;
        }

        YAxis yl = chart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(false);
        yl.setAxisMaximum(mMaxium);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
    }

    public void getAxisRight(HorizontalBarChart chart) {

        YAxis yr = chart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(true);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);
    }

    public void getLegend(HorizontalBarChart chart) {
        Legend l = chart.getLegend();
        //l.setFormSize(5f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //l.setTypeface(...);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        //l.setXEntrySpacxe(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
    }

    public void barData(HorizontalBarChart chart) {
        chart.setData(createHorizontalBarChartData(chart));
        //chart.groupBars(1980f,groupSpace);
        chart.setFitBars(true);
        chart.invalidate();
        chart.animateY(2000);
    }

    private BarData createHorizontalBarChartData(HorizontalBarChart chart){

        ArrayList<IBarDataSet> chart1 = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (chart == mProteinChart){
            NutritionName = "Protein_g";
            mAmount = mProteinAmount2;
            mInputAmount = mProteinSum;
            colors.add(ColorTemplate.COLORFUL_COLORS[4]);
            colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        }else if(chart == mCalorieChart){
            NutritionName = "Calorie_cal";
            mAmount = mCalorieAmount2;
            mInputAmount = mCalorieSum;
            colors.add(ColorTemplate.COLORFUL_COLORS[4]);
            colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        }else if(chart == mFiberChart){
            NutritionName = "Fiber_g";
            mAmount = mFiberAmount2;
            mInputAmount = mFiberSum;
            colors.add(ColorTemplate.COLORFUL_COLORS[4]);
            colors.add(ColorTemplate.COLORFUL_COLORS[2]);
        }else if(chart == mCalciumChart){
            NutritionName = "Calcium_mg";
            mAmount = mCalciumAmount2;
            mInputAmount = mCalciumSum;
            colors.add(ColorTemplate.COLORFUL_COLORS[4]);
            colors.add(ColorTemplate.COLORFUL_COLORS[3]);
        }


        ArrayList<BarEntry> chart2 = new ArrayList<>();
        int mAmount2=Integer.parseInt(mAmount);

        chart2.add(new BarEntry(1f,mInputAmount));
        chart2.add(new BarEntry(2f, mAmount2));

        BarDataSet DataSet1 = new BarDataSet(chart2,NutritionName);
        DataSet1.setBarBorderWidth(1f);

        // 色の設定
        //colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        DataSet1.setColors(colors);
        DataSet1.setDrawValues(true);

        chart1.add(DataSet1);

        BarData barData = new BarData(chart1);
        return barData;
    }

    protected RectF mOnValueSelectedRectF = new RectF();

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

    private void ConstGet(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mProteinAmount2 = sharedPref.getString(Const.ProteinAmountPATH,"0");
        mCalorieAmount2 = sharedPref.getString(Const.CalorieAmountPATH,"0");
        mFiberAmount2 = sharedPref.getString(Const.FiberAmountPATH,"0");
        mCalciumAmount2 = sharedPref.getString(Const.CalciumAmountPATH,"0");
    }
}