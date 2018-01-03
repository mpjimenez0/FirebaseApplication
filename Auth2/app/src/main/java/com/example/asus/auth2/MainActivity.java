package com.example.asus.auth2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.IDNA;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Spinner
    public Spinner sp_reg_city;
    public Spinner sp_reg_gender;


    //Date
    private EditText et_reg_bdate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Button bt_reg_submit;
    private EditText et_reg_email;
    private EditText  et_reg_pass;
    private TextView textViewSignin;
    DatabaseReference databaseInfo;





    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sp_reg_city= (Spinner) findViewById(R.id.sp_reg_city);
        sp_reg_gender = (Spinner) findViewById(R.id.sp_reg_gender);
        databaseInfo = FirebaseDatabase.getInstance().getReference("info");

        //Date
        et_reg_bdate = (EditText) findViewById(R.id.et_reg_bdate);

        et_reg_bdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
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
        //End of Date

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        bt_reg_submit= (Button) findViewById(R.id.buttonRegister);
        et_reg_pass = (EditText) findViewById(R.id.et_reg_pass);
        et_reg_email = (EditText) findViewById(R.id.et_reg_email);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);


        bt_reg_submit.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);



    }



    private void registerUser() {
        String email = et_reg_email.getText().toString().trim();
        String password = et_reg_pass.getText().toString().trim();
        String city = sp_reg_city.getSelectedItem().toString();
        String gender = sp_reg_gender.getSelectedItem().toString();
        String date = et_reg_bdate.getText().toString();

        //Check email if exist
        firebaseAuth.fetchProvidersForEmail(et_reg_email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        boolean check = !task.getResult().getProviders().isEmpty();

                        if (check){
                            Toast.makeText(getApplicationContext(), "Email already exist",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        //end of check email if exist


        //empty email
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show();
            return;
        }else
            //invalid email
        if (!email.contains("@")){
            Toast.makeText(this,"Email not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        //empty password
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show();
            return;
        }else
        //password not 6 characters
        if (password.length() < 6){
            Toast.makeText(this," Password min of 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        //Empty gender
        if (TextUtils.isEmpty(gender)){
            Toast.makeText(this,"Enter gender", Toast.LENGTH_SHORT).show();
            return;
        }else
        //Empty Citu
        if (TextUtils.isEmpty(city)){
            Toast.makeText(this,"Enter city", Toast.LENGTH_SHORT).show();
            return;
        }else
        //empty bday
        if (TextUtils.isEmpty(date)){
            Toast.makeText(this,"Enter Birthdate", Toast.LENGTH_SHORT).show();
            return;
        }
        //complete infos
        else
        {
            String id = databaseInfo.push().getKey();

            info artist = new info(id, gender, city, email, date);

            databaseInfo.child(id).setValue(artist);
        }



        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });






    }



    @Override
    public void onClick(View view) {
        if (view == bt_reg_submit){
            registerUser();

        }
        else
        if (view == textViewSignin){
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
