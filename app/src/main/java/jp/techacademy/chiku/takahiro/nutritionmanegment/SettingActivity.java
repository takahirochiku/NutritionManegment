package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import static jp.techacademy.chiku.takahiro.nutritionmanegment.Const.ProteinAmountPATH;

public class SettingActivity extends AppCompatActivity implements OnChartValueSelectedListener{

    protected HorizontalBarChart mProteinChart;
    protected HorizontalBarChart mCalorieChart;
    protected HorizontalBarChart i;
    protected ArrayList<BarEntry> iList;
    String NutritionName;
    String mSex;
    String mAge;
    String mUser;
    String mProteinAmount;
    String mProteinAmount2;
    int mProteinAmount3;

    protected RectF mOnValueSelectedRectF = new RectF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("設定");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart);
        mProteinChart.setOnChartValueSelectedListener(this);
        mCalorieChart = (HorizontalBarChart) findViewById(R.id.calorie_chart);
        mCalorieChart.setOnChartValueSelectedListener(this);

        //意味を確認する
        ArrayList<BarEntry> proteinchart = new ArrayList<BarEntry>();
        i = mProteinChart;
        NutritionName ="Protein";
        iList = proteinchart;
        horizontalBarChart();
        horizontalBarChartSetting1();
        horizontalBarChartSetting2();
        horizontalBarChartSetting3();
        horizontalBarChartSetting4();
        horizontalBarChartProcess();

        ArrayList<BarEntry> caloriechart = new ArrayList<BarEntry>();
        i = mCalorieChart;
        NutritionName ="Calorie";
        iList = caloriechart;
        horizontalBarChart();
        horizontalBarChartSetting1();
        horizontalBarChartSetting2();
        horizontalBarChartSetting3();
        horizontalBarChartSetting4();
        horizontalBarChartProcess();

        //sharedpreferenceGet();

        final Spinner mSpinnerAge = (Spinner) findViewById(R.id.age_select);
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

        final CheckBox mCheckBoxSexMale = (CheckBox) findViewById(R.id.checkbox1);
        mCheckBoxSexMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkbox形式はisCheckedでtrueが変える場合、変数に値を代入させる
                if (mCheckBoxSexMale.isChecked() == true) {
                    mSex = new String("男性");
                }
            }
        });

        final CheckBox mCheckBoxSexFemale = (CheckBox) findViewById(R.id.checkbox2);
        mCheckBoxSexFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBoxSexFemale.isChecked() == true) {
                    mSex = new String("女性");
                }
            }
        });

        final EditText mEditTextUser = (EditText) findViewById(R.id.username_editText);
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
                    }
                }
            }
        });

    }

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

    private void settingsearch(){
        Log.d("TEST","spinnerage2:"+mAge);
        Log.d("TEST","sex2:"+mSex);
        Realm mRealm = Realm.getDefaultInstance();
        RealmQuery<Nutritiondata> query = mRealm.where(Nutritiondata.class)
                .equalTo("age",mAge)
                .equalTo("sex",mSex)
                .equalTo("nutrition","Protein_g");
        Nutritiondata resultprotein = query.findFirst();
        mProteinAmount = String.valueOf(resultprotein.getAmount());
        Log.d("TEST","Score:"+mProteinAmount);
    }

    private void horizontalBarChart(){
        i.setDrawBarShadow(true);
        i.setDrawValueAboveBar(true);
        i.setPinchZoom(false);
        i.setDrawGridBackground(false);
        i.getDescription().setEnabled(false);
    }

    private void horizontalBarChartSetting1(){
        XAxis xl = i.getXAxis();
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(true);
        xl.setGranularity(20f);
    }

    private void horizontalBarChartSetting2(){
        YAxis yl = i.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f);
    }

    private void horizontalBarChartSetting3() {
        YAxis yr =i.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(false);
        yr.setAxisMinimum(0f);
    }

    private void horizontalBarChartSetting4() {
        Legend l = i.getLegend();
        l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
    }

    private void horizontalBarChartProcess(){
        BarData data = new BarData(getDataSet());
        i.setData(data);
        i.setFitBars(false);
        i.animateY(2500);
        i.invalidate();
    }

    private BarDataSet getDataSet() {
        //mProteinAmount3 = Integer.parseInt(mProteinAmount2);
        iList.add(new BarEntry(1f,100));

        BarDataSet set1;

        if (i.getData() != null &&
                i.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) i.getData().getDataSetByIndex(0);
            set1.setValues(iList);
            i.getData().notifyDataChanged();
            i.notifyDataSetChanged();
        } else {
            Log.d("TESTTEST", "チャートに入れる数値がありません");
        }
        BarDataSet dataset = new BarDataSet(iList,NutritionName);
        return dataset;
    }

    private void sharedpreference(View view){
    SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    //Spinner形式はgetTextではなくgetSelectedItem
        editor.putString(Const.AgePATH, mAge);
        editor.putString(Const.SexPATH, mSex);
        editor.putString(Const.NamePATH, mUser);
        editor.putString(Const.ProteinAmountPATH, mProteinAmount);
        editor.commit();
        Snackbar.make(view, "Setting success", Snackbar.LENGTH_LONG).show();
        Log.d("TEST","spinnerage1:"+mAge);
        Log.d("TEST","sex1:"+mSex);
        Log.d("TEST","user1:"+mUser);
    }

    /**private void sharedpreferenceGet() {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mProteinAmount2 = sharedPref.getString(Const.ProteinAmountPATH);
        Log.d("TEST","Constから引っ張った値:"+mProteinAmount2);
    }*/
}
