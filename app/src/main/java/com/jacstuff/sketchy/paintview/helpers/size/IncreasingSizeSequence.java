package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class IncreasingSizeSequence extends AbstractSizeSequence implements SizeSequence{

    private int currentSize;

    public IncreasingSizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel){
        super(sizeInitializer, mainViewModel);
    }


    @Override
    public void init(){
        super.init();
        this.currentSize = viewModel.sizeSequenceMin;
    }


    @Override
    public int getNextBrushSize(float x, float y){
        if(viewModel.isSizeSequenceRepeated && currentSize == viewModel.sizeSequenceMax){
            currentSize = viewModel.sizeSequenceMin;
            return currentSize;
        }
        currentSize = currentSize < viewModel.sizeSequenceMax ? currentSize + viewModel.sizeSequenceIncrement : viewModel.sizeSequenceMax;
        return  currentSize;
    }


    @Override
    public int getBrushSize(){
        return currentSize;
    }


    @Override
    public void reset() {
        if(currentSize == viewModel.sizeSequenceMax || viewModel.isSizeSequenceResetOnTouchUp){
            currentSize = viewModel.sizeSequenceMin;
        }
    }


    @Override
    public boolean hasSizeChanged(){
        return true;
    }
}
