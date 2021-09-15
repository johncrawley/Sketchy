package com.jacstuff.sketchy.multicolor;


import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceColorSelector implements ColorSelector {

    private List<Integer> colors;
    private final ColorSequenceControls colorSequenceControls;
    private int currentIndex;
    private int resetIndex;
    private final Random random;
    private final StrobeCalculator strobeCalculator;

    int sequenceMaxIndex;
    int sequenceMinIndex;

    private final boolean isUsingBrightnessRange;


    public SequenceColorSelector(ControlsHolder viewModel){
        this(viewModel, false, false);
    }

    private boolean isForSingleColor = false;

    private int getIncrement(){
        return isForSingleColor ? colorSequenceControls.skippedShades : 1;
    }

    public SequenceColorSelector(ControlsHolder viewModel, boolean isUsingBrightnessRange){
        this.colorSequenceControls = viewModel.getColorSequenceControls();
        this.isUsingBrightnessRange = isUsingBrightnessRange;
        random = new Random(System.currentTimeMillis());
        strobeCalculator = new StrobeCalculator(viewModel);
    }

    public SequenceColorSelector(ControlsHolder viewModel, boolean isUsingBrightnessRange, boolean isForSingleColor){
        this.colorSequenceControls = viewModel.getColorSequenceControls();
        this.isUsingBrightnessRange = isUsingBrightnessRange;
        random = new Random(System.currentTimeMillis());
        this.isForSingleColor = isForSingleColor;
        strobeCalculator =  isForSingleColor ? new SingleColorStrobeCalculator(viewModel) : new StrobeCalculator(viewModel);
    }


    public void setSequenceType(ColorSequenceType type){
        colorSequenceControls.colorSequenceType = type;
        updateRangeIndexes();
    }


    public int getNextColor(){
        switch (colorSequenceControls.colorSequenceType){
            case FORWARDS:
                calculateNextForwardIndex();
                break;
            case BACKWARDS:
                calculateNextBackwardsIndex();
                break;
            case STROBE:
                calculateNextStrobeIndex();
                break;
            case RANDOM:
                calculateNextRandomIndex();
                break;
        }

        return colors.get(currentIndex);
    }


    public void calculateNextForwardIndex(){
        if(isAtEndOfForwardsSequence()){
            currentIndex = currentIndex == sequenceMinIndex ?
                    sequenceMaxIndex :
                    colorSequenceControls.doesRepeat ? sequenceMinIndex : sequenceMaxIndex;
            return;
        }
       currentIndex += getIncrement();
    }


    private void calculateNextBackwardsIndex(){
        if(isAtEndOfBackwardsSequence()){
            currentIndex = currentIndex == sequenceMaxIndex ?
                    sequenceMinIndex :
                    colorSequenceControls.doesRepeat ? sequenceMaxIndex : currentIndex;
            return;
        }
        currentIndex -= getIncrement();
    }


    private void calculateNextStrobeIndex(){
        currentIndex = strobeCalculator.getNextStrobeIndex(currentIndex, sequenceMinIndex, sequenceMaxIndex);
    }


    private void calculateNextRandomIndex(){
        currentIndex = sequenceMinIndex + random.nextInt((sequenceMaxIndex - sequenceMinIndex) + 1);
    }


    private boolean isAtEndOfForwardsSequence(){
        return currentIndex + getIncrement() > sequenceMaxIndex;
    }


    private boolean isAtEndOfBackwardsSequence(){
        return currentIndex - getIncrement() < sequenceMinIndex;
    }


    @Override
    public void resetCurrentIndex(){
        reset();
    }


    public void onTouchUp(){
        if(colorSequenceControls.isResetOnRelease){
            reset();
        }
    }


    @Override
    public void reset(){
        currentIndex = resetIndex;
        strobeCalculator.reset();
    }


    public void setColorList(List<Integer> inputList){
        colors = new ArrayList<>(inputList);
        updateRangeIndexes();
    }



    public void setResetIndex(int resetValue){
        strobeCalculator.setResetIndex(resetValue);
        this.resetIndex = resetValue;
    }


    public void updateRangeIndexes(){
        if(colors == null){
            return;
        }
        if(!isUsingBrightnessRange){
            sequenceMaxIndex = colors.size() -1;
            sequenceMinIndex = 0;
            currentIndex = resetIndex;
            return;
        }

        sequenceMaxIndex = getMaxIndexOfSequence(colorSequenceControls.colorSequenceMaxPercentage, colors);
        int minIndex = getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, colors);
        sequenceMinIndex = minIndex < sequenceMaxIndex ? minIndex : sequenceMaxIndex -1;

        resetIndex = Math.max(resetIndex, sequenceMinIndex);
        resetIndex = Math.min(resetIndex, sequenceMaxIndex);
        currentIndex = resetIndex;
    }


    public int getMaxIndexOfSequence(int seekBarColorRangeMaximum, List<?> colorList){
        int lastIndex = colorList.size() -1;
        int maxSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMaximum);
        return Math.max(1,maxSequenceIndex);
    }


    public int getMinIndexOfSequence(int seekBarColorRangeMinimum, List<?> colorList){
        int lastIndex = colorList.size() -1;
        int minSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMinimum);
        return Math.min(colorList.size()-2, minSequenceIndex);
    }


    public int getCurrentIndex(){
        return currentIndex;
    }


    @Override
    public void add(int id, List<Integer> shades){
        colors = new ArrayList<>(shades);
    }


    @Override
    public void remove(int id){
        //do nothing
    }


    @Override
    public void setColorList(int color){
        // do nothing
    }

}