package com.jacstuff.sketchy.paintview.helpers.size;

import android.app.Activity;

import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.VaryingSizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DecreasingSizeSequence extends AbstractSizeSequence implements  SizeSequence{

    private int currentSize;

    public DecreasingSizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel){
        super(sizeInitializer, mainViewModel);
    }


    @Override
    public void init(){
        super.init();
        this.currentSize = viewModel.sizeSequenceMax;
    }


    @Override
    public int getNextBrushSize(){
        if(viewModel.isSizeSequenceRepeated && currentSize == viewModel.sizeSequenceMin){
            currentSize = viewModel.sizeSequenceMax;
            return currentSize;
        }

        currentSize = currentSize > viewModel.sizeSequenceMin ? currentSize - viewModel.sizeSequenceIncrement : viewModel.sizeSequenceMin;
        return currentSize;
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
