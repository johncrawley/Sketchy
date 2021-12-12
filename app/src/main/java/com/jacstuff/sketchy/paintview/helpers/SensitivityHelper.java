package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.controls.settings.placement.PlacementType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class SensitivityHelper {

    private final int maxCount;
    private int currentCount;
    private final MainViewModel viewModel;

    public SensitivityHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
        currentCount = 0;
        maxCount = 8;
    }

    private boolean isEnabled(){
        return viewModel.placementType == PlacementType.RANDOM;
    }


    public boolean shouldDraw(){
        if(!isEnabled()){
            return true;
        }
        if(++currentCount >= maxCount){
            currentCount = 0;
            return true;
        }
        return false;
    }
}
