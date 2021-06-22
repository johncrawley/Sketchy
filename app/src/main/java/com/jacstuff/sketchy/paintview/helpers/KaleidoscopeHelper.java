package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class KaleidoscopeHelper {

    private boolean isEnabled;
    private int defaultCenterX, defaultCenterY;
    private int centerX, centerY;
    private final int TOTAL_DEGREES = 360;
    private float degreeIncrement = 30;
    private final MainViewModel viewModel;

    public KaleidoscopeHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
    }


    public void setDefaultCenter(int x, int y){
        defaultCenterX = x;
        defaultCenterY = y;
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
        return viewModel.isKaleidoscopeCentred ? defaultCenterX : centerX;
    }

    public int getCenterY(){
        return viewModel.isKaleidoscopeCentred ? defaultCenterY : centerY;
    }

}
