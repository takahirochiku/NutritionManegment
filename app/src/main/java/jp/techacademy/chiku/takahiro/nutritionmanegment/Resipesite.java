package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static jp.techacademy.chiku.takahiro.nutritionmanegment.SummaryActivity.mMealsText;

public class Resipesite extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resipesite);

        WebView  myWebView = (WebView)findViewById(R.id.webView1);
        myWebView.setWebViewClient(new WebViewClient());
        Log.d("TEST","mMealsText:"+mMealsText);
        myWebView.loadUrl("https://recipe.rakuten.co.jp/search/"+mMealsText+"/");
        myWebView.getSettings().setJavaScriptEnabled(true);

    }
}
