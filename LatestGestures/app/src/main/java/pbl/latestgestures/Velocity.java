package pbl.latestgestures;

/**
 * Created by LENOVO_PC on 23/10/2017.
 */

public class Velocity {
    public String velocityId;
    public boolean singleTap, doubleTap, longPress;


    public Velocity(String velocityId, boolean singleTap, boolean doubleTap, boolean longPress) {
        this.velocityId = velocityId;
        this.singleTap = singleTap;
        this.doubleTap = doubleTap;
        this.longPress = longPress;
    }
}

