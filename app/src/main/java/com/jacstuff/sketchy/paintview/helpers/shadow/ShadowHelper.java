package com.jacstuff.sketchy.paintview.helpers.shadow;

import android.graphics.Color;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.utils.ColorUtils;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ShadowHelper {


    private ShadowType shadowType = ShadowType.NONE;

    private int shadowOffsetFactor;
    private boolean hasSizeBeenUpdated;
    private boolean hasDistanceBeenUpdated;
    private Paint paint;
    private final MainViewModel viewModel;


    public ShadowHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
    }


    public void setShadowSize(int size){
        viewModel.shadowSize = 1 + size;
        hasSizeBeenUpdated = true;
        assignShadow();
    }


    public void setShadowDistance(int progress){
        hasDistanceBeenUpdated = true;
        viewModel.shadowDistance = 1 + progress;
        assignShadow();
    }


    public void init(Paint paint){
        this.paint = paint;
    }


    public void updateOffsetFactor(){
        updateOffsetFactor(1);
    }


    public void updateOffsetFactor(int halfBrushSize){
        switch(viewModel.shadowOffsetType){
            case USE_SET_VALUE:
                shadowOffsetFactor = 100;
                break;
            case USE_SHAPE_WIDTH:
                shadowOffsetFactor = halfBrushSize;
                break;
            case USE_STROKE_WIDTH:
                shadowOffsetFactor = (int)paint.getStrokeWidth();
        }
        shadowOffsetFactor *= 2;
        assignShadow();
    }


    public boolean isShadowEnabled(){
        return shadowType != ShadowType.NONE;
    }


    public void setType(ShadowType shadowType){
        this.shadowType = shadowType;
        if(shadowType == ShadowType.NONE){
            paint.clearShadowLayer();
            return;
        }
        viewModel.shadowOffsetX = calculateOffsetFrom(shadowType.offsetX);
        viewModel.shadowOffsetY = calculateOffsetFrom(shadowType.offsetY);
        setShadowLayer();
    }


    private void assignShadow() {
        if(shadowType == ShadowType.NONE){
            paint.clearShadowLayer();
            return;
        }

        float previousX = viewModel.shadowOffsetX;
        float previousY = viewModel.shadowOffsetY;

        viewModel.shadowOffsetX = calculateOffsetFrom(shadowType.offsetX);
        viewModel.shadowOffsetY = calculateOffsetFrom(shadowType.offsetY);

        if( isShadowOffsetAndDistanceTheSame(previousX, previousY) && !hasSizeBeenUpdated){
            return;
        }

        hasSizeBeenUpdated = false;
        hasDistanceBeenUpdated = false;
        setShadowLayer();
    }


    private boolean isShadowOffsetAndDistanceTheSame(float previousX, float previousY){
        return previousX == viewModel.shadowOffsetX && previousY == viewModel.shadowOffsetY && !hasDistanceBeenUpdated;
    }


    public void setShadowLayer(){
        int color = ColorUtils.getColorFromSlider(viewModel.shadowColor);
        paint.setShadowLayer(viewModel.shadowSize, viewModel.shadowOffsetX, viewModel.shadowOffsetY, color);
    }


    private float calculateOffsetFrom(int offset){
        return ((shadowOffsetFactor * offset) /100f)  * viewModel.shadowDistance;
    }

}
