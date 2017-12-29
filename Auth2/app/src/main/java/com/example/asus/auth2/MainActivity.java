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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Spinner
    public Spinner spinnercity;
    public String[] city;
    public ArrayAdapter<String > adapter;
    public Spinner spinnergender;
    public String [] gender;

    //Date
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    DatabaseReference databaseInfo;





    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinnercity = (Spinner) findViewById(R.id.city);
        spinnergender = (Spinner) findViewById(R.id.gender);

        databaseInfo = FirebaseDatabase.getInstance().getReference("info");


        //Date
        mDisplayDate = (TextView) findViewById(R.id.date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
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
                mDisplayDate.setText(date);

            }
        };
        //End of Date

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);


        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);



    }

    //Database command for Spinner//
    private void addArtist(){

    }
    //End of Database command for Spinner//

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String city = spinnercity.getSelectedItem().toString();
        String gender = spinnergender.getSelectedItem().toString();
        String date = mDisplayDate.getText().toString();

        //empty email
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show();
            return;
        }else
        //empty password
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show();
            return;
        }else
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

        //Database//




    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            registerUser();

        }

        if (view == textViewSignin){
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
