package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;

import io.realm.Realm;

import static android.icu.util.Calendar.getInstance;
import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.styleable.View;

public class InputActivity extends AppCompatActivity {

    Button mDateButton,mRegisterButton;
    private String mTiming,mDateString,mDate;
    private int mYear, mMonth, mDay;
    private int year, month, day;
    private ArrayAdapter<CharSequence> adapter;
    private AutoCompleteTextView mMealsReserach;
    private EditText mCountEdit;
    private Spinner mTimingSpinner;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        setTitle("新規登録");
        calendar();

        adapter = ArrayAdapter.createFromResource(this, R.array.list4, android.R.layout.simple_dropdown_item_1line);
        mMealsReserach = (AutoCompleteTextView)findViewById(R.id.Meals_EditText);
        mMealsReserach.setAdapter(adapter);

        mDateButton = (Button) findViewById(R.id.consuming_button);
        mDateButton.setOnClickListener(mOnDateClickListener);

        mTimingSpinner =(Spinner)findViewById(R.id.timing_spinner);
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
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            mDateString = mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay);
                            mDateButton.setText(mDateString);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
    };

    private View.OnClickListener mOnDoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addMeals();
            finish();
        }
    };

    private void addMeals() {
        //ealm realm = Realm.getDefaultInstance();
        //realm.beginTransaction();

        mTiming = (String) mTimingSpinner.getSelectedItem();
        mDate = mDateString;
        String meals = mMealsReserach.getText().toString();
        String counts = mCountEdit.getText().toString();
        Log.d("TEST", "摂取日にち:" + mDate);
        Log.d("TEST", "摂取タイミング:" + mTiming);
        Log.d("TEST", "摂取Meals:" + meals);
        Log.d("TEST", "摂取量(100g×):" + counts);

    }

    private void calendar() {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);// 0 - 11
            //month++;
            day = calendar.get(Calendar.DAY_OF_MONTH);
            //day++;
    }
}