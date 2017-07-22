package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
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

        //意味を確認する
        mProteinChart.setDrawBarShadow(false);
        mProteinChart.setDrawValueAboveBar(false);
        mProteinChart.setPinchZoom(false);
        mProteinChart.setDrawGridBackground(false);

        mProteinChart.getDescription().setEnabled(false);

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Protein");

        XAxis xl = mProteinChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(labels);
        xl.setValueFormatter(xaxisFormatter);
        xl.setGranularity(1);

        YAxis yl = mProteinChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mProteinChart.getAxisRight();
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        BarData data = new BarData(getDataSet());
        mProteinChart.setData(data);
        mProteinChart.setFitBars(true);
        mProteinChart.animateY(2500);
        mProteinChart.invalidate();

    }

    private BarDataSet getDataSet() {
        //float barWidth = 9f;
        //float spaceForBar = 10f;
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

    public class CategoryBarChartXaxisFormatter implements IAxisValueFormatter {

        ArrayList<String> mValues;

        public CategoryBarChartXaxisFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            int val = (int) value;
            String label = "";
            if (val >= 0 && val < mValues.size()) {
                label = mValues.get(val);
            } else {
                label = "";
            }
            return label;
        }
    }
}
