package com.example.entunix_jimenez.firebaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Authentication extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private EditText et_email, et_pass;
    private Button bt_signin;
    private TextView tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        mAuth = FirebaseAuth.getInstance();

        tv_signup = findViewById(R.id.tv_signup);
        bt_signin = findViewById(R.id.bt_signin);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
        }

        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = et_email.getText().toString().trim();
                String getpassword = et_pass.getText().toString().trim();
                callsignin(getemail, getpassword);
            }
        });


        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Authentication.this, Register.class));
            }
        });
    }


    private void callsignin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
                            Toast.makeText(Authentication.this, "Sign in Failed",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Intent i = new Intent(Authentication.this, MainMenu.class);
                            startActivity(i);
                        }
                    }
                });
    }
}