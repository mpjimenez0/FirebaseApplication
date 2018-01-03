package pbl.latestgestures;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class SwipeY extends AppCompatActivity implements
        GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    String myName;
    String deviceName = android.os.Build.MODEL;
    String deviceMan = android.os.Build.MANUFACTURER;
    String gestureNum;

    float singleTapRadius = 25f;
    float longPressRadius = 10f;
    float doubleTapRadius = 200;



    float x, y, sX, sY, fX, fY;
    long totalTime;
    int tapCount = 0;
    int nextCount = 0;
    boolean breakLoop;

    boolean singleTap;
    boolean doubleTap;
    boolean longPress;
    boolean scroll;
    boolean fling;
    boolean swipeX;
    boolean swipeY;

    long upTime = 0;

    List<SwipeY.TapData> Data = new ArrayList();


    private static TextView textView;
    private Button submitData;

    DatabaseReference databaseVelocity;
    DatabaseReference databaseGestureDetails;

    private GestureDetectorCompat GestureDetect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_y);

        databaseVelocity = FirebaseDatabase.getInstance().getReference();
        databaseGestureDetails = FirebaseDatabase.getInstance().getReference();

        myName = "usertrial";
        x = 0;
        y = 0;
        sX = 0;
        sY = 0;
        fX = 0;
        fY = 0;
        totalTime = 0;

        singleTap = false;
        doubleTap = false;
        longPress = false;
        scroll = false;
        fling = false;
        swipeX = false;
        swipeY = false;

        submitData = (Button)findViewById(R.id.submitData);
        textView = (TextView)findViewById(R.id.textView);
        GestureDetect = new GestureDetectorCompat(this,this);
        GestureDetect.setOnDoubleTapListener(this);

