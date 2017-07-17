package jp.techacademy.chiku.takahiro.nutritionmanegment;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import static jp.techacademy.chiku.takahiro.nutritionmanegment.R.styleable.View;

public class InputActivity extends AppCompatActivity implements android.view.View.OnClickListener  {

    Button mConsumingButton;
    Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        setTitle("新規登録");

        mConsumingButton = (Button) findViewById(R.id.consuming_button);
        mConsumingButton.setOnClickListener(this);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.consuming_button) {
            showDatePickerDialog();
        } else if (v.getId() == R.id.register_button) {
            Log.d("ONPROCESS", "このボタンは未だ作り途中です");
        }
    }

        private void  showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        },
                        2016,
                        6,
                        1);
                datePickerDialog.show();


            }

        }