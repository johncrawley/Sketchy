package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.viewmodel.ControlsHolder;

public class SingleColorStrobeCalculator extends StrobeCalculator {
    public SingleColorStrobeCalculator(ControlsHolder controlsHolder){
        super(controlsHolder);
        isUsingVariableIncrement = true;
    }
}
