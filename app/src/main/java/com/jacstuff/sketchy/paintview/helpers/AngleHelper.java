package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.brushes.AngleType;

import java.util.Random;

public class AngleHelper {

    private int currentAngle;
    private int increment;
    private AngleType angleType;
    private final Random random;


    public AngleHelper(){
        random = new Random(System.currentTimeMillis());
        angleType = AngleType.ZERO;
    }


    public void setAngle(AngleType angleType) {
        currentAngle = 0;
        this.angleType = angleType;
        if (angleType.isIncremental()) {
            increment = angleType.get();
        }
    }


    public void updateAngle(){
        if(angleType.isIncremental()){
            currentAngle += increment;
            currentAngle = currentAngle % 360;
        }
        else if(angleType == AngleType.RANDOM){
            currentAngle = getRandomAngle();
        }
    }


    public void setAngle(int angle){
        this.currentAngle = angle;
    }


    public int getAngle(){
        if(angleType.isIncremental()
                || angleType == AngleType.OTHER
                || angleType == AngleType.RANDOM){
            return currentAngle;
        }
        return angleType.get();
    }

    public AngleType getAngleType(){
        return angleType;
    }


    private int getRandomAngle(){
        return random.nextInt(359);
    }

}
