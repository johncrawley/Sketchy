package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class IncreasingSizeSequence implements  SizeSequence{

    private int currentSize;
    private final MainViewModel viewModel;

    public IncreasingSizeSequence(MainViewModel mainViewModel){
        this.viewModel = mainViewModel;
    }


    @Override
    public void init(){
        this.currentSize = viewModel.sizeSequenceMin;
    }


    @Override
    public int getNextBrushSize(){
        if(viewModel.isSizeSequenceRepeated && currentSize == viewModel.sizeSequenceMax){
            currentSize = viewModel.sizeSequenceMin;
            return currentSize;
        }
        currentSize = currentSize < viewModel.sizeSequenceMax ? currentSize + viewModel.sizeSequenceIncrement : viewModel.sizeSequenceMax;
        return  currentSize;
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
