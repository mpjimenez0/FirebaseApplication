package com.example.entunix_jimenez.firebaseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Math.pow;

/**
 * Created by mpjim on 12/18/2017.
 */

public class SendData extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    // GENERAL VARIABLES
    private GestureDetectorCompat GestureDetect;
    String myName;
    String deviceName = android.os.Build.MODEL;
    String deviceMan = android.os.Build.MANUFACTURER;

    // GESTURE DETAILS VARIABLES
    float radius = 25f;
    float x, y, sX, sY, fX, fY;
    int tapCount = 0;
    long upTime = 0;
    long totalTime;
    float prevX = 0;
    float prevY = 0;

    // GESTURE RECOGNITION VARIABLES
    boolean singleTap;
    boolean doubleTap;
    boolean longPress;
    boolean swipe;
    boolean scroll;

    // LAYOUT VARIABLES
    Button bt_submitData;
    TextView tv_gestDetails, tv_gestGreeting;

    // FIREBASE VARIABLES
    private FirebaseAuth mAuth;
    DatabaseReference gestureAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_activity_layout);

        // GESTURE DETAILS VARIABLES
        x = 0;
        y = 0;
        sX = 0;
        sY = 0;
        fX = 0;
        fY = 0;
        totalTime = 0;

        // GESTURE RECOGNITION VARIABLES
        singleTap = false;
        doubleTap = false;
        longPress = false;
        scroll = false;
        swipe = false;

        // FIREBASE VARIABLES
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String gestureAttemptUrl = "https://fir-application-8e6b4.firebaseio.com/" + user.getUid() + "/details";
        gestureAttempt = FirebaseDatabase.getInstance().getReferenceFromUrl(gestureAttemptUrl);
        //
        bt_submitData = findViewById(R.id.bt_submitData);
        tv_gestDetails = findViewById(R.id.tv_gestDetails);
        tv_gestGreeting = findViewById(R.id.tv_gestGreeting);

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), Authentication.class));
        }

        if(user != null){
            tv_gestGreeting.setText("Welcome, " + user.getDisplayName() + "\n User Id: " + user.getUid());
            myName = user.getDisplayName().toString();
        }

        GestureDetect = new GestureDetectorCompat(this, this);
        GestureDetect.setOnDoubleTapListener(this);

        bt_submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addDetails();
            }
        });
    }

    private void addDetails(){
        GestureDetails gestDetails = new GestureDetails(x, y, sX, sY, fX, fY, totalTime, myName, deviceName, deviceMan);
        String tapGestureId =  gestureAttempt.push().getKey();
        gestureAttempt.child(tapGestureId).setValue(gestDetails);
        Toast.makeText(this, "Value Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetect.onTouchEvent(event);
        tv_gestDetails = findViewById(R.id.tv_gestDetails);
        totalTime = event.getDownTime() - upTime;
        x = event.getX();
        y = event.getY();
        float touchDuration = upTime - event.getDownTime();
        long eventDuration = event.getEventTime() - event.getDownTime();

        if (tapCount >= 2){
            tapCount = 0;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sX = event.getX();
                sY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                fX = event.getX();
                fY = event.getY();
                upTime = event.getEventTime();
                tapCount++;
                if (tapCount == 1) {
                    prevX = event.getX();
                    prevY = event.getY();
                }
                break;
        }

        //SINGLE TAP
        singleTapFunc(touchDuration);

        //DOUBLE TAP
        float tapPos = (float) Math.sqrt(pow(prevX - event.getX(), 2) + pow(prevY - event.getY(), 2));
        doubleTapFunc(tapPos);

        //LONGPRESS
        float longPressTime = (event.getEventTime() - event.getDownTime());
        longPressFunc(longPressTime);

        //SWIPE
        float swipeDistanceX = Math.abs(fX - sX);
        float swipeDistanceY = Math.abs(fY - sY);

        if( swipeDistanceX >= 200) {
            if (swipeDistanceY >= 200) {
                if (eventDuration > 100) {
                    singleTap = false;
                    doubleTap = false;
                    longPress = false;
                    scroll = false;
                    swipe = true;
                }
            }
        }

        tv_gestDetails.setText(
                "\n\nON TOUCHEVENT"
                        + "\nSINGLE TAP: " + singleTap
                        + "\nDOUBLE TAP: " + doubleTap
                        + "\nLONG PRESS: " + longPress
                        + "\nSWIPE: " + swipe
                        + "\nCount: " + tapCount
                        + "\nCurrent X: " + x
                        + "\nCurrent Y: " + y
                        + "\nDown X: " + sX
                        + "\nDownY: " + sY
                        + "\nUp X: " + fX
                        + "\nUp Y: " + fY
                        + "\nDown Time: " + event.getDownTime() + "ms"
                        + "\nEvent Time: " + event.getEventTime() + "ms"
                        + "\nupTime: " + upTime + "ms"
                        + "\nEvent Duration: " + totalTime + "ms"
                        + "\ngetPointerCount: " + event.getPointerCount()
                        + "\nDevice Name: " + deviceName
                        + "\nDevice Manufacturer: " + deviceMan
        );
        return super.onTouchEvent(event);
    }

    public void singleTapFunc(float touchDuration) {
        if (touchDuration < 500) {
            singleTap = true;
            longPress = false;
            doubleTap = false;
            swipe = false;
        }
    }

    public void doubleTapFunc(float tapPos) {
        if (tapCount == 2) {
            if (tapPos <= radius) {
                singleTap = false;
                longPress = false;
                doubleTap = true;
                swipe = false;
            }
        }
    }

    public void longPressFunc(float longPressTime) {
        if (longPressTime >= 500) {
            singleTap = false;
            doubleTap = false;
            swipe = false;
            tapCount = 0;
            float downPos = (float) Math.sqrt(pow(sX, 2) + pow(sY, 2));
            float curPos = (float) Math.sqrt(pow(x, 2) + pow(y, 2));
            if (Math.abs(curPos - downPos) > radius) {
                longPress = false;
            }else{
                longPress = true;
            }
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
