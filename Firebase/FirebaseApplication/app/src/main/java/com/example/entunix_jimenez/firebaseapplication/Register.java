package com.example.entunix_jimenez.firebaseapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by mpjimenez on 12/23/2017.
 */


public class Register extends AppCompatActivity {
    EditText et_reg_name;
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

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        mAuth = FirebaseAuth.getInstance();

        // layout components declaration (connecting the xml file and the class)
        et_reg_name = findViewById(R.id.et_reg_name);
        et_reg_email = findViewById(R.id.et_reg_email);
        et_reg_pass = findViewById(R.id.et_reg_pass);
        sp_reg_city = findViewById(R.id.sp_reg_city);
        sp_reg_gender = findViewById(R.id.sp_reg_gender);
        bt_reg_submit = findViewById(R.id.bt_reg_submit);
        et_reg_bdate = findViewById(R.id.date);

        // spinner components declaration (referencing the String arrays from the /values/strings.xml)
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

        bt_reg_submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                registerUser();
            }
        });
    }

    private void registerUser(){
        // note: This method is for checking if all fields has data on it (because all fields in the register part is mandatory
        String reg_name = et_reg_name.getText().toString().trim();
        String reg_email = et_reg_email.getText().toString().trim();
        String reg_pass = et_reg_pass.getText().toString().trim();
        String reg_gender = sp_reg_gender.getSelectedItem().toString().trim();
        String reg_city = sp_reg_city.getSelectedItem().toString().trim();
        String reg_bdate = et_reg_bdate.getText().toString().trim();

        if(TextUtils.isEmpty(reg_name)){
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(reg_email)){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(reg_pass)){
            Toast.makeText(this, "Enter Pass", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(reg_gender)){
            Toast.makeText(this, "Enter Gender", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(reg_city)){
            Toast.makeText(this, "Enter City", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(reg_bdate)){
            Toast.makeText(this, "Enter Birthdate", Toast.LENGTH_SHORT).show();
        }

        if(!TextUtils.isEmpty(reg_name) && !TextUtils.isEmpty(reg_email) && !TextUtils.isEmpty(reg_pass) &&
                !TextUtils.isEmpty(reg_gender) && !TextUtils.isEmpty(reg_city) && !TextUtils.isEmpty(reg_bdate)){
            // **insert authentication code here (authenticate, then lead straight to the Main Menu)**
            String getemail = et_reg_email.getText().toString().trim();
            String getpassword = et_reg_pass.getText().toString().trim();
            callsignup(getemail, getpassword);
        }
    }

    private void callsignup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("TESTING", "createUserWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                } else {
                    userProfile();
                    Toast.makeText(Register.this, "Created Account", Toast.LENGTH_SHORT).show();
                    Log.d("TESTING", "User profile updated.");
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                }
            }
        });
    }

    private void userProfile() {
        FirebaseUser user =  mAuth.getCurrentUser();
        if(user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(et_reg_name.getText().toString().trim()).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>(){
                @Override
                public void onComplete(@NonNull Task<Void> task){
                    if(task.isSuccessful()){
                        Log.d("TESTING", "User Profile Updated!" + task.isSuccessful());
                    }
                }
            });
        }
    }
}
