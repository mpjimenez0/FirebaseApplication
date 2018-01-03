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
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference databaseInfo;

    EditText et_reg_bdate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

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

        String infoUrl = "https://fir-application-8e6b4.firebaseio.com/Users/";
        databaseInfo = FirebaseDatabase.getInstance().getReferenceFromUrl(infoUrl);

        //date
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
        //end of date
    }

    private void registerUser(){
        // note: This method is for checking if all fields has data on it (because all fields in the register part is mandatory
        String reg_name = et_reg_name.getText().toString().trim();
        String reg_email = et_reg_email.getText().toString().trim();
        String reg_pass = et_reg_pass.getText().toString().trim();
        String reg_gender = sp_reg_gender.getSelectedItem().toString().trim();
        String reg_city = sp_reg_city.getSelectedItem().toString().trim();
        String reg_bdate = et_reg_bdate.getText().toString().trim();

        //check email if exist
        mAuth.fetchProvidersForEmail(et_reg_email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        boolean check = !task.getResult().getProviders().isEmpty();
                        if (check){
                            Toast.makeText(getApplicationContext(), "Email already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //end of check email if exist


        //empty email
        if (TextUtils.isEmpty(reg_email)){
            Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!reg_email.contains("@")){
            Toast.makeText(this,"Email not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        if (TextUtils.isEmpty(reg_pass)){
            Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show();
            return;
        }else

        if (reg_pass.length() < 6){
            Toast.makeText(this," Password min of 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        //Empty gender
        if (TextUtils.isEmpty(reg_gender)){
            Toast.makeText(this,"Enter gender", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (TextUtils.isEmpty(reg_city)){
            Toast.makeText(this,"Enter city", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (TextUtils.isEmpty(reg_bdate)){
            Toast.makeText(this,"Enter Birthdate", Toast.LENGTH_SHORT).show();
            return;
        }
        else

        if(!TextUtils.isEmpty(reg_name) && !TextUtils.isEmpty(reg_email) && !TextUtils.isEmpty(reg_pass) &&
                !TextUtils.isEmpty(reg_gender) && !TextUtils.isEmpty(reg_city) && !TextUtils.isEmpty(reg_bdate)){
            // **insert authentication code here (authenticate, then lead straight to the Main Menu)**

            String getemail = et_reg_email.getText().toString().trim();
            String getpassword = et_reg_pass.getText().toString().trim();
            addDetails(reg_name, reg_email, reg_gender, reg_city, reg_bdate);
            callsignup(getemail, getpassword);
            finish();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
        }
    }

    private void addDetails(String reg_name, String reg_email, String reg_gender, String reg_city, String reg_bDate){
        UserInfo info = new UserInfo(reg_name, reg_email, reg_gender, reg_city, reg_bDate);
        String infoId = databaseInfo.push().getKey();
        databaseInfo.child(infoId).setValue(info);
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
                }
            }
        });
    }

    private void userProfile() {
        FirebaseUser user =  mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(et_reg_name.getText().toString().trim()).build();
        user.updateProfile(profileUpdates);
    }
}