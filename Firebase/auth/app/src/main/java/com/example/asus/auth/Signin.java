    package com.example.asus.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

    public class Signin extends AppCompatActivity {

    Button signout;
    private FirebaseAuth mAuth;
    TextView uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        signout = (Button) findViewById(R.id.signout2);
        uname = (TextView) findViewById(R.id.username2);


        if (mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            uname.setText("Welcome" + user.getDisplayName());
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

    }
}
