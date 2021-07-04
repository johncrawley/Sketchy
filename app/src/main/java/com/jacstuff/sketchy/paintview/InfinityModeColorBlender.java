package com.jacstuff.sketchy.paintview;

import android.graphics.Paint;

import com.jacstuff.sketchy.ColorUtils;
import com.jacstuff.sketchy.controls.colorbuttons.ColorConverter;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import static com.jacstuff.sketchy.ColorUtils.Rgb.*;


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
        int r = getNextColorComponent(currentColor, targetColor, RED);
        int g = getNextColorComponent(currentColor, targetColor, GREEN);
        int b = getNextColorComponent(currentColor, targetColor, BLUE);
        return ColorConverter.getIntFrom(r,g,b);
    }


    private int getNextColorComponent(int currentColor, int targetColor, ColorUtils.Rgb rgb){
        final int SHADE_INCREMENT = 8;

        int source = ColorUtils.getComponentFrom(currentColor, rgb);
        int target = ColorUtils.getComponentFrom(targetColor, rgb);

        if(Math.abs(source - target) <= SHADE_INCREMENT){
            return target;
        }
        return source > target ?
                source - SHADE_INCREMENT
                : source + SHADE_INCREMENT;
    }

}
