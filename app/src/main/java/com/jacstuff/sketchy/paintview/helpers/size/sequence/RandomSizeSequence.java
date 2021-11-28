package com.jacstuff.sketchy.paintview.helpers.size.sequence;

import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class RandomSizeSequence extends  AbstractSizeSequence implements SizeSequence {

    private final Random random;
    private int targetSize;
    private int currentSize;

    public RandomSizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel){
        super(sizeInitializer, mainViewModel);
        random = new Random(System.currentTimeMillis());
    }


    @Override
    public void init(){
        super.init();
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
    public int getNextBrushSize(float x, float y){
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