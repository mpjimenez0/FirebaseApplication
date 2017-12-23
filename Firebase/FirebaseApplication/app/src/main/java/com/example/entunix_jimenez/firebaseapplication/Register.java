package com.example.entunix_jimenez.firebaseapplication;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mpjimenez on 12/23/2017.
 */


public class Register extends AppCompatActivity {
    EditText et_reg_email;
    EditText et_reg_pass;
    Spinner sp_reg_city;
    Spinner sp_reg_gender;
    Button bt_reg_submit;

    EditText et_reg_bdate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    ArrayAdapter<String> sp_adapter;
    String[] city;
    String[] gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);

        et_reg_email = findViewById(R.id.et_reg_email);
        et_reg_pass = findViewById(R.id.et_reg_pass);
        sp_reg_city = findViewById(R.id.sp_reg_city);
        sp_reg_gender = findViewById(R.id.sp_reg_gender);
        bt_reg_submit = findViewById(R.id.bt_reg_submit);
        et_reg_bdate = findViewById(R.id.date);

        city = getResources().getStringArray(R.array.city);
        sp_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, city);
        sp_reg_city.setAdapter(sp_adapter);
        gender = getResources().getStringArray(R.array.gender);
        sp_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, gender);
        sp_reg_gender.setAdapter(sp_adapter);

        et_reg_bdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Register.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                et_reg_bdate.setText(date);
            }
        };
    }
}
