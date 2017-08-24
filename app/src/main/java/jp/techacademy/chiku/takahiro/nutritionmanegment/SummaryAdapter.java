package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by takahiro chiku on 2017/08/24.
 */

public class SummaryAdapter extends BaseAdapter {
    //他のxmlリソースのViewを取り扱うための仕組みであるLayoutInflaterをメンバ変数として定義
    private LayoutInflater mLayoutInflater = null;
    private List<InputData> mSummaryList;

    public SummaryAdapter(Context context) {
        //システムレベルのサービスを取得するためのメソッド
        //レイアウトのためのサービスとアラームのためのサービスを取得する
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setSummaryList(List<InputData> summaryList) {
        mSummaryList = summaryList;
    }

    @Override
    public int getCount() {
        return mSummaryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSummaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSummaryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_view, null);
        }

        TextView textView1 = (TextView) convertView.findViewById(R.id.text1);
        TextView textView2 = (TextView) convertView.findViewById(R.id.text2);

        textView1.setText(mSummaryList.get(position).getNutrition());
        double amount = mSummaryList.get(position).getAmount();
        textView2.setText(String.valueOf(amount));

        return convertView;
    }

}
