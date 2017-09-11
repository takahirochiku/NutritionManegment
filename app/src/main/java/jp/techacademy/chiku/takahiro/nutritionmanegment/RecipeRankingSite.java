package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static jp.techacademy.chiku.takahiro.nutritionmanegment.RecipeRankingData.RecipeUrl;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.SummaryActivity.mMealsText;

public class RecipeRankingSite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ranking_site);

        WebView myWebView = (WebView)findViewById(R.id.webView1);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(RecipeUrl);
        myWebView.getSettings().setJavaScriptEnabled(true);

    }
}