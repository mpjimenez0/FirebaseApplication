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

import java.util.ArrayList;
import java.util.List;

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
    String gestureNum;

    int nextCount = 0;

    // GESTURE DETAILS VARIABLES
    float singleTapRadius = 25f;
    float longPressRadius = 10f;
    float x, y, sX, sY, fX, fY;
    int tapCount = 0;
    long upTime = 0;
    long totalTime;
    float prevX = 0;
    float prevY = 0;
    List<TapData> Data = new ArrayList<TapData>();

    // GESTURE RECOGNITION VARIABLES
    boolean singleTap;
    boolean doubleTap;
    boolean longPress;
    boolean scroll;
    boolean fling;
    boolean swipeX;
    boolean swipeY;

    // LAYOUT VARIABLES
    Button bt_submitData;
    TextView tv_gestDetails, tv_gestGreeting, tv_gesture, tv_gest_counter;

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
        fling = false;
        swipeX = false;
        swipeY = false;

        // FIREBASE VARIABLES
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String gestureAttemptUrl = "https://fir-application-8e6b4.firebaseio.com/" + user.getUid() + "/details";
        //String gestureAttemptUrl = "https://fir-application-8e6b4.firebaseio.com/" + user.getUid() + "/details";
        gestureAttempt = FirebaseDatabase.getInstance().getReferenceFromUrl(gestureAttemptUrl);

        //WORKING
        bt_submitData = findViewById(R.id.bt_submitData);
        tv_gestDetails = findViewById(R.id.tv_gestDetails);
        tv_gestGreeting = findViewById(R.id.tv_gestGreeting);
        tv_gesture = findViewById(R.id.tv_gesture);
        tv_gest_counter = findViewById(R.id.tv_gest_counter);

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
                if(tapCount == 0){
                    Toast.makeText(SendData.this, "No Gesture Yet", Toast.LENGTH_SHORT).show();
                }else{
                    nextCount++;
                    gestureNum = "Gesture" + nextCount;
                    addDetails();
                    int countHolder = nextCount+1;
                    tv_gest_counter.setText("Input Gesture# " + countHolder);
                    tapCount = 0;
                }

                if(nextCount == 5){
                    bt_submitData.setText("Next");
                    tv_gesture.setText("Proceed to next gesture");
                    finish();
                }
            }
        });
    }

    private void addDetails(){
        GestureDetails gestDetails = new GestureDetails(x, y, sX, sY, fX, fY, totalTime, myName,
                deviceName, deviceMan, singleTap, doubleTap, longPress, scroll, fling, swipeX, swipeY);
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
        float tapPos = (float) Math.sqrt(pow(prevX - event.getX(), 2) + pow(prevY - event.getY(), 2));
        float swipeDistanceX = Math.abs(fX - sX);
        float swipeDistanceY = Math.abs(fY - sY);
        float scrollDistance = Math.abs(fY - sY);
        float downPos = (float) Math.sqrt(pow(sX, 2) + pow(sY, 2));
        float curPos = (float) Math.sqrt(pow(x, 2) + pow(y, 2));

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
                break;
        }

        //SINGLE TAP
        singleTapFunc(touchDuration, curPos, downPos, x, y, upTime);
        //DOUBLE TAP
        doubleTapFunc();
        //LONGPRESS
        longPressFunc(eventDuration, curPos, downPos);
        //HORIZONTAL SWIPE
        horizSwipeFunc(swipeDistanceX, touchDuration);
        //VERTICAL SWIPE
        vertSwipeFunc(swipeDistanceY, touchDuration);
        //SCROLL
        scrollFunc(scrollDistance, touchDuration);

        tv_gestDetails.setText(
                "\n\nON TOUCHEVENT"
                        + "\nSINGLE TAP: " + singleTap
                        + "\nDOUBLE TAP: " + doubleTap
                        + "\nLONG PRESS: " + longPress
                        + "\nSCROLL: " + scroll
                        + "\nHORIZONTAL SWIPE: " + swipeX
                        + "\nVERTICAL SWIPE: " + swipeY
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

    public void singleTapFunc(float touchDuration, float curPos, float downPos, float x, float y, long upTime) {
        if (touchDuration < 500) {
            singleTap = true;
            longPress = false;
            doubleTap = false;
            scroll = false;
            swipeX = false;
            swipeY = false;
            if(Math.abs(curPos - downPos) > singleTapRadius){
                singleTap = false;
            }else{
                singleTap = true;
                SendData.TapData data = new SendData.TapData();
                data.prevX = x;
                data.prevY = y;
                data.time = upTime;
                Data.add(data);
            }
        }
    }

    public void doubleTapFunc() {
        if(Data.size() > 1){
            singleTap = false;
            longPress = false;
            doubleTap = true;
            scroll = false;
            swipeX = false;
            swipeY = false;
            if(Math.abs(Data.get(Data.size() - 1).prevX - Data.get(Data.size() - 2).prevX) > singleTapRadius){
                doubleTap = false;
            }
            if (Math.abs(Data.get(Data.size() - 1).prevY - Data.get(Data.size() - 2).prevY) > singleTapRadius) {
                doubleTap = false;
            }
            if (Math.abs(Data.get(Data.size() - 1).time - Data.get(Data.size() - 2).time) > 200) {
                doubleTap = false;
            }
        }
    }

    public void longPressFunc(float longPressTime, float curPos, float downPos) {
        if (longPressTime >= 500) {
            tapCount = 0;
            singleTap = false;
            doubleTap = false;
            scroll = false;
            swipeX = false;
            swipeY = false;
            if (Math.abs(curPos - downPos) > longPressRadius) {
                longPress = false;
            }else{
                longPress = true;
            }
        }
    }

    public void horizSwipeFunc(float swipeDistanceX, float swipeDurationX){
        if(swipeDistanceX >= 150 && swipeDurationX <= 500){
            singleTap = false;
            longPress = false;
            doubleTap = false;
            scroll = false;
            swipeX = true;
            swipeY = false;
        }
    }

    public void vertSwipeFunc(float swipeDistanceY, float swipeDurationY){
        if(swipeDistanceY >= 150 && swipeDurationY <= 500){
            singleTap = false;
            longPress = false;
            doubleTap = false;
            scroll = false;
            swipeX = false;
            swipeY = true;
        }
    }

    public void scrollFunc(float scrollDistance, float scrollDuration){
        if(scrollDistance >= 50 && scrollDuration > 500){
            singleTap = false;
            longPress = false;
            doubleTap = false;
            scroll = false;
            swipeX = false;
            swipeY = true;
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

    private class TapData {
        public float prevX;
        public float prevY;
        public long time;
    }
}
