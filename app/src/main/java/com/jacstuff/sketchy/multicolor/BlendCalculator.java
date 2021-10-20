package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.controls.colorbuttons.ColorConverter;
import com.jacstuff.sketchy.utils.ColorUtils;
import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;


public class BlendCalculator {



    private int currentShade, targetShade;
    private final ColorSequenceControls colorSequenceControls;


    public BlendCalculator(ControlsHolder controlsHolder){
        this.colorSequenceControls = controlsHolder.getColorSequenceControls();
    }


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
        currentShade = ColorConverter.getNextShadeOfColor(currentShade, targetShade, colorSequenceControls.skippedShades);
        return currentShade;
    }


    public boolean hasReachedTargetShade() {
        return ColorUtils.haveEqualRgb(currentShade, targetShade);
    }
}
