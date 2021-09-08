package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class RandomSizeSequence implements  SizeSequence{

    private final MainViewModel viewModel;
    private final Random random;
    private int targetSize;
    private int currentSize;

    public RandomSizeSequence(MainViewModel mainViewModel){
        this.viewModel = mainViewModel;
        random = new Random(System.currentTimeMillis());
    }


    @Override
    public void init(){
        assignNextTargetSize();
        this.currentSize = targetSize;
    }


    @Override
    public int getBrushSize(){
        return currentSize;
    }


    private void assignNextTargetSize(){
        if(viewModel.sizeSequenceMax < viewModel.sizeSequenceMin){
            targetSize = viewModel.sizeSequenceMin;
        }
        int diff = viewModel.sizeSequenceMax - viewModel.sizeSequenceMin;
        targetSize = viewModel.sizeSequenceMin + random.nextInt(diff);
    }


    @Override
    public int getNextBrushSize(){
        if(currentSize == targetSize){
            assignNextTargetSize();
        }
        if(currentSize < targetSize){
            currentSize = Math.min(currentSize + viewModel.sizeSequenceIncrement, targetSize);
        }
        else if(currentSize > targetSize){
            currentSize = Math.max(currentSize - viewModel.sizeSequenceIncrement, targetSize);
        }
        return  currentSize;
    }


    @Override
    public void reset() {
        assignNextTargetSize();
        currentSize = targetSize;
    }


    @Override
    public boolean hasSizeChanged(){
        return true;
    }
}