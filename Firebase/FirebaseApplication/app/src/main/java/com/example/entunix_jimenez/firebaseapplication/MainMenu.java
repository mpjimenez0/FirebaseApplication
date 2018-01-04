package com.example.entunix_jimenez.firebaseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mpjim on 12/18/2017.
 */

public class MainMenu extends AppCompatActivity{
    Button bt_gestureStart, bt_profileView, bt_signOut;
    TextView tv_greeting;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);

        bt_gestureStart = findViewById(R.id.bt_gestureStart);
        bt_profileView = findViewById(R.id.bt_profileView);
        bt_signOut = findViewById(R.id.bt_signOut);
        tv_greeting = findViewById(R.id.tv_mainMenGreeting);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            tv_greeting.setText("Welcome, " + user.getDisplayName());
        }else if(user == null){
            finish();
            startActivity(new Intent(getApplicationContext(), Authentication.class));
            Toast.makeText(MainMenu.this, "Sign in Failed",
                    Toast.LENGTH_SHORT).show();
        }

        bt_gestureStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), SendData.class));
            }
        });

        bt_profileView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), ViewProfile.class));
            }
        });

        bt_signOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), Authentication.class));
            }
        });
    }
}
