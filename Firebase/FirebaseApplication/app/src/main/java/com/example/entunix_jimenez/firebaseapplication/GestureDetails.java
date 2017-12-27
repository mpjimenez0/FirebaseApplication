package com.example.entunix_jimenez.firebaseapplication;

/**
 * Created by mpjim on 12/19/2017.
 */

public class GestureDetails {
    public float xPosition, yPosition, initialX, initialY, finalX, finalY;
    public long totalTime;
    public String myName, deviceName, deviceMan;

    public GestureDetails (float x, float y,
                           float sX, float sY,
                           float fX, float fY,
                           long totalTime, String myName,
                           String deviceName, String deviceMan){
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
}
