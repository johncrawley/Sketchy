package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Canvas;

import com.jacstuff.sketchy.brushes.shapes.drawer.KaleidoscopeDrawer;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class KaleidoscopeHelper {

    private boolean isEnabled;
    private int defaultCenterX, defaultCenterY;
    private int centerX, centerY;
    private final int TOTAL_DEGREES = 360;
    private float degreeIncrement = 30;
    private final MainViewModel viewModel;
    private final KaleidoscopeDrawer kaleidoscopeDrawer;

    public KaleidoscopeHelper(PaintView paintView, MainViewModel viewModel){
        this.viewModel = viewModel;
        kaleidoscopeDrawer = new KaleidoscopeDrawer(paintView, viewModel, this);
    }


    public void setDefaultCenter(int x, int y){
        defaultCenterX = x;
        defaultCenterY = y;
    }


    public void setCanvas(Canvas canvas){
        kaleidoscopeDrawer.setCanvas(canvas);
        setDefaultCenter(canvas.getWidth()/2, canvas.getHeight()/2);
    }


    public boolean isEnabled(){
        return isEnabled;
    }

    public int getMaxDegrees(){
        final int REMAINDER_OF_ANGLE_DIVISION = 5; //accounts for divisions of 360 that don't fit into 360 exactly
        return TOTAL_DEGREES  - REMAINDER_OF_ANGLE_DIVISION;
    }


    public boolean isInfinityModeEnabled(){
        return isEnabled && viewModel.isInfinityModeEnabled;
    }

    public void setSegments(int numberOfSegments){
        isEnabled = numberOfSegments > 1;
        this.degreeIncrement = (float)TOTAL_DEGREES / numberOfSegments;
    }

    public float getDegreeIncrement(){
        return degreeIncrement;
    }


    public void setCenter(float x, float y){
        System.out.println("default centre x,y :" + defaultCenterX + " " + defaultCenterY + " canvas width,height: " + kaleidoscopeDrawer.paintView.getWidth() + "," + kaleidoscopeDrawer.paintView.getHeight());
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
