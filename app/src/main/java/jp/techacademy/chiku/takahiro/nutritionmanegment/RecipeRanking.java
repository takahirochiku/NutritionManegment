package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class RecipeRanking extends AppCompatActivity {

    private final String mFormat = "json";
    private final int mFormatVersion = 1;
    private final String mAppicationId = "1093116609709872158";
    private final String mCategoryId = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Map<String,Object> response = null;
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.getForObject("https://app.rakuten.co.jp/services/api/Recipe/CategoryRanking/ applicationId="+mAppicationId+"format"+mFormat+"categoryId"+mCategoryId+"formatVersion"+mFormatVersion,Map response);


        }
    }
}


