package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DecreasingSizeSequence implements  SizeSequence{

    private int currentSize;
    private final MainViewModel viewModel;

    public DecreasingSizeSequence(MainViewModel mainViewModel){
        this.viewModel = mainViewModel;
    }


    @Override
    public void init(int currentSize){
        this.currentSize = viewModel.brushSize;
    }


    @Override
    public int getNextBrushSize(){
        currentSize = currentSize > viewModel.sizeSequenceMin ? currentSize - viewModel.sizeSequenceIncrement : viewModel.sizeSequenceMin;
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
