package com.jacstuff.sketchy.multicolor;


import android.graphics.Color;

import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceColorSelector implements ColorSelector {

    private List<Integer> colors;
    private final ColorSequenceControls colorSequenceControls;
    private int sequenceMaxIndex, sequenceMinIndex, currentIndex, resetIndex;
    private final Random random;
    private final StrobeCalculator strobeCalculator;
    private final boolean isUsingBrightnessRange, isForSingleColor;
    private final BlendCalculator blendCalculator;

    public SequenceColorSelector(ControlsHolder viewModel){
        this(viewModel, false, false);
    }


    public SequenceColorSelector(ControlsHolder viewModel, boolean isUsingBrightnessRange, boolean isForSingleColor){
        this.colorSequenceControls = viewModel.getColorSequenceControls();
        this.isUsingBrightnessRange = isUsingBrightnessRange;
        random = new Random(System.currentTimeMillis());
        this.isForSingleColor = isForSingleColor;
        strobeCalculator =  isForSingleColor ? new StrobeCalculator(viewModel, true) : new StrobeCalculator(viewModel);
        blendCalculator = new BlendCalculator();
    }


    @Override
    public void resetCurrentIndex(){
        reset();
    }


    @Override
    public void reset(){
        currentIndex = resetIndex;
        strobeCalculator.reset();
    }


    @Override
    public void add(int id, List<Integer> shades){
        colors = new ArrayList<>(shades);
    }


    public void setSequenceType(ColorSequenceType type){
        colorSequenceControls.colorSequenceType = type;
        if(colorSequenceControls.colorSequenceType == ColorSequenceType.BLEND){
           blendCalculator.reset(calculateNextBlendTargetColor(), calculateNextBlendTargetColor());
        }
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
            case BLEND:
                return calculateNextBlendColor();
        }
        return colors.get(currentIndex);
    }



    public int getCurrentColor(){
        return colors == null ? Color.RED : colors.get(currentIndex);
    }


    public void calculateNextForwardIndex(){
        if(isAtEndOfForwardsSequence()){
            currentIndex = currentIndex == sequenceMinIndex ?
                    sequenceMaxIndex :
                    colorSequenceControls.doesRepeat ? sequenceMinIndex : sequenceMaxIndex;
            return;
        }
        incrementCurrentIndex(getIncrement());
    }


    private int calculateNextBlendTargetColor(){
        currentIndex = strobeCalculator.getNextStrobeIndex(currentIndex, sequenceMinIndex, sequenceMaxIndex);
        System.out.println("^^^ SequenceColorSelector.calculateNextBlendTargetColor() : currentIndex =  " + currentIndex);
        return colors.get(currentIndex);
    }


    public void updateRangeIndexes(){
        if(colors == null){
            return;
        }
        if(!isUsingBrightnessRange){
            calculateRangesWhenUsingBrightnessControl();
            return;
        }
        sequenceMaxIndex = getMaxIndexOfSequence(colorSequenceControls.colorSequenceMaxPercentage, colors);
        int minIndex = getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, colors);
        sequenceMinIndex = minIndex < sequenceMaxIndex ? minIndex : sequenceMaxIndex -1;

        resetIndex = Math.min(Math.max(resetIndex, sequenceMinIndex), sequenceMaxIndex);
        currentIndex = resetIndex;
    }


    public void incrementCurrentIndex(int value){
        currentIndex += value;
        currentIndex = Math.min(colors.size()-1, currentIndex);
        currentIndex = Math.max(0, currentIndex);
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


    private void calculateRangesWhenUsingBrightnessControl(){
        sequenceMaxIndex = colors.size() -1;
        sequenceMinIndex = 0;
        currentIndex = resetIndex;
    }


    private void calculateNextBackwardsIndex(){
        if(isAtEndOfBackwardsSequence()){
            currentIndex = currentIndex == sequenceMaxIndex ?
                    sequenceMinIndex :
                    colorSequenceControls.doesRepeat ? sequenceMaxIndex : currentIndex;
            return;
        }
        incrementCurrentIndex(getIncrement() * -1);
    }


    private void calculateNextStrobeIndex(){
        currentIndex = strobeCalculator.getNextStrobeIndex(currentIndex, sequenceMinIndex, sequenceMaxIndex);
    }


    private int calculateNextBlendColor(){
        int nextBlendColor = blendCalculator.getNextShade();
        if(nextBlendColor == getCurrentColor()){
            blendCalculator.setTargetShade(calculateNextBlendTargetColor());
        }
        return nextBlendColor;
    }


    private void log(String msg){
        System.out.println("^^^ SequenceColorSelector: " + msg);
        System.out.flush();
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


    private int getIncrement(){
        return isForSingleColor ? colorSequenceControls.skippedShades : 1;
    }


    public void setColorList(List<Integer> inputList){
        colors = new ArrayList<>(inputList);
        updateRangeIndexes();
    }


    public void setResetIndex(int resetValue){
        strobeCalculator.setResetIndex(resetValue);
        this.resetIndex = resetValue;
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