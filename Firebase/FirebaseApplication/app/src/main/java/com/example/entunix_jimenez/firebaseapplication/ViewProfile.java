package com.example.entunix_jimenez.firebaseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewProfile extends AppCompatActivity {
    Button SingleTap, DoubleTap, LongPress, HorizontalSwipe, VerticalSwipe, Scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        SingleTap = (Button)findViewById(R.id.Butt1);
        DoubleTap = (Button)findViewById(R.id.Butt2);
        LongPress = (Button)findViewById(R.id.Butt3);
        HorizontalSwipe = (Button)findViewById(R.id.Butt4);
        VerticalSwipe = (Button)findViewById(R.id.Butt5);
        Scroll = (Button)findViewById(R.id.Butt6);

        SingleTap.setOnClickListener(new View.OnClickListener(){
            @Override
<<<<<<< HEAD
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
=======
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), SingleTapProfile.class));
>>>>>>> 680c26d0f46790dcc2e6d0dbc9bd3a82734df556
            }
        });
        DoubleTap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), DoubleTapProfile.class));
            }
        });
        LongPress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), LongPressProfile.class));
            }
        });
        HorizontalSwipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), HorizontalSwipeProfile.class));
            }
        });
        VerticalSwipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), VerticalSwipeProfile.class));
            }
        });
        Scroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), ScrollProfile.class));
            }
        });

    }
}
