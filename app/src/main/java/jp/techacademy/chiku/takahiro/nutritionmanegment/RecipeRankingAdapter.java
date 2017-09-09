package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class RecipeRankingAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<String> mRecipeRankingList;

    private void setRecipeRankingList(List<String> recipeRankingList) {
        mRecipeRankingList = recipeRankingList;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null);
        }

        //TextView textView1 = (TextView) convertView.findViewById(android.R.id.titleTextView);
        //TextView textView2 = (TextView) convertView.findViewById(android.R.id.timeTextView);
        //TextView textView3 = (TextView) convertView.findViewById(android.R.id.costTextView);

        // 後でTaskクラスから情報を取得するように変更する
        //textView1.setText(mRecipeRankingList.get(position));

        return convertView;
    }
}