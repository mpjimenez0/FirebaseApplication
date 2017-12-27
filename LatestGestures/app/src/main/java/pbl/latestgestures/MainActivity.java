package pbl.latestgestures;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.GestureDetector.OnDoubleTapListener;
import static android.view.GestureDetector.OnGestureListener;
import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity implements
        OnGestureListener,OnDoubleTapListener{


    float singleTapRadius = 25f;
    float longPressRadius = 10f;
    float doubleTapRadius = 200;

    float x, y, sX, sY, fX, fY;

    boolean singleTap;
    boolean doubleTap;
    boolean longPress;
    boolean scroll;
    boolean fling;
    boolean swipeX;
    boolean swipeY;

    long upTime = 0;

    List<TapData> Data = new ArrayList<TapData>();


    private static TextView textView;
    private GestureDetectorCompat GestureDetect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = 0;
        y = 0;
        sX = 0;
        sY = 0;
        fX = 0;
        fY = 0;

        singleTap = false;
        doubleTap = false;
        longPress = false;
        scroll = false;
        fling = false;
        swipeX = false;
        swipeY = false;

        textView = (TextView)findViewById(R.id.textView);
        GestureDetect = new GestureDetectorCompat(this,this);
        GestureDetect.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetect.onTouchEvent(event);

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
                TapData data = new TapData();
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
                        +"\nSINGLE TAP: " + singleTap
                        +"\nDOUBLE TAP: " + doubleTap
                        +"\nLONG PRESS: " + longPress
                        +"\nSWIPEX: " + swipeX
                        +"\nSWIPEY: " + swipeY
                        +"\nSCROLL: " + scroll
                        +"\nCurrent X: " + x
                        +"\nCurrent Y: " + y
                        +"\nDown X: " + sX
                        +"\nDownY: " + sY
                        +"\nUp X: " + fX
                        +"\nUp Y: " + fY
                        +"\nDown Time: " + event.getDownTime()
                        +"\nupTime: " + upTime
                        +"\nEvent Time: " + event.getEventTime()

        );

        return super.onTouchEvent(event);
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
