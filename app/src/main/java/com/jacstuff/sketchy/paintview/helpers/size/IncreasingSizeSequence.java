package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class IncreasingSizeSequence implements  SizeSequence{

    private int currentSize;
    private final MainViewModel viewModel;

    public IncreasingSizeSequence(MainViewModel mainViewModel){
        this.viewModel = mainViewModel;
    }


    @Override
    public void init(int currentSize){
        this.currentSize = viewModel.brushSize;
    }


    @Override
    public int getNextBrushSize(){
        currentSize = currentSize < viewModel.sizeSequenceMax ? currentSize + 1 : viewModel.sizeSequenceMin;
        return  currentSize;
    }


    @Override
    public boolean hasSizeChanged(){
        return true;
    }
}
