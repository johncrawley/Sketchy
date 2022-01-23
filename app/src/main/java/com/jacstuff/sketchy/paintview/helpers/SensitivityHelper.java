package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.controls.settings.placement.PlacementType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class SensitivityHelper {

    private final int maxCount;
    private int currentCount;
    private final MainViewModel viewModel;
    private final AngleHelper angleHelper;

    public SensitivityHelper(MainViewModel viewModel, AngleHelper angleHelper){
        this.viewModel = viewModel;
        currentCount = 0;
        maxCount = 12;
        this.angleHelper = angleHelper;
    }


    public boolean shouldDraw(Brush currentBrush){
        if(!isEnabled() || !currentBrush.isUsingPlacementHelper()){
            return true;
        }
        if(++currentCount >= maxCount){
            currentCount = 0;
            return true;
        }
        return false;
    }


    private boolean isEnabled(){
        return viewModel.placementType == PlacementType.RANDOM
                || angleHelper.getAngleType() == AngleType.RANDOM
                || isLargeIncrementalAngleEnabled();
    }
    
    private boolean isLargeIncrementalAngleEnabled(){
        return angleHelper.getAngleType().isIncremental() && Math.abs(angleHelper.getAngleType().get()) > 10;
    }
}
