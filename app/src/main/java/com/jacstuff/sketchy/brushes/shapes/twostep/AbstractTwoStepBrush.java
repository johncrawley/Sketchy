package com.jacstuff.sketchy.brushes.shapes.twostep;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractBrush;

public class AbstractTwoStepBrush extends AbstractBrush {
    StepState stepState = StepState.FIRST;

    public AbstractTwoStepBrush(BrushShape brushShape){
        super(brushShape);
    }

    public boolean isOnFirstStep(){
        return stepState == StepState.FIRST;
    }

    public void resetState(){
        stepState = StepState.FIRST;
    }


    public void setStateTo(StepState state){
        this.stepState = state;
    }



    public boolean isInFirstStep(){
        return stepState == StepState.FIRST;
    }


    public boolean isInSecondStep(){
        return stepState == StepState.SECOND;
    }


    public StepState getStepState(){
        return stepState;
    }
}
