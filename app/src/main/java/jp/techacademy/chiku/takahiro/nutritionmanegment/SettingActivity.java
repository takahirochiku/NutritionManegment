package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

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
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    protected HorizontalBarChart mProteinChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("設定");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mProteinChart = (HorizontalBarChart) findViewById(R.id.protein_chart);
        mProteinChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        //mProteinChart.setDrawBarShadow(false);

        //mProteinChart.setDrawValueAboveBar(true);

        //mProteinChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        //mProteinChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        //mProteinChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        //mProteinChart.setDrawGridBackground(false);

        XAxis xl = mProteinChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = mProteinChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mProteinChart.getAxisRight();
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        setData(12, 50);
        //mProteinChart.setFitBars(true);
        mProteinChart.animateY(2500);

        Legend l = mProteinChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);


    }

    private void setData(int count, float range) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> proteinlist = new ArrayList<>();

        BarEntry v1e1 = new BarEntry(110.000f, 0);
        proteinlist.add(v1e1);

        BarDataSet set1 = null;

        if (mProteinChart.getData() != null &&
                mProteinChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mProteinChart.getData().getDataSetByIndex(0);
            set1.setValues(proteinlist);
            mProteinChart.getData().notifyDataChanged();
            mProteinChart.notifyDataSetChanged();
        } else {
            Log.d("TESTTEST", "set1出来ません。");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mProteinChart.setData(data);
        }
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
    };

}
