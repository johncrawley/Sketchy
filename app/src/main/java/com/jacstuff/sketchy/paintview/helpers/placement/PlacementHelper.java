package com.jacstuff.sketchy.paintview.helpers.placement;

import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.controls.settings.placement.PlacementType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class PlacementHelper {

    private final MainViewModel viewModel;
    private Paint paint;
    private final RandomPlacement randomPlacement;

    public PlacementHelper(PaintView paintView, MainViewModel viewModel){
        this.viewModel = viewModel;
        this.randomPlacement = new RandomPlacement(viewModel, paintView);
    }


    public void init(Paint paint){
        this.paint = paint;
        updateMaxRandomDistance();
        calculateQuantizationFactor();
    }


    public PointF calculatePoint(float x, float y){
        updateQuantizationFactor();
        return new PointF(getX(x), getY(y));
    }


    private float getX(float x){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return getQuantized(x);
            case RANDOM:
                return randomPlacement.getX(x);
        }
        return x;
    }


    private float getY(float y){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return getQuantized(y);
            case RANDOM:
                return randomPlacement.getY(y);
        }
        return y;
    }


    private float getQuantized(float a){
        return (viewModel.placementQuantizationSavedBrushSize) + a - (a % (viewModel.placementQuantizationFactor));
    }


    private void updateQuantizationFactor(){
        if(viewModel.isPlacementQuantizationLocked || viewModel.placementType != PlacementType.QUANTIZATION){
            return;
        }
        calculateQuantizationFactor();
    }


    private void calculateQuantizationFactor(){
        viewModel.placementQuantizationSavedBrushSize = viewModel.brushSize / 2f;
        viewModel.placementQuantizationFactor = viewModel.brushSize + getLineSizeFactor();
    }


    public void updateMaxRandomDistance(){
        randomPlacement.updateMaxRandomDistance();
    }


    public float getLineSizeFactor(){
       return viewModel.isPlacementQuantizationLineWidthIncluded ? paint.getStrokeWidth() : 0;
    }
}
