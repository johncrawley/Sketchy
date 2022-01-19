package com.jacstuff.sketchy.paintview.helpers.color;

import com.jacstuff.sketchy.utils.ColorUtils;

import java.util.Random;

import static com.jacstuff.sketchy.controls.colorbuttons.ColorConverter.getNextShadeOfColor;

public class InfinityModeRandomGradientBlender {

    private int currentShade;
    private int targetShade;
    private final Random random;

    public InfinityModeRandomGradientBlender(Random random){
        this.random = random;
        currentShade = getRandomColor();
        targetShade = getRandomColor();
    }


    public int getNextInfinityModeShade(){
        currentShade = getNextShadeOfColor(currentShade, targetShade);
        if(ColorUtils.areEqual(currentShade, targetShade)){
            targetShade = getRandomColor();
        }
        return currentShade;
    }


    private int getRandomColor(){
        return ColorUtils.getRandomColor(random);
    }
}
