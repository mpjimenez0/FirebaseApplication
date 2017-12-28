package pbl.latestgestures;

/**
 * Created by LENOVO_PC on 23/10/2017.
 */

public class Velocity {
    public String velocityId;
    public boolean singleTap, doubleTap, longPress, swipeX, swipeY, scroll;


    public Velocity(String velocityId, boolean singleTap, boolean doubleTap, boolean longPress, boolean swipeX, boolean swipeY, boolean scroll) {
        this.velocityId = velocityId;
        this.singleTap = singleTap;
        this.doubleTap = doubleTap;
        this.longPress = longPress;
        this.swipeX = swipeX;
        this.swipeY = swipeY;
        this.scroll = scroll;
    }
}

