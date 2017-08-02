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
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SettingActivity extends AppCompatActivity implements OnChartValueSelectedListener{

    protected HorizontalBarChart mProteinChart;
    String mSex;
    Spinner mSpinnerAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("設定");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart);
        mProteinChart.setOnChartValueSelectedListener(this);

        //意味を確認する
        mProteinChart.setDrawBarShadow(false);
        mProteinChart.setDrawValueAboveBar(true);
        mProteinChart.setPinchZoom(false);
        mProteinChart.setDrawGridBackground(false);
        mProteinChart.getDescription().setEnabled(false);

        XAxis xl = mProteinChart.getXAxis();
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(true);
        xl.setGranularity(20f);

        YAxis yl = mProteinChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mProteinChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yl.setDrawLabels(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        Legend l = mProteinChart.getLegend();
        //l.setFormSize(5f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //l.setTypeface(...);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        BarData data = new BarData(getDataSet());
        mProteinChart.setData(data);
        mProteinChart.setFitBars(false);
        mProteinChart.animateY(2500);
        mProteinChart.invalidate();

        final Spinner mSpinnerAge = (Spinner) findViewById(R.id.age_select);

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

        Button mButtonSetting = (Button) findViewById(R.id.setting_button);
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.setting_button) {

                    InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    if (mSpinnerAge == null) {
                        Snackbar.make(view, "年齢の選択がありません", Snackbar.LENGTH_LONG).show();
                    } else if (mSex == null) {
                        Snackbar.make(view, "性別の選択がありません", Snackbar.LENGTH_LONG).show();
                    } else if (mEditTextUser == null) {
                        Snackbar.make(view, "ユーザー名がありません", Snackbar.LENGTH_LONG).show();
                    } else {
                        //Context.MODE_PRIVATEはセキュリティレベルが：このアプリ限定を示す
                        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        //Spinner形式はgetTextではなくgetSelectedItem
                        editor.putString("Age", mSpinnerAge.getSelectedItem().toString());
                        editor.putString("Sex", mSex);
                        editor.putString("User", mEditTextUser.getText().toString());
                        editor.apply();
                        Snackbar.make(view, "Setting success", Snackbar.LENGTH_LONG).show();

                        settingsearch();

                    }
                }
            }
        });
    }


    private BarDataSet getDataSet() {
        ArrayList<BarEntry> proteinchart = new ArrayList<BarEntry>();

        proteinchart.add(new BarEntry(1f, 100));

        BarDataSet set1;

        if (mProteinChart.getData() != null &&
                mProteinChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mProteinChart.getData().getDataSetByIndex(0);
            set1.setValues(proteinchart);
            mProteinChart.getData().notifyDataChanged();
            mProteinChart.notifyDataSetChanged();
        } else {
            Log.d("TESTTEST", "チャートに入れる数値がありません");
        }
        BarDataSet dataset = new BarDataSet(proteinchart, "Protein");
        return dataset;
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

    private void settingsearch(){
        Realm mRealm = Realm.getDefaultInstance();
        RealmQuery<Nutritiondata> query = mRealm.where(Nutritiondata.class)
                .equalTo("Age",mSpinnerAge.getSelectedItem().toString())
                .equalTo("Sex",mSex)
                .equalTo("Nutrition","protein");
        RealmResults<Nutritiondata> resultprotein = query.findAll();
        Log.d("TESTTEST","Score is:"+resultprotein);

    }

}
