package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class IncreasingSizeSequence implements  SizeSequence{

    private int currentSize;
    private final MainViewModel viewModel;

    public IncreasingSizeSequence(MainViewModel mainViewModel){
        this.viewModel = mainViewModel;
    }


    public void init(int currentSize){
        this.currentSize = viewModel.brushSize;
    }


    public void assignNextBrushSize(){
        viewModel.brushSize = currentSize < viewModel.sizeSequenceMax ? currentSize + 1 : viewModel.sizeSequenceMin;
        viewModel.halfBrushSize = viewModel.brushSize / 2; //TODO: need to trigger some kind of notification for the current brush
    }


}
