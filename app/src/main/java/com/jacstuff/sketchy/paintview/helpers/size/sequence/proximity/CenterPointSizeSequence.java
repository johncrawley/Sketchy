package com.jacstuff.sketchy.paintview.helpers.size.sequence.proximity;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.paintview.helpers.size.sequence.AbstractSizeSequence;
import com.jacstuff.sketchy.paintview.helpers.size.sequence.SizeSequence;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class CenterPointSizeSequence extends AbstractSizeSequence implements SizeSequence {

    private int currentSize;
    private final PaintView paintView;
    private float centerX, centerY;
    private float touchDownX, touchDownY;

    public CenterPointSizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel, PaintView paintView){
        super(sizeInitializer, mainViewModel);
        this.paintView = paintView;
    }


    @Override
    public void init(){
        super.init();
        this.currentSize = viewModel.sizeSequenceMax;
    }

    @Override
    public void onTouchDown(float x, float y){
        touchDownX = x;
        touchDownY = y;
    }


    @Override
    public int getNextBrushSize(float x, float y){
        setCenterXY();
        return Math.min(viewModel.sizeSequenceMax, 1 + getDistanceFromCenter(x,y));
    }


    private void setCenterXY(){
        if(viewModel.sizeSequenceProximityFocalPoint == ProximityFocalPoint.CENTER){
            centerX = paintView.getWidth() / 2f;
            centerY = paintView.getHeight() /2f;
            return;
        }
        centerX = touchDownX;
        centerY = touchDownY;
    }


    private int getDistanceFromCenter(float x, float y){
        double diffX = centerX - x;
        double diffY = centerY - y;
        return (int) Math.sqrt( Math.pow(diffX,2) + Math.pow(diffY,2) );
    }


    @Override
    public int getBrushSize(){
        return currentSize;
    }


    @Override
    public void reset() {
        if(currentSize == viewModel.sizeSequenceMin || viewModel.isSizeSequenceResetOnTouchUp){
            currentSize = viewModel.sizeSequenceMax;
        }
    }


    @Override
    public boolean hasSizeChanged(){
        return true;
    }
}