//        submitData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(tapCount == 0){
//                    Toast.makeText(MainActivity.this, "No Gesture Yet", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    addVelocity();
//                }
//            }
//        });

        textView.setText("Input Gesture# 1");
        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tapCount == 0) {
                    Toast.makeText(SwipeY.this, "No Gesture Yet", Toast.LENGTH_SHORT).show();
                } else {
                    nextCount++;
                    gestureNum = "Gesture " + nextCount;
                    addVelocity();
                    int countHolder = nextCount+1;
                    textView.setText("Input Gesture# "+countHolder);
                    tapCount = 0;
                }

                if (nextCount == 5){
                    submitData.setText("Next");
                    textView.setText("Proceed to next gesture.");
                    Intent intent = new Intent(SwipeY.this, Scroll.class);
                    startActivity(intent);
                }
            }
        });



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetect.onTouchEvent(event);

        totalTime = upTime - event.getDownTime();
        x = event.getX();
        y = event.getY();

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

        float touchDuration = upTime - event.getDownTime();
        float downPos = (float) Math.sqrt(pow(sX, 2) + pow(sY, 2));
        float curPos = (float) Math.sqrt(pow(x, 2) + pow(y, 2));

        // SINGLE TAP
        if (touchDuration < 500) {
            singleTap = true;
            longPress = false;
            doubleTap = false;
            swipeX = false;
            swipeY = false;
            if (Math.abs(curPos - downPos) > singleTapRadius) {
                singleTap = false;
            } else {
                singleTap = true;
                // pagsingletap add sa data
                SwipeY.TapData data = new SwipeY.TapData();
                data.prevX = (long) event.getX();
                data.prevY = (long) event.getY();
                data.time = upTime;
                Data.add(data);
            }

        }


        // DOUBLE TAP
        // Check kung > 1 laman nung list

        if (Data.size() > 1) {
            // TRUE MUNA, pag pumasok sa loop, at natapos ung loop na true parin, double tap un

            doubleTap = true;
            // check para radius shit


            //TapData prev = Data.get(Data.size() - 1);
            //TapData prev2 = Data.get(Data.size()-2);

            // check x pos, pag hindi pasok, false
            if (Math.abs(Data.get(Data.size() - 1).prevX - Data.get(Data.size() - 2).prevX) > singleTapRadius) {
                doubleTap = false;
            }

            // check y pos, pag hindi pasok, false
            if (Math.abs(Data.get(Data.size() - 1).prevY - Data.get(Data.size() - 2).prevY) > singleTapRadius) {
                doubleTap = false;
            }

            // check time, pag hindi pasok, false
            if (Math.abs(Data.get(Data.size() - 1).time - Data.get(Data.size() - 2).time) > 200) {
                doubleTap = false;
            }

        }
        //hehehe di ko na alam




        // LONG PRESS
        if ( event.getEventTime() - event.getDownTime() >= 500)
        {
            singleTap = false;
            doubleTap = false;
            swipeX = false;
            swipeY = false;

            if( Math.abs( curPos - downPos ) > longPressRadius )
            {
                longPress = false;
            }
            else
                longPress = true;
        }

        //SWIPEHORIZONTAL or swipeX
        //uptime = time pagbitaw ng finger & event.getDownTime() = time pagpindot
        float swipeDistanceX = Math.abs(fX - sX);
        long swipeDurationX = (upTime - event.getDownTime());
        if( swipeDistanceX >= 150) {
            if(swipeDurationX <= 500) {
                singleTap = false;
                doubleTap = false;
                longPress = false;
                scroll = false;
                swipeX = true;
            }
        }

        //SWIPEVERTICAL or swipeY
        float swipeDistanceY = Math.abs(fY - sY);
        long swipeDurationY = (upTime - event.getDownTime());
        if( swipeDistanceY >= 150) {
            if(swipeDurationY <= 500) {
                singleTap = false;
                doubleTap = false;
                longPress = false;
                scroll = false;
                swipeY = true;
            }
        }

        //SCROLL
        float scrollDistance = Math.abs(fY - sY);
        long scrollDuration = (upTime - event.getDownTime());
        if( scrollDistance >= 50) {
            if( scrollDuration > 500) {
                singleTap = false;
                doubleTap = false;
                longPress = false;
                scroll = true;
                swipeX = false;
                swipeY = false;

            }
        }


        textView.setText(
                "\n\nON TOUCHEVENT"
//                        + "\nSINGLE TAP: " + singleTap
//                        + "\nDOUBLE TAP: " + doubleTap
//                        + "\nLONG PRESS: " + longPress
//                        + "\nSWIPE HORIZONTAL: " + swipeX
                          + "\nSWIPE VERTICAL: " + swipeY
//                        + "\nSCROLL: " + scroll
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

    private void addVelocity(){
        String id = databaseVelocity.push().getKey();
        String tapGestureId = databaseGestureDetails.push().getKey();
//        String tapGestureId =  databaseUser.push().getKey();    //user.getUid();

        Velocity velocity = new Velocity(id, singleTap, doubleTap, longPress, swipeX, swipeY, scroll);
        GestureDetails gestureDetails = new GestureDetails(x, y, sX, sY, fX, fY, totalTime, myName, deviceName, deviceMan);
//        GestureDetails gestureDetails = new GestureDetails(x, y, sX, sY, fX, fY, totalTime, myName, deviceName, deviceMan);

        databaseVelocity.child("Velocity").child("SwipeY").child(gestureNum).child(id).setValue(velocity);
        databaseGestureDetails.child("Gesture Details").child("SwipeY").child(gestureNum).child(tapGestureId).setValue(gestureDetails);
//        databaseUser.child(tapGestureId).setValue(gestureDetails);
        Toast.makeText(this, "Value Added", Toast.LENGTH_LONG).show();
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

       /* long EventDuration = e.getEventTime() - e.getDownTime();
        boolean doubleTapped = false;

        if(EventDuration > 100)
        {
            return false;
        }

       if(( prevX - e.getX() ) <= rangeX)
        {
            distance = prevX - e.getX();
            doubleTapped = true;
        }

        if((e.getX() - prevX) <= rangeX)
        {
            distance = e.getX() - prevX;
            doubleTapped = true;
        }


        */

        /* 8distance = (float)sqrt(  pow((double)prevX - (double)e.getX(), 2) + pow( (double)prevY - (double)e.getY(), 2)   );

        if(distance <= radius)
        {
            doubleTapped = true;
        }
        */
        /*
        if(doubleTapped == false)
        {
            return false;
        }
            textView.setText("\n\nonDoubleTapEvent\n"
                    + "X Position: " + e.getX()
                    + "\nY Position: " + e.getY()
                    + "\nDistance: " + distance
                    + "\n\nEventTime: " + e.getEventTime()
                    + "\nDownTime: " + e.getDownTime()
                    + "\nEventDuration: " + EventDuration
            );

        prevX = e.getX();
        prevY = e.getY();

        */

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

