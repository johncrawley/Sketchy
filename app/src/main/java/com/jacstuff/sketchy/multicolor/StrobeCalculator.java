package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

public class StrobeCalculator {

    private final ColorSequenceControls colorSequenceControls;
    private int direction = 1;
    private int changedDirectionCount = 0;
    private int resetIndex;


    public StrobeCalculator(ControlsHolder viewModel){
        this.colorSequenceControls = viewModel.getColorSequenceControls();
    }

    private void log(String msg){
        System.out.println("StrobeCalculator:" +  msg);
    }


    public int getNextStrobeIndex(int currentIndex, int sequenceMinIndex, int sequenceMaxIndex){
        if((direction == 1 && isAtEndOfForwardsSequence(currentIndex, sequenceMaxIndex))
                || (direction == -1 && isAtEndOfBackwardsSequence(currentIndex, sequenceMinIndex))){
            direction *= -1;
            changedDirectionCount++;
            if(colorSequenceControls.skippedShades > (sequenceMaxIndex - sequenceMinIndex)){
                return currentIndex == sequenceMaxIndex ? sequenceMinIndex : sequenceMaxIndex;
            }
        }

        if(!colorSequenceControls.doesRepeat
                && direction == 1
                && changedDirectionCount > 1
                && currentIndex + colorSequenceControls.skippedShades > resetIndex){
            return resetIndex;
        }
        int amendedValue = currentIndex + (direction * colorSequenceControls.skippedShades);
        return Math.min(sequenceMaxIndex, Math.max(sequenceMinIndex, amendedValue));
    }


    public void setResetIndex(int resetValue){
        this.resetIndex = resetValue;
    }

    public void reset(){
        changedDirectionCount = 0;
        direction = 1;
    }


    private boolean isAtEndOfForwardsSequence(int currentIndex, int sequenceMaxIndex){
        return currentIndex + colorSequenceControls.skippedShades > sequenceMaxIndex;
    }


    private boolean isAtEndOfBackwardsSequence(int currentIndex, int sequenceMinIndex){
        return currentIndex - colorSequenceControls.skippedShades < sequenceMinIndex;
    }

}
