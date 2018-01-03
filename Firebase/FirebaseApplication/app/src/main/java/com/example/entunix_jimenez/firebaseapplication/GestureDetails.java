package com.example.entunix_jimenez.firebaseapplication;

/**
 * Created by mpjim on 12/19/2017.
 */

public class GestureDetails {
    public float xPosition, yPosition, initialX, initialY, finalX, finalY;
    public long totalTime;
    public String myName, deviceName, deviceMan, key;
    public boolean singleTap, doubleTap, longPress,
            scroll, fling, swipeX, swipeY;

    public GestureDetails(){

    }

    public GestureDetails (float x, float y,
                           float sX, float sY,
                           float fX, float fY,
                           long totalTime, String myName,
                           String deviceName, String deviceMan,
                           boolean singleTap, boolean doubleTap,
                           boolean longPress, boolean scroll,
                           boolean fling, boolean swipeX, boolean swipeY, String key){
        this.xPosition = x;
        this.yPosition = y;
        this.initialX = sX;
        this.initialY = sY;
        this.finalX = fX;
        this.finalY = fY;
        this.totalTime = totalTime;
        this.myName = myName;
        this.deviceName = deviceName;
        this.deviceMan = deviceMan;
        this.singleTap = singleTap;
        this.doubleTap = doubleTap;
        this.longPress = longPress;
        this.scroll = scroll;
        this.fling = fling;
        this.swipeX = swipeX;
        this.swipeY = swipeY;
        this.key = key;
    }

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getInitialX() {
        return initialX;
    }

    public void setInitialX(float initialX) {
        this.initialX = initialX;
    }

    public float getInitialY() {
        return initialY;
    }

    public void setInitialY(float initialY) {
        this.initialY = initialY;
    }

    public float getFinalX() {
        return finalX;
    }

    public void setFinalX(float finalX) {
        this.finalX = finalX;
    }

    public float getFinalY() {
        return finalY;
    }

    public void setFinalY(float finalY) {
        this.finalY = finalY;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMan() {
        return deviceMan;
    }

    public void setDeviceMan(String deviceMan) {
        this.deviceMan = deviceMan;
    }

    public boolean isSingleTap() {return singleTap;}

    public void setSingleTap(boolean singleTap) {this.singleTap = singleTap;}

    public boolean isDoubleTap() {return doubleTap;}

    public void setDoubleTap(boolean doubleTap) {this.doubleTap = doubleTap;}

    public boolean isLongPress() {return longPress;}

    public void setLongPress(boolean longPress) {this.longPress = longPress;}

    public boolean isScroll() {return scroll;}

    public void setScroll(boolean scroll) {this.scroll = scroll;}

    public boolean isFling() {return fling;}

    public void setFling(boolean fling) {this.fling = fling;}

    public boolean isSwipeX() {return swipeX;}

    public void setSwipeX(boolean swipeX) {this.swipeX = swipeX;}

    public boolean isSwipeY() {return swipeY;}

    public void setSwipeY(boolean swipeY) {this.swipeY = swipeY;}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
