package com.example.asus.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText reg_email;
    EditText reg_password;
    EditText reg_name;
    Button signin;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_password = (EditText) findViewById(R.id.reg_pass);
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.Signup);

        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Signin.class));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = reg_email.getText().toString().trim();
                String getpassword = reg_password.getText().toString().trim();
                callsignin(getemail, getpassword);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = reg_email.getText().toString().trim();
                String getpassword = reg_password.getText().toString().trim();
                callsignup(getemail, getpassword);
            }
        });




    }

    private void callsignup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Succesful" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            userProfile();
                            Toast.makeText(MainActivity.this, "Created", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created");
                        }

                        // ...
                    }
                });
    }

    private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(reg_name.getText().toString().trim()).build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.d("TESTING", "User profile updates");
                    }
                }
            });


        }
    }

    private void callsignin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Signin successful" + task.isSuccessful());

                        if (!task.isSuccessful()){
                            Log.v("TESTING", "signInwithemail failed", task.getException());
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            Intent i = new Intent(MainActivity.this, Signin.class);
                            finish();
                            startActivity(i);
                        }
                    }
                });
    }




}
