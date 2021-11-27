package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class CenterPointSizeSequence extends AbstractSizeSequence implements  SizeSequence{

    private int currentSize;

    public CenterPointSizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel){
        super(sizeInitializer, mainViewModel);
    }


    @Override
    public void init(){
        super.init();
        this.currentSize = viewModel.sizeSequenceMax;
    }

    private final float centerX = 500;
    private final float centerY = 500;


    @Override
    public int getNextBrushSize(float x, float y){
        return Math.min(viewModel.sizeSequenceMax, 1 + getDistanceFromCenter(x,y));
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
