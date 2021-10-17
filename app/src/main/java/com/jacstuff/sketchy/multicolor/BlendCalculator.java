package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.controls.colorbuttons.ColorConverter;
import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.List;
import java.util.Random;

public class BlendCalculator {


    private int currentShade, targetShade;


    public void reset(int currentShade, int nextShade) {
        this.currentShade = currentShade;
        this.targetShade = nextShade;
    }

    public void setTargetShade(int targetShade){
        this.targetShade = targetShade;
    }

    public int getNextShade() {
        if(hasReachedTargetShade()){
            return currentShade;
        }

        currentShade = ColorConverter.getNextShadeOfColor(currentShade, targetShade, 1);
        return currentShade;
    }


    public boolean hasReachedTargetShade(){
        return currentShade == targetShade;
    }
}
