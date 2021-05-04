package com.jacstuff.sketchy.paintview.helpers;

public class KaleidoscopeHelper {


    private boolean isFixed = true;
    private boolean isEnabled;
    private int defaultCenterX, defaultCenterY;
    private int centerX, centerY;
    private final int TOTAL_DEGREES = 360;
    private float degreeIncrement = 30;

    public KaleidoscopeHelper(int defaultCenterX, int defaultCenterY){
        this.defaultCenterX = defaultCenterX;
        this.defaultCenterY = defaultCenterY;
    }


    public void setFixed(boolean isFixed){
        this.isFixed = isFixed;
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public int getMaxDegrees(){
        final int REMAINDER_OF_ANGLE_DIVISON = 5; //accounts for divisions of 360 that don't fit into 360 exactly
        return TOTAL_DEGREES  - REMAINDER_OF_ANGLE_DIVISON;
    }

    public void setSegments(int numberOfSegments){
        isEnabled = numberOfSegments > 1;
        this.degreeIncrement = (float)TOTAL_DEGREES / numberOfSegments;
    }

    public float getDegreeIncrement(){
        return degreeIncrement;
    }


    public void setCenter(float x, float y){
        this.centerX = (int) x;
        this.centerY = (int) y;
    }

    public int getCenterX(){
        return isFixed ? defaultCenterX : centerX;
    }

    public int getCenterY(){
        return isFixed ? defaultCenterY : centerY;
    }

}
