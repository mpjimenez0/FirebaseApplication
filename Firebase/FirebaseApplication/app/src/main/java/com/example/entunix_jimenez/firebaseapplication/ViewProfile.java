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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpjim on 12/18/2017.
 */


public class ViewProfile extends AppCompatActivity{
    TextView tv_profile_welcome, tv_profile_id;
    ListView lv_gesture_details;

    DatabaseReference databaseGestureDetails;
    private FirebaseAuth mAuth;

    List<GestureDetails> gestureDetailsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_layout);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        gestureDetailsList = new ArrayList<>();

        tv_profile_welcome = findViewById(R.id.tv_profile_welcome);
        tv_profile_id = findViewById(R.id.tv_profile_id);
        lv_gesture_details = findViewById(R.id.lv_gesture_details);

        databaseGestureDetails = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-application-8e6b4.firebaseio.com/Gestures/" + user.getUid() + "/");

        tv_profile_welcome.setText("Welcome, " + user.getDisplayName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseGestureDetails.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gestureDetailsList.clear();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                for(DataSnapshot gestureSnapshot: dataSnapshot.getChildren()){
                    String x = gestureSnapshot.getKey();
                    System.out.println(x);
                    databaseGestureDetails = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-application-8e6b4.firebaseio.com/Gestures/" + user.getUid() + "/" + x + "/Scroll");
                    databaseGestureDetails.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot gestureSnapshot: dataSnapshot.getChildren()){
                                String x = gestureSnapshot.getKey();
                                System.out.println("x: " + x);
                                GestureDetails gestureDetails = gestureSnapshot.getValue(GestureDetails.class);

                                gestureDetailsList.add(gestureDetails);
                            }
                            GestureList adapter = new GestureList(ViewProfile.this, gestureDetailsList);
                            lv_gesture_details.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                GestureList adapter = new GestureList(ViewProfile.this, gestureDetailsList);
                lv_gesture_details.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
