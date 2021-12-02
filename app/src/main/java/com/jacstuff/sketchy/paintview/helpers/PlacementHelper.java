package com.jacstuff.sketchy.paintview.helpers;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PlacementHelper {

    private final MainViewModel viewModel;

    public PlacementHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
    }

    public float getX(float x){
        if(viewModel.isPlacementQuantizationEnabled){
            return (viewModel.brushSize/2f) + x - (x % viewModel.brushSize);
        }
        return x;
    }

    public float getY(float y){
        if(viewModel.isPlacementQuantizationEnabled){
            return (viewModel.brushSize/2f) + y - (y % viewModel.brushSize);
        }
        return y;
    }
}
