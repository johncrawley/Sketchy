package com.jacstuff.sketchy.paintview;

import android.graphics.Paint;

import com.jacstuff.sketchy.controls.colorbuttons.ColorConverter;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class InfinityModeColorBlender {

    private final MainViewModel viewModel;
    private ColorSelector colorSelector;
    private final Paint paint;
    private boolean hasBlendStarted;
    private int targetColor;


    InfinityModeColorBlender(MainViewModel viewModel, ColorSelector colorSelector, Paint paint){
        this.viewModel = viewModel;
        this.colorSelector = colorSelector;
        this.paint = paint;
    }


    void assignNextInfinityModeColor(){
        int currentColor = paint.getColor();
        if(!hasBlendStarted){
            hasBlendStarted = true;
            targetColor = colorSelector.getNextColor();
            viewModel.color = targetColor;
        }
        int nextShade = getNextShadeOfColor(currentColor, targetColor);
        paint.setColor(nextShade);

        if(nextShade == targetColor){
            viewModel.previousColor = targetColor;
            hasBlendStarted = false;
        }
    }


    void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
    }


    private int getNextShadeOfColor(int currentColor, int targetColor){
        int r = getNextColorComponent(currentColor, targetColor, 16);
        int g = getNextColorComponent(currentColor, targetColor, 8);
        int b = getNextColorComponent(currentColor, targetColor, 0);

        return ColorConverter.getIntFrom(r,g,b);
    }


    private int getNextColorComponent(int currentColor, int targetColor, int bitShift){
        final int SHADE_INCREMENT = 8;
        int source = getComponent(currentColor, bitShift);
        int target = getComponent(targetColor, bitShift);


        if(Math.abs(source - target) <= SHADE_INCREMENT){
            return target;
        }

        return source > target ?
                source - SHADE_INCREMENT
                : source + SHADE_INCREMENT;
    }


    private int getComponent(int color, int bitShift){
        return (color >> bitShift) & 0xff;
    }

}
