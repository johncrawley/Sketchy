package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.controls.settings.placement.PlacementType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class PlacementHelper {

    private final MainViewModel viewModel;
    private final PaintView paintView;
    private Paint paint;
    private final Random random;

    public PlacementHelper(PaintView paintView, MainViewModel viewModel){
        this.paintView = paintView;
        this.viewModel = viewModel;
        random = new Random(System.currentTimeMillis());
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


    public float getX(float x){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return getQuantized(x);
            case RANDOM:
                return getRandomX(x);
        }
        return x;
    }


    public float getY(float y){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return getQuantized(y);
            case RANDOM:
                return getRandomY(y);
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


    private float getRandomY(float y){
        return ( y-viewModel.randomPlacementMaxDistanceY) + (2 * (random.nextInt((int)viewModel.randomPlacementMaxDistanceY)));
    }


    private float getRandomX(float x){
        return (x -viewModel.randomPlacementMaxDistanceX) + (2 * (random.nextInt((int)viewModel.randomPlacementMaxDistanceX)));
    }


    public void updateMaxRandomDistance(){
        viewModel.randomPlacementMaxDistanceX = 2 + (paintView.getWidth() / 100f) * viewModel.randomPlacementMaxDistancePercentage;
        viewModel.randomPlacementMaxDistanceY = 2 + (paintView.getHeight() / 100f) * viewModel.randomPlacementMaxDistancePercentage;
    }


    public float getLineSizeFactor(){
       return viewModel.isPlacementQuantizationLineWidthIncluded ? paint.getStrokeWidth() : 0;
    }
}
