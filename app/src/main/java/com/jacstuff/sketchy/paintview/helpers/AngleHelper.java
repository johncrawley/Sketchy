package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.brushes.AngleType;

import java.util.concurrent.ThreadLocalRandom;

public class AngleHelper {

    private int currentAngle;
    private int increment;
    private int isRandom;
    private AngleType angleType;
    private int currentPresetViewId;

    public AngleHelper(){
        log("Creating angle");
        angleType = AngleType.ZERO;
    }

    public void setAngle(AngleType angleType) {
        log("Entered setAngle(angleType) + angleType: "+  angleType.toString());
        currentAngle = 0;
        this.angleType = angleType;
        if (angleType.isIncremental()) {
            increment = angleType.get();
            return;
        }
    }

    private void log(String msg){
        System.out.println("AngleHelper: " + msg);
    }

    public void setCurrentPresetViewId(int viewId){
        this.currentPresetViewId = viewId;
    }


    public int getCurrentPresetViewId(){
        return currentPresetViewId;
    }


    public void updateAngle(){
        if(angleType.isIncremental()){
            currentAngle += increment;
            currentAngle = currentAngle % 360;
        }
    }

    public void setAngle(int angle){
        this.currentAngle = angle;
    }

    public int getAngle(){
        if(angleType == AngleType.RANDOM){
            return getRandomAngle();
        }
        if(angleType.isIncremental() || angleType == AngleType.OTHER){
            return currentAngle;
        }
        return angleType.get();
    }


    private int getRandomAngle(){
        return ThreadLocalRandom.current().nextInt(0, 359);
    }

}
