package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Toolbar mToolbar;
    protected HorizontalBarChart mProteinChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");

        mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart2);
        mProteinChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        //意味を確認する
        mProteinChart.setDrawBarShadow(false);
        mProteinChart.setDrawValueAboveBar(true);
        mProteinChart.setPinchZoom(false);
        mProteinChart.setDrawGridBackground(false);
        mProteinChart.getDescription().setEnabled(false);

        XAxis xl = mProteinChart.getXAxis();
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.nav_meals) {
                mToolbar.setTitle("食事");
                Log.d("TESTEST","この機能は未だ作り途中です");
            } else if (id == R.id.nav_nuetrition) {
                mToolbar.setTitle("栄養");
                Log.d("TESTEST","この機能は未だ作り途中です");
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
            Log.d("TESTEST", "チャートに入れる数値がありません");
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