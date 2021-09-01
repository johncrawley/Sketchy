package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class StrobeSizeSequence implements  SizeSequence{

    private int currentSize;
    private final MainViewModel viewModel;
    private boolean isIncreasing;
    private final boolean isIncreasingDefault;

    public StrobeSizeSequence(MainViewModel mainViewModel, boolean isIncreasing){
        this.viewModel = mainViewModel;
        this.isIncreasing = isIncreasing;
        this.isIncreasingDefault = isIncreasing;
    }


    @Override
    public void init(){
        if(isIncreasingDefault){
            this.currentSize = viewModel.sizeSequenceMin;
            return;
        }
        this.currentSize = viewModel.sizeSequenceMax;
    }


    @Override
    public  int getNextBrushSize(){

        if(viewModel.sizeSequenceMax < viewModel.sizeSequenceMin){
            return viewModel.sizeSequenceMin;
        }

        if(isIncreasing) {
            if(currentSize < viewModel.sizeSequenceMax){
                currentSize += viewModel.sizeSequenceIncrement;
            }
            else{
                currentSize = viewModel.sizeSequenceMax;
                if(!isIncreasingDefault && !viewModel.isSizeSequenceRepeated){
                    return currentSize;
                }
                isIncreasing = false;
            }
        }
        else{
            if(currentSize > viewModel.sizeSequenceMin){
                currentSize -= viewModel.sizeSequenceIncrement;
            }
            else{
                currentSize = viewModel.sizeSequenceMin;
                if(isIncreasingDefault && !viewModel.isSizeSequenceRepeated){
                    return currentSize;
                }
                isIncreasing = true;
            }
        }

        return  currentSize;
    }


    @Override
    public void reset() {
        if(viewModel.isSizeSequenceResetOnTouchUp){
            isIncreasing = isIncreasingDefault;
            currentSize = isIncreasingDefault ? viewModel.sizeSequenceMin : viewModel.sizeSequenceMax;
        }
    }


    @Override
    public boolean hasSizeChanged(){
        return true;
    }
}
