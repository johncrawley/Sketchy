package com.jacstuff.sketchy.paintview.helpers.size.sequence.proximity;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.paintview.helpers.size.sequence.AbstractSizeSequence;
import com.jacstuff.sketchy.paintview.helpers.size.sequence.SizeSequence;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ProximitySizeSequence extends AbstractSizeSequence implements SizeSequence {

    private int currentSize;
    private final PaintView paintView;
    private float centerX, centerY;
    private float touchDownX, touchDownY;


    public ProximitySizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel, PaintView paintView){
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
        int size = ((int)(getDistance(x,y) / 12f) * viewModel.sizeSequenceIncrement);

        return Math.max(viewModel.sizeSequenceMin, Math.min(viewModel.sizeSequenceMax, size));
    }


    private int getDistance(float x, float y){
        switch (viewModel.proximityType){
            case POINT:
                return getDistanceFromFocalPoint(x,y);
            case HORIZONTAL_LINE:
                return Math.abs((int)centerY - (int)y);
            case VERTICAL_LINE:
               return Math.abs((int)centerX - (int) x);
        }
        return 1;
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


    private int getDistanceFromFocalPoint(float x, float y){
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
