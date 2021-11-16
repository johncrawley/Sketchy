package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.brushes.RotationType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class AngleHelper {

    private int currentAngle;
    private int increment;
    private AngleType angleType;
    private final Random random;
    private final MainViewModel viewModel;
    private RotationType rotationType;


    public AngleHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
        random = new Random(System.currentTimeMillis());
        angleType = AngleType.ZERO;
        rotationType = RotationType.PRESET;
    }


    public void setAngle(AngleType angleType) {
        currentAngle = 0;
        this.angleType = angleType;
        if (angleType.isIncremental()) {
            increment = angleType.get();
        }
    }

    public void setRotationType(RotationType rotationType){
        this.rotationType = rotationType;
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


    public void setOtherAngle(int angle){
        angleType = AngleType.OTHER;
        this.currentAngle = angle;
        viewModel.angle = angle;
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
