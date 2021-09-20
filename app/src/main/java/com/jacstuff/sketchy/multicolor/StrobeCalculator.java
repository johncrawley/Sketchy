package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;


public class StrobeCalculator {

    private final ColorSequenceControls colorSequenceControls;
    private int direction = 1;
    private int changedDirectionCount = 0;
    private int resetIndex;
    boolean isUsingVariableIncrement = false;


    public StrobeCalculator(ControlsHolder viewModel){
        this.colorSequenceControls = viewModel.getColorSequenceControls();
    }


    public StrobeCalculator(ControlsHolder viewModel, boolean forSingleColor){
        this(viewModel);
        this.isUsingVariableIncrement = forSingleColor;
    }


    private int getIncrement(){
        return isUsingVariableIncrement ? colorSequenceControls.skippedShades : 1;
    }


    public int getNextStrobeIndex(int currentIndex, int sequenceMinIndex, int sequenceMaxIndex){
        if((direction == 1 && isAtEndOfForwardsSequence(currentIndex, sequenceMaxIndex))
                || (direction == -1 && isAtEndOfBackwardsSequence(currentIndex, sequenceMinIndex))){
            direction *= -1;
            changedDirectionCount++;
            if(getIncrement() > (sequenceMaxIndex - sequenceMinIndex)){
                return currentIndex == sequenceMaxIndex ? sequenceMinIndex : sequenceMaxIndex;
            }
        }

        if(!colorSequenceControls.doesRepeat
                && direction == 1
                && changedDirectionCount > 1
                && currentIndex + getIncrement() > resetIndex){
            return resetIndex;
        }
        int amendedValue = currentIndex + (direction * getIncrement());
        return Math.min(sequenceMaxIndex, Math.max(sequenceMinIndex, amendedValue));
    }


    public int getNextStrobeIndex(int currentIndex, int lastIndex){
        return getNextStrobeIndex(currentIndex, 0, lastIndex);
    }


    public void setResetIndex(int resetValue){
        this.resetIndex = resetValue;
    }

    public void reset(){
        changedDirectionCount = 0;
        direction = 1;
    }


    private boolean isAtEndOfForwardsSequence(int currentIndex, int sequenceMaxIndex){
        return currentIndex + getIncrement() > sequenceMaxIndex;
    }


    private boolean isAtEndOfBackwardsSequence(int currentIndex, int sequenceMinIndex){
        return currentIndex - getIncrement() < sequenceMinIndex;
    }

}
