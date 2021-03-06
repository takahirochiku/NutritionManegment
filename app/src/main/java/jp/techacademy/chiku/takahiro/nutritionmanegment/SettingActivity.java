package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class SettingActivity extends AppCompatActivity implements OnChartValueSelectedListener{

    private Toolbar mToolbar;
    Spinner mSpinnerAge;
    String mSex,mAge,mUser,mAmount;
    String mProteinAmount,mProteinAmount2;
    String mCalorieAmount,mCalorieAmount2,mFiberAmount,mFiberAmount2,mCalciumAmount,mCalciumAmount2;
    protected RectF mOnValueSelectedRectF = new RectF();
    private int mAgeId;
    protected HorizontalBarChart mProteinChart,mCalorieChart,mFiberChart,mCalciumChart;

    //ArrayList<HorizontalBarChart> list = new ArrayList<HorizontalBarChart>();
    //String NutritionName;
    //int mProteinAmount3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Setting");

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

        /**mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart);
        mProteinChart.setOnChartValueSelectedListener(this);
        mCalorieChart = (HorizontalBarChart) findViewById(R.id.calorie_chart);
        mCalorieChart.setOnChartValueSelectedListener(this);
        mFiberChart = (HorizontalBarChart) findViewById(R.id.fiber_chart);
        mFiberChart.setOnChartValueSelectedListener(this);
        mCalciumChart = (HorizontalBarChart) findViewById(R.id.calcium_chart);
        mCalciumChart.setOnChartValueSelectedListener(this);*/

        sharedpreferenceCall();
        //((Spinner) findViewById(R.id.age_select)).setSelection(mAgecall);
        sharedpreferenceGet();

        /**list = new ArrayList<>();
        list.add(mProteinChart);
        list.add(mCalorieChart);
        list.add(mFiberChart);
        list.add(mCalciumChart);

        chartsetting();*/

        mSpinnerAge = (Spinner) findViewById(R.id.age_select);
        String[] AgeList = getResources().getStringArray(R.array.list);
        for(int i =0;i<AgeList.length;i++){
            if(mAge.equals(AgeList[i])){
                mSpinnerAge.setSelection(i);
            break;
            }
        }

        mSpinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner mSpinnerAge= (Spinner) parent;
                mAge = (String) mSpinnerAge.getSelectedItem();
                Log.d("TEST", "spinnerage0:" + mAge);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final RadioGroup mRadioGroup= (RadioGroup) findViewById(R.id.radiogroup);
         if(mSex.equals("男性")){
             mRadioGroup.check(R.id.radiobutton_male);
         }if(mSex.equals("女性")){
             mRadioGroup.check(R.id.radiobutton_female);
         }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId != -1) {
                            //mSex = new String("男性");
                            RadioButton mRadioButton = (RadioButton) findViewById(checkedId);
                            mSex = mRadioButton.getText().toString();
                        }
            }
        });

        final EditText mEditTextUser = (EditText) findViewById(R.id.username_editText);
        if(mUser!="0"){
            mEditTextUser.setText(mUser);
        }
        mEditTextUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button mButtonSetting = (Button) findViewById(R.id.setting_button);
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.setting_button) {

                    InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mUser =mEditTextUser.getText().toString();
                    if (mAge == "select your age") {
                        Snackbar.make(view, "年齢の選択がありません", Snackbar.LENGTH_LONG).show();
                    } else if (mSex == null) {
                        Snackbar.make(view, "性別の選択がありません", Snackbar.LENGTH_LONG).show();
                    } else if (mUser == null) {
                        Snackbar.make(view, "ユーザー名がありません", Snackbar.LENGTH_LONG).show();
                    } else {
                        //Context.MODE_PRIVATEはセキュリティレベルが：このアプリ限定を示す
                        settingsearch();
                        sharedpreference(view);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    /**private void chartsetting() {
            for (HorizontalBarChart chart:list) {
                setChart(chart);
                getXAxis(chart);
                getAxisLeft(chart);
                getAxisRight(chart);
                getLegend(chart);
                barData(chart);
            }
    }*/

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

    /**public void setChart(HorizontalBarChart chart){
        chart.setDrawBarShadow(true);
        chart.setDrawValueAboveBar(true);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
    }

    public void getXAxis(HorizontalBarChart chart){
        XAxis xl = chart.getXAxis();
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(true);
        xl.setGranularity(20f);
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
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setAxisMaximum(mMaxium);
        yl.setAxisMinimum(0f);
    }

    public void getAxisRight(HorizontalBarChart chart) {
        YAxis yr = chart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(false);
        yr.setAxisMinimum(0f);
    }

    public void getLegend(HorizontalBarChart chart) {
        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
    }

    public void barData(HorizontalBarChart chart){
        BarData data = new BarData(getDataSet(chart));
        chart.setData(data);
        chart.setFitBars(false);
        chart.animateY(2500);
        chart.invalidate();
    }

    private BarDataSet getDataSet(HorizontalBarChart chart) {
        ArrayList<BarEntry>barEntryList = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (chart == mProteinChart){
            NutritionName = "Protein";
            mAmount = mProteinAmount2;
            colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        }else if(chart == mCalorieChart){
            NutritionName = "Calorie";
            mAmount = mCalorieAmount2;
            colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        }else if(chart == mFiberChart){
            NutritionName = "Fiber";
            mAmount = mFiberAmount2;
            colors.add(ColorTemplate.COLORFUL_COLORS[2]);
        }else if(chart == mCalciumChart){
            NutritionName = "Calorie";
            mAmount = mCalciumAmount2;
            colors.add(ColorTemplate.COLORFUL_COLORS[3]);
        }

        mProteinAmount3 = Integer.parseInt(mAmount);
        barEntryList.add(new BarEntry(1f,mProteinAmount3));

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(barEntryList);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            Log.d("TESTTEST", "チャートに入れる数値がありません");
        }

        BarDataSet dataset = new BarDataSet(barEntryList,NutritionName);
        dataset.setColors(colors);
        dataset.setDrawValues(true);

        return dataset;
    }*/

    private void settingsearch(){
        Log.d("TEST","spinnerage2:"+mAge);
        Log.d("TEST","sex2:"+mSex);
        Realm mRealm = Realm.getDefaultInstance();

        RealmQuery<Nutritiondata> query = mRealm.where(Nutritiondata.class)
                .equalTo("age",mAge)
                .equalTo("sex",mSex)
                .equalTo("nutrition","Protein_g");
        Nutritiondata resultProtein = query.findFirst();
        mProteinAmount = String.valueOf(resultProtein.getAmount());

        RealmQuery<Nutritiondata> query2 = mRealm.where(Nutritiondata.class)
                .equalTo("age",mAge)
                .equalTo("sex",mSex)
                .equalTo("nutrition","Calorie_cal");
        Nutritiondata resultCalorie = query2.findFirst();
        mCalorieAmount = String.valueOf(resultCalorie.getAmount());
        Log.d("TEST","Score:"+mCalorieAmount);

        RealmQuery<Nutritiondata> query3 = mRealm.where(Nutritiondata.class)
                .equalTo("age",mAge)
                .equalTo("sex",mSex)
                .equalTo("nutrition","Fiber_g");
        Nutritiondata resultFiber = query3.findFirst();
        mFiberAmount = String.valueOf(resultFiber.getAmount());
        Log.d("TEST","Score:"+mFiberAmount);

        RealmQuery<Nutritiondata> query4 = mRealm.where(Nutritiondata.class)
                .equalTo("age",mAge)
                .equalTo("sex",mSex)
                .equalTo("nutrition","Calcium_mg");
        Nutritiondata resultCalcium = query4.findFirst();
        mCalciumAmount = String.valueOf(resultCalcium.getAmount());
        Log.d("TEST","Score:"+mCalciumAmount);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Const.ProteinAmountPATH, mProteinAmount);
        editor.putString(Const.CalorieAmountPATH,mCalorieAmount);
        editor.putString(Const.FiberAmountPATH,mFiberAmount);
        editor.putString(Const.CalciumAmountPATH,mCalciumAmount);
        editor.commit();

        sharedpreferenceGet();
        //chartsetting();
    }

    private void sharedpreference(View view){
    SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    //Spinner形式はgetTextではなくgetSelectedItem
        editor.putString(Const.AgePATH, mAge);
        editor.putString(Const.SexPATH, mSex);
        editor.putString(Const.NamePATH, mUser);
        editor.putString(Const.ProteinAmountPATH, mProteinAmount);
        editor.putString(Const.CalorieAmountPATH,mCalorieAmount);
        editor.putString(Const.FiberAmountPATH,mFiberAmount);
        editor.putString(Const.CalciumAmountPATH,mCalciumAmount);
        editor.commit();
        Snackbar.make(view, "Setting success", Snackbar.LENGTH_LONG).show();
        Log.d("TEST","spinnerage1:"+mAge);
        Log.d("TEST","sex1:"+mSex);
        Log.d("TEST","user1:"+mUser);
    }

    private void sharedpreferenceCall(){

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mAge = sharedPref.getString(Const.AgePATH, "0");
        mSex = sharedPref.getString(Const.SexPATH,"0");
        mUser = sharedPref.getString(Const.NamePATH,"0");

        Log.d("TEST","mAgeの値："+mAge);
        Log.d("TEST","mSexの値："+mSex);

        Realm mRealm = Realm.getDefaultInstance();
        RealmQuery<Nutritiondata> query = mRealm.where(Nutritiondata.class)
                .equalTo("age",mAge)
                .equalTo("sex",mSex);
        Nutritiondata resultProtein = query.findFirst();
        int mAgecall = resultProtein.getId();
        Log.d("TEST","mAgeCall2の値："+mAgecall);
        Log.d("TEST","resultProtein："+resultProtein);
    }

    public void sharedpreferenceGet() {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mProteinAmount2 = sharedPref.getString(Const.ProteinAmountPATH,"0");
        mCalorieAmount2 = sharedPref.getString(Const.CalorieAmountPATH,"0");
        mFiberAmount2 = sharedPref.getString(Const.FiberAmountPATH,"0");
        mCalciumAmount2 = sharedPref.getString(Const.CalciumAmountPATH,"0");
        Log.d("TEST","Constから引っ張った値:"+mProteinAmount2);
        Log.d("TEST","Constから引っ張った値:"+mCalorieAmount2);
        Log.d("TEST","Constから引っ張った値:"+mFiberAmount2);
        Log.d("TEST","Constから引っ張った値:"+ mCalciumAmount2);

    }
}
