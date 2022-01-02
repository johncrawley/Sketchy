package com.jacstuff.sketchy.paintview.helpers.placement;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.settings.placement.PlacementType;
import com.jacstuff.sketchy.paintview.helpers.size.SizeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PlacementHelper {

    private final MainViewModel viewModel;
    private Paint paint;
    private final RandomPlacement randomPlacement;
    private final int offsetDefault;
    private final int OFFSET_MULTIPLE = 5;
    private final SizeHelper sizeHelper;

    public PlacementHelper(MainViewModel viewModel, Context context, SizeHelper sizeHelper){
        this.viewModel = viewModel;
        this.randomPlacement = new RandomPlacement(viewModel);
        offsetDefault = context.getResources().getInteger(R.integer.placement_offset_x_default);
        this.sizeHelper = sizeHelper;
    }


    public void init(Paint paint){
        this.paint = paint;
        updateMaxRandomDistance();
        calculateQuantizationFactor();
    }

    public void initDimensions(int width, int height){
        randomPlacement.initDimensions(width, height);
    }


    public PointF calculatePoint(float x, float y){
        updateQuantizationFactor();
        return new PointF(getX(x), getY(y));
    }


    public void registerTouchDown(float x, float y){
        viewModel.touchDownXForLock = x;
        viewModel.touchDownYForLock = y;
        viewModel.quantizedTouchDownXForLock = getQuantized(x);
        viewModel.quantizedTouchDownYForLock = getQuantized(y);
    }


    private float getX(float x){
        if(viewModel.isPlacementHorizontalLocked){
            return viewModel.placementType == PlacementType.QUANTIZATION ?
                    viewModel.quantizedTouchDownXForLock :
                    viewModel.touchDownXForLock;
        }
        switch (viewModel.placementType){
            case OFFSET:
                return x + (viewModel.placementOffsetX - offsetDefault) * OFFSET_MULTIPLE;
            case QUANTIZATION:
                return getQuantized(x);
            case RANDOM:
                return randomPlacement.getX(x);
        }
        return x;
    }


    private float getY(float y){
        if(viewModel.isPlacementVerticalLocked){

            return viewModel.placementType == PlacementType.QUANTIZATION ?
                    viewModel.quantizedTouchDownYForLock :
                    viewModel.touchDownYForLock;
        }

        switch (viewModel.placementType){
            case OFFSET:
                return y + (viewModel.placementOffsetY - offsetDefault) * OFFSET_MULTIPLE;
            case QUANTIZATION:
                return getQuantized(y);
            case RANDOM:
                return randomPlacement.getY(y);
        }
        return y;
    }


    private float getQuantized(float a){
        return (viewModel.placementQuantizationSavedBrushSize) + a - (a % (viewModel.quantizationPlacementSpacing + viewModel.placementQuantizationFactor));
    }


    private void updateQuantizationFactor(){
        if(viewModel.isPlacementQuantizationLocked || viewModel.placementType != PlacementType.QUANTIZATION){
            return;
        }
        calculateQuantizationFactor();
    }


    public void setQuantizationLock(boolean isChecked){
        calculateQuantizationFactor();
        viewModel.isPlacementQuantizationLocked = isChecked;
    }


    private void calculateQuantizationFactor(){
        float size = sizeHelper.isCurrentSequenceStationary() ? viewModel.brushSize : viewModel.sizeSequenceMax;
        viewModel.placementQuantizationSavedBrushSize = size / 2f;
        viewModel.placementQuantizationFactor = size + getLineSizeFactor();
    }


    public void updateMaxRandomDistance(){
        randomPlacement.updateMaxRandomDistance();
    }


    public float getLineSizeFactor(){
       return viewModel.isPlacementQuantizationLineWidthIncluded ? paint.getStrokeWidth() : 0;
    }
}
