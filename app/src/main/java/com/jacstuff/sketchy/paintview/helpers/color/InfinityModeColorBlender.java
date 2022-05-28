package com.jacstuff.sketchy.paintview.helpers.color;

import android.graphics.Paint;

import com.jacstuff.sketchy.utils.ColorUtils;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import static com.jacstuff.sketchy.controls.colorbuttons.ColorConverter.getNextShadeOfColor;


public class InfinityModeColorBlender {

    private final MainViewModel viewModel;
    private ColorSelector colorSelector;
    private final Paint paint;
    private boolean hasBlendStarted;
    private int targetColor;
    private int nextTargetSecondaryShade;


    public InfinityModeColorBlender(MainViewModel viewModel, ColorSelector colorSelector, Paint paint){
        this.viewModel = viewModel;
        this.colorSelector = colorSelector;
        this.paint = paint;
        nextTargetSecondaryShade = paint.getColor();
    }


    public void assignNextInfinityModeColor(){
        int currentColor = paint.getColor();
        if(!hasBlendStarted){
            hasBlendStarted = true;
            targetColor = colorSelector.getNextColor();
            nextTargetSecondaryShade = currentColor;
            viewModel.color = targetColor;
        }

        int nextShade = getNextShadeOfColor(currentColor, targetColor);
        viewModel.previousColor = getNextShadeOfColor(viewModel.previousColor, nextTargetSecondaryShade);
        paint.setColor(nextShade);
        paint.setAlpha(viewModel.colorAlpha);
        viewModel.color = nextShade;
        if(ColorUtils.areEqual(nextShade, targetColor)){
            hasBlendStarted = false;
        }
        if(viewModel.previousColor == nextTargetSecondaryShade){
            nextTargetSecondaryShade = paint.getColor();
        }
    }


    public void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
    }

}
