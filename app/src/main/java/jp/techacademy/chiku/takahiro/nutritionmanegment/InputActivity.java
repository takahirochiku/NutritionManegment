package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;


public class InputActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Button mDateButton;
    private String mTiming,mDateString,mMeals;
    private int mYear, mMonth, mDay;
    public static int year,month,day;
    private ArrayAdapter<CharSequence> adapter;
    private AutoCompleteTextView mMealsReserach;
    private EditText mCountEdit;
    private Spinner mTimingSpinner;
    private RegisterData mRegisterData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Registration");

        // ナビゲーションドロワーの設定
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected (MenuItem item){
                int id = item.getItemId();

                if (id == R.id.nav_meals) {
                    mToolbar.setTitle("Today");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                /**else if (id == R.id.nav_nuetrition) {
                 mToolbar.setTitle("栄養");
                 Log.d("TESTEST", "この機能は未だ作り途中です");
                 }*/
                else if (id == R.id.nav_saumary) {
                    mToolbar.setTitle("Summary");
                    Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        calendar();

        mDateButton = (Button) findViewById(R.id.consuming_button);
        mDateButton.setOnClickListener(mOnDateClickListener);

        mTimingSpinner =(Spinner)findViewById(R.id.timing_spinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.list4, android.R.layout.simple_dropdown_item_1line);
        mMealsReserach = (AutoCompleteTextView)findViewById(R.id.Meals_EditText);
        mMealsReserach.setAdapter(adapter);

        mCountEdit =(EditText)findViewById(R.id.Count_EditText);
        findViewById(R.id.register_button).setOnClickListener(mOnDoneClickListener);

        mTimingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner mTimingSpinner= (Spinner) parent;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




    private View.OnClickListener mOnDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(InputActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear+1;
                            mDay = dayOfMonth;
                            mDateString = mYear + "/" + String.format("%02d",(mMonth)) + "/" + String.format("%02d", mDay);
                            mDateButton.setText(mDateString);
                        }
                    }, year, month-1, day);
            initialDateSet();
            datePickerDialog.show();
        }
    };

    private void initialDateSet(){
        mDateString = year + "/" + String.format("%02d",(month)) + "/" + String.format("%02d", day);
        mDateButton.setText(mDateString);
    }

    private View.OnClickListener mOnDoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            addMeals();
            reload();
        }
    };

    private void addMeals() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        if(mRegisterData ==null){
            mRegisterData =new RegisterData();

            RealmResults<RegisterData> RegisterDataResults = realm.where(RegisterData.class).findAll();

            int identifier;
            if (RegisterDataResults.max("id") != null){
                identifier = RegisterDataResults.max("id").intValue()+1;
            }else{
                identifier = 0;
            }
            mRegisterData.setId(identifier);
        }

        mTiming = (String) mTimingSpinner.getSelectedItem();
        mMeals = mMealsReserach.getText().toString();
        String counts = mCountEdit.getText().toString();

        mRegisterData.setTiming(mTiming);
        mRegisterData.setMeals(mMeals);
        mRegisterData.setDate(mDateString);
        mRegisterData.setCount(counts);

        realm.copyToRealmOrUpdate(mRegisterData);
        realm.commitTransaction();

        realm.close();

        Log.d("TEST", "摂取日にち:" + mDateString);
        Log.d("TEST", "摂取タイミング:" + mTiming);
        Log.d("TEST", "摂取Meals:" + mMeals);
        Log.d("TEST", "摂取量(100g×):" + counts);
    }

    public static void calendar() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);// 0 - 11
        month++;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        day++;
        day++;
    }

    public void reload() {
        int i =0;
        mTimingSpinner.setSelection(i);
        mMealsReserach.setText(null);
        mCountEdit.setText(null);
    }

}