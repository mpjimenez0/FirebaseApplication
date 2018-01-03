package com.example.entunix_jimenez.firebaseapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mpjim on 12/18/2017.
 */


public class ViewProfile extends AppCompatActivity{
    TextView tv_profile_welcome, tv_profile_id;
    ListView lv_gesture_details;

    DatabaseReference databaseGestureDetails;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_layout);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        tv_profile_welcome = findViewById(R.id.tv_profile_welcome);
        tv_profile_id = findViewById(R.id.tv_profile_id);
        lv_gesture_details = findViewById(R.id.lv_gesture_details);

        databaseGestureDetails = FirebaseDatabase.getInstance().getReference();

        tv_profile_welcome.setText("Welcome, " + user.getDisplayName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseGestureDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot gestureSnapshot: dataSnapshot.getChildren()){
                    GestureDetails gestureDetails = gestureSnapshot.getValue(GestureDetails.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
